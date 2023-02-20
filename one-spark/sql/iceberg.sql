-- SET

CREATE TABLE IF NOT EXISTS iceberg_catalog.bigdata_iceberg.iceberg_table_1 (
  id bigint,
  data string,
  ts timestamp)
USING iceberg
PARTITIONED BY (date(ts))

SELECT * FROM iceberg_table_1;