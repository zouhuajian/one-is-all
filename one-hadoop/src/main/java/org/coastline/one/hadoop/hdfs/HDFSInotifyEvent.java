package org.coastline.one.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSInotifyEventInputStream;
import org.apache.hadoop.hdfs.client.HdfsAdmin;
import org.apache.hadoop.hdfs.inotify.Event;
import org.apache.hadoop.hdfs.inotify.EventBatch;
import org.apache.hadoop.hdfs.inotify.MissingEventsException;
import org.coastline.one.core.tool.TimeTool;

import java.io.IOException;
import java.net.URI;

/**
 * @author Jay.H.Zou
 * @date 2022/11/17
 */
public class HDFSInotifyEvent {

    public static void main(String[] args) throws IOException, InterruptedException, MissingEventsException, IOException {

        long lastReadTxid = 2484433735L;

        if (args.length > 1) {
            lastReadTxid = Long.parseLong(args[1]);
        }
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        URI uri = URI.create("hdfs://xxx:4007/");
        Configuration configuration = new Configuration();
        HdfsAdmin admin = new HdfsAdmin(uri, configuration);
        // DFSInotifyEventInputStream eventStream = admin.getInotifyEventStream(lastReadTxid);
        DFSInotifyEventInputStream eventStream = admin.getInotifyEventStream();
        while (true) {
            EventBatch batch = eventStream.take();
            for (Event event : batch.getEvents()) {
                String format = TimeTool.currentLocalDateTimeFormat() + "\t" +
                        event.getEventType() + ":\tPath = %s";
                String path;
                String info = null;
                switch (event.getEventType()) {
                    case CREATE:
                        Event.CreateEvent createEvent = (Event.CreateEvent) event;
                        path = createEvent.getPath();
                        break;
                    case UNLINK:
                        Event.UnlinkEvent unlinkEvent = (Event.UnlinkEvent) event;
                        path = unlinkEvent.getPath();
                        break;
                    case APPEND:
                        Event.AppendEvent appendEvent = (Event.AppendEvent) event;
                        path = appendEvent.getPath();
                        break;
                    case CLOSE:
                        Event.CloseEvent closeEvent = (Event.CloseEvent) event;
                        path = closeEvent.getPath();
                        break;
                    case RENAME:
                        Event.RenameEvent renameEvent = (Event.RenameEvent) event;
                        path = renameEvent.getSrcPath();
                        info = ", NewPath = " + renameEvent.getDstPath();
                        break;
                    case METADATA:
                        Event.MetadataUpdateEvent metadataUpdateEvent = (Event.MetadataUpdateEvent) event;
                        path = metadataUpdateEvent.getPath();
                        info = ", MetadataType = " + metadataUpdateEvent.getMetadataType();
                        break;
                    case TRUNCATE:
                        Event.TruncateEvent truncateEvent = (Event.TruncateEvent) event;
                        path = truncateEvent.getPath();
                        break;
                    default:
                        continue;
                }
                if (path.startsWith("/xxxx")) {
                    System.out.println(String.format(format, path) + (info == null ? "" : info));
                }
            }
        }
    }

}
