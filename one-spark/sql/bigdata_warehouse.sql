-- dwd hdfs path detail
CREATE TABLE `dwd_hdfs_detail_ext`
(
    `path`        string,
    `replication` int,
    `mtime`       string,
    `atime`       string,
    `blocksize`   bigint,
    `blockscount` bigint,
    `filesize`    bigint,
    `nsquota`     bigint,
    `dsquota`     bigint,
    `permission`  string,
    `username`    string,
    `groupname`   string,
    `ext_cols`    map<string,string>
)
    PARTITIONED BY
        (
        `dt` string,
        `cluster` string,
        `ns` string,
        `type` string
        )
    ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
    STORED AS INPUTFORMAT 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat'
        OUTPUTFORMAT
            'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat'
    LOCATION
        'qbfs://online01/warehouse/bigdata_dataops.db/dwd_hdfs_detail_ext'
    TBLPROPERTIES
        (
        'bucketing_version' = '2',
        'spark.sql.create.version' = '3.1.1',
        'spark.sql.sources.schema.numPartCols' = '4',
        'spark.sql.sources.schema.numParts' = '1',
        'spark.sql.sources.schema.part.0' =
                '{type:struct,fields:[{name:path,type:string,nullable:true,metadata:{}},{name:replication,type:integer,nullable:true,metadata:{}},{name:mtime,type:string,nullable:true,metadata:{}},{name:atime,type:string,nullable:true,metadata:{}},{name:blockSize,type:long,nullable:true,metadata:{}},{name:blocksCount,type:long,nullable:true,metadata:{}},{name:fileSize,type:long,nullable:true,metadata:{}},{name:nsQuota,type:long,nullable:true,metadata:{}},{name:dsQuota,type:long,nullable:true,metadata:{}},{name:permission,type:string,nullable:true,metadata:{}},{name:userName,type:string,nullable:true,metadata:{}},{name:groupName,type:string,nullable:true,metadata:{}},{name:ext_cols,type:{type:map,keyType:string,valueType:string,valueContainsNull:true},nullable:true,metadata:{}},{name:dt,type:string,nullable:true,metadata:{}},{name:cluster,type:string,nullable:true,metadata:{}},{name:ns,type:string,nullable:true,metadata:{}},{name:type,type:string,nullable:false,metadata:{}}]}',
        'spark.sql.sources.schema.partCol.0' = 'dt',
        'spark.sql.sources.schema.partCol.1' = 'cluster',
        'spark.sql.sources.schema.partCol.2' = 'ns',
        'spark.sql.sources.schema.partCol.3' = 'type'
        );