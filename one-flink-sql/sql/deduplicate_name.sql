./bin/sql-client.sh embedded

SET 'sql-client.execution.result-mode' = 'tableau';

CREATE TABLE datagen (
 one_sequence INT,
 one_id INT,
 one_key STRING,
 one_time AS localtimestamp,
 WATERMARK FOR one_time AS one_time
) WITH (
 'connector' = 'datagen',
 -- optional options --
 'rows-per-second'='5',
 'fields.one_sequence.kind'='sequence',
 'fields.one_sequence.start'='1',
 'fields.one_sequence.end'='1000',
 'fields.one_id.min'='1',
 'fields.one_id.max'='1000',
 'fields.one_key.length'='8'
);

SELECT * FROM (
    SELECT
        one_sequence,
        one_id,
        one_key,
        one_time,
        ROW_NUMBER() OVER (
            PARTITION BY one_key ORDER BY one_time
        ) AS rownum
    FROM datagen
)
WHERE rownum = 1;

