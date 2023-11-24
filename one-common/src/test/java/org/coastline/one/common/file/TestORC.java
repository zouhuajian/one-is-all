package org.coastline.one.common.file;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.orc.CompressionKind;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;
import org.coastline.one.common.model.INode;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2023/10/18
 */
public class TestORC {

    @Test
    public void testWrite() throws IOException {
        INode inode = INode.builder()
                .id(15)
                .parentId(1)
                .name("warehouse")
                .build();
        String filePath = "/Users/zouhuajian/data/projects/jay/one-is-all/one-data/avro/inode.avro";
        String orcFilePath = "your_data.orc"; // ORC文件路径


        TypeDescription schema = TypeDescription.createStruct();
        schema.addField("id", TypeDescription.createLong());
        schema.addField("parent_id", TypeDescription.createLong());
        schema.addField("name", TypeDescription.createString());

        OrcFile.WriterOptions options = OrcFile.writerOptions(new Configuration())
                .setSchema(schema)
                .compress(CompressionKind.ZSTD)
                ;

        try {
            Writer writer = OrcFile.createWriter(new Path(orcFilePath), options);


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
