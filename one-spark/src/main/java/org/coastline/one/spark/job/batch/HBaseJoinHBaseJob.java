package org.coastline.one.spark.job.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HBaseJoinHBaseJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseJoinHBaseJob.class);

    public static void main(String[] args) {
        /*SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .master("local[2]")
                .getOrCreate();

        JavaSparkContext jsc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        Configuration sourceHBaseConf = SOURCE_HBASE_CLUSTER.getConfiguration();
        // 跨集群配置, 指定读取HBase所在集群.
        JavaHBaseContext hbaseContext = new JavaHBaseContext(jsc, sourceHBaseConf);

        // 通过Scan获取数据
        JavaRDD<Result> sourceHBaseRDD = hbaseContext.hbaseRDD(TableName.valueOf(SOURCE_HBASE_TABLE), new Scan())
                .map(new Function<Tuple2<ImmutableBytesWritable, Result>, Result>() {
                    @Override
                    public Result call(Tuple2<ImmutableBytesWritable, Result> t) throws Exception {
                        return t._2;
                    }
                });

        sourceHBaseRDD.foreachPartition((it) -> {
            for (int i = 0; i < 100 && it.hasNext(); i++) {
                LOGGER.error(it.next());
            }
        });

        jsc.stop();*/
    }
}
