package org.apache.hadoop.hdfs.server.namenode;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.server.common.Storage;
import org.apache.hadoop.hdfs.server.protocol.NamespaceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2023/7/24
 */
public class OneEditLogTailer {
    private static final Logger LOG = LoggerFactory.getLogger(OneEditLogTailer.class);
    Configuration conf = new Configuration();

    private OneJournalSet journalSet = null;
    private NNStorage storage = null;
    static long segmentTxId = 1234L;

    public static void main(String[] args) throws IOException, SAXException {
        segmentTxId = Long.parseLong(args[0]);
        OneEditLogTailer tailer = new OneEditLogTailer();
        tailer.initJournals();
        tailer.selectInputStreams();
    }

    private void selectInputStreams() throws IOException, SAXException {
        if (!journalSet.isOpen()) {
            throw new RuntimeException("journalSet not open");
        }
        List<EditLogInputStream> streams = new ArrayList<EditLogInputStream>();

        journalSet.selectInputStreams(streams, segmentTxId, true, true);

        LOG.info("streams size is {}", streams.size());
        System.out.println("streams size is " + streams.size());
        for (EditLogInputStream stream : streams) {
            RedundantEditLogInputStream inputStream = (RedundantEditLogInputStream) stream;
            System.out.println(inputStream.getClass());
            System.out.printf("stream info: position = %d, lastTxId = %d%n", inputStream.getPosition(), inputStream.getLastTxId());
            ContentHandler contentHandler = getContentHandler(System.out);
            FSEditLogOp fsEditLogOp = inputStream.nextValidOp();
            while (fsEditLogOp != null) {
                fsEditLogOp.outputToXml(contentHandler);
                fsEditLogOp = inputStream.nextValidOp();
            }
            contentHandler.endDocument();
        }
    }

    private ContentHandler getContentHandler(OutputStream out)
            throws IOException {
        String XML_INDENTATION_PROP = "{http://xml.apache.org/xslt}indent-amount";
        String XML_INDENTATION_NUM = "2";
        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        try {
            TransformerHandler handler = factory.newTransformerHandler();
            handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
            handler.getTransformer().setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
            handler.getTransformer().setOutputProperty(XML_INDENTATION_PROP, XML_INDENTATION_NUM);
            handler.getTransformer().setOutputProperty(OutputKeys.STANDALONE, "yes");
            handler.setResult(new StreamResult(out));
            handler.startDocument();
            handler.startElement("", "", "EDITS", new AttributesImpl());
            return handler;
        } catch (TransformerConfigurationException e) {
            throw new IOException("SAXTransformer error: " + e.getMessage());
        } catch (SAXException e) {
            throw new IOException("SAX error: " + e.getMessage());
        }
    }

    private void initJournals() throws IOException {
        Collection<URI> imageDirs = FSNamesystem.getNamespaceDirs(conf);
        List<URI> editsDirs = FSNamesystem.getNamespaceEditsDirs(conf);
        List<URI> sharedEditsDirs = FSNamesystem.getSharedEditsDirs(conf);

        storage = new NNStorage(conf, imageDirs, editsDirs);
        int minimumRedundantJournals = conf.getInt(
                DFSConfigKeys.DFS_NAMENODE_EDITS_DIR_MINIMUM_KEY,
                DFSConfigKeys.DFS_NAMENODE_EDITS_DIR_MINIMUM_DEFAULT);

        journalSet = new OneJournalSet(minimumRedundantJournals);
        for (URI u : sharedEditsDirs) {
            boolean required = FSNamesystem.getRequiredNamespaceEditsDirs(conf).contains(u);
            if (u.getScheme().equals("file")) {
                Storage.StorageDirectory sd = storage.getStorageDirectory(u);
                if (sd != null) {
                    journalSet.add(new FileJournalManager(conf, sd, storage), required, sharedEditsDirs.contains(u));
                }
            } else {
                journalSet.add(createJournal(u), required, sharedEditsDirs.contains(u));
            }
        }

        if (journalSet.isEmpty()) {
            LOG.error("No edits directories configured!");
            System.err.println("No edits directories configured!");
        }

        LOG.info("Init successfully");
        System.out.println("Init successfully");
    }

    JournalManager createJournal(URI uri) {
        Class<? extends JournalManager> clazz = getJournalClass(conf, uri.getScheme());
        try {
            Constructor<? extends JournalManager> cons
                    = clazz.getConstructor(Configuration.class, URI.class, NamespaceInfo.class, String.class);
            String nameServiceId = conf.get(DFSConfigKeys.DFS_NAMESERVICE_ID);
            return cons.newInstance(conf, uri, storage.getNamespaceInfo(), nameServiceId);
        } catch (NoSuchMethodException ne) {
            try {
                Constructor<? extends JournalManager> cons
                        = clazz.getConstructor(Configuration.class, URI.class, NamespaceInfo.class);
                return cons.newInstance(conf, uri, storage.getNamespaceInfo());
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to construct journal, " + uri, e);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to construct journal, " + uri, e);
        }
    }

    static Class<? extends JournalManager> getJournalClass(Configuration conf, String uriScheme) {
        String key
                = DFSConfigKeys.DFS_NAMENODE_EDITS_PLUGIN_PREFIX + "." + uriScheme;
        Class<? extends JournalManager> clazz = null;
        try {
            clazz = conf.getClass(key, null, JournalManager.class);
        } catch (RuntimeException re) {
            throw new IllegalArgumentException("Invalid class specified for " + uriScheme, re);
        }
        if (clazz == null) {
            // LOG.warn("No class configured for " + uriScheme + ", " + key + " is empty");
            throw new IllegalArgumentException(
                    "No class configured for " + uriScheme);
        }
        return clazz;
    }

}
