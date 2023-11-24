package org.coastline.one.common.file;


import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.coastline.one.common.model.INode;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2023/10/18
 */
public class TestAvro {

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
        String filePath = "/Users/zouhuajian/data/projects/jay/one-is-all/one-data/avro/inode.avro";
        // 创建Avro数据文件的输出流
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(new GenericDatumWriter<>(SCHEMA));
        dataFileWriter.create(SCHEMA, new File(filePath)); // 替换为您要写入的Avro文件路径
        // 将INode对象转换为GenericRecord并写入Avro文件
        GenericRecord record = new GenericData.Record(SCHEMA);
        record.put("id", inode.getId());
        record.put("parent_id", inode.getParentId());
        record.put("name", inode.getName());
        dataFileWriter.append(record);
        // 关闭文件
        dataFileWriter.close();
    }
}
