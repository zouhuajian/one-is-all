CREATE TABLE iceberg_table_1 (
    id   BIGINT,
    data STRING,
    ts   TIMESTAMP
) WITH (
    'connector'='iceberg',
    'catalog-name'='iceberg_catalog',
    'catalog-type'='hadoop',
    'catalog-database'='bigdata_iceberg',
    'warehouse'='hdfs://nn:8020/data/warehouse'
);

SELECT * FROM iceberg_table_1;