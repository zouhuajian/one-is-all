package org.coastline.one.common.file;


import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.column.ParquetProperties;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.coastline.one.common.model.INode;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2023/10/18
 */
public class TestParquet {

    private static final Schema SCHEMA = new Schema.Parser().parse("{\n" +
            "  \"type\": \"record\",\n" +
            "  \"name\": \"inode\",\n" +
            "  \"fields\": [\n" +
            "    {\n" +
            "      \"name\": \"id\",\n" +
            "      \"type\": \"long\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"parent_id\",\n" +
            "      \"type\": \"long\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"name\",\n" +
            "      \"type\": \"string\"\n" +
            "    }\n" +
            "  ]\n" +
            "}");

    @Test
    public void testWrite() throws IOException {
        INode inode = INode.builder()
                .id(15)
                .parentId(1)
                .name("warehouse")
                .build();
        String filePath = "file:///Users/zouhuajian/data/projects/jay/one-is-all/one-data/parquet/inode.parquet";
        ParquetWriter<GenericRecord> parquetWriter = AvroParquetWriter
                .<GenericRecord>builder(HadoopOutputFile.fromPath(new Path(filePath), new Configuration()))
                .withCompressionCodec(CompressionCodecName.ZSTD)
                .withSchema(SCHEMA)
                .build();
        //写入数据
        GenericRecord record = new GenericData.Record(SCHEMA);
        record.put("id", inode.getId());
        record.put("parent_id", inode.getParentId());
        record.put("name", inode.getName());
        parquetWriter.write(record);
        parquetWriter.close();
    }

}
