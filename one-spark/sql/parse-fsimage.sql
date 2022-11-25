
CREATE TABLE source_fsimage (
  Path STRING,
  Replication INT,
  ModificationTime STRING,
  AccessTime STRING,
  PerferredBlockSize BIGINT,
  BlocksCount INT,
  FileSize BIGINT,
  NSQUOTA INT,
  DSQUOTA INT,
  Permission STRING,
  UserName STRING,
  GroupName STRING
) WITH (
 'connector' = 'filesystem',
 'path' = 'file:///Users/zouhuajian/Jay/project/zouhuajian/one-is-all/one-flink-sql/sql/fsimage.csv',
 'format' = 'csv',
 'csv.ignore-parse-errors' = 'true',
 'csv.allow-comments' = 'true'
)

CREATE TABLE sink_print WITH (
 'connector' = 'print'
)
LIKE source_fsimage (EXCLUDING ALL)

INSERT INTO sink_print (
    SELECT * FROM source_fsimage
)