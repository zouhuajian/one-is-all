package org.coastline.one.spark.job.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jay.H.Zou
 * @date 2023/01/09
 */
public class KVJoinHBaseExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(KVJoinHBaseExample.class);

    private static final String DIMENSION_HBASE_TABLE = "test_table";

    // 测试 HOST, 这边填的是开发机. 使用 nc -l 9999 启动一个端口
    // 输入：000179729f79002e872257d22237f78b1108:my_source_value
    private static String INPUT_HOST = "10.5.154.7";
    private static int INPUT_PORT = 9999;

    /*public static void main(String[] args) throws IOException, InterruptedException {
        SparkConf conf = new SparkConf()
                .setAppName("KVJoinHBaseExample");
        // 本地开发时打开
        // conf.setMaster("local[2]");
        // LoginUtil.login();

        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));

        JavaReceiverInputDStream<String> lines = jssc.socketTextStream(INPUT_HOST, INPUT_PORT);
        JavaDStream<Tuple2<String, String>> kvDStream = lines.flatMap(new FlatMapFunction<String, Tuple2<String, String>>() {
            @Override
            public Iterator<Tuple2<String, String>> call(String value) throws Exception {
                String[] split = value.split(":");
                return Lists.newArrayList(new Tuple2<>(split[0].trim(), split[1].trim())).iterator();
            }
        });

        JavaHBaseContext dimHC = new JavaHBaseContext(jssc.sparkContext(), new Configuration());

        // get function
        Function<Tuple2<String, String>, Get> makeGet = kv -> {
            Get get = new Get(Bytes.toBytes(kv._1()));
            get.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("col1"));
            return get;
        };

        // convert dim result，此处仅查询了一列，可以增加多列，相应代码需要调整
        Function<Result, Tuple2<String, String>> convertResultFunction = result -> {
            String rowKey = Bytes.toString(result.getRow());
            String c1 = Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("col1")));
            return new Tuple2<>(rowKey, c1);
        };
        JavaDStream<Tuple2<String, String>> selectDStream =
                dimHC.streamBulkGet(TableName.valueOf(DIMENSION_HBASE_TABLE), 10, kvDStream, makeGet, convertResultFunction);

        JavaPairDStream<String, Tuple2<String, String>> selectPairDStream = selectDStream.mapToPair(new PairFunction<Tuple2<String, String>, String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, Tuple2<String, String>> call(Tuple2<String, String> tuple2) throws Exception {
                return new Tuple2<>(tuple2._1(), new Tuple2<>(tuple2._1(), tuple2._2()));
            }
        });

        JavaPairDStream<String, Tuple2<String, String>> kvPairDStream = kvDStream.mapToPair(new PairFunction<Tuple2<String, String>, String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, Tuple2<String, String>> call(Tuple2<String, String> tuple2) throws Exception {
                return new Tuple2<>(tuple2._1(), new Tuple2<>(tuple2._1(), tuple2._2()));
            }
        });

        JavaDStream<Tuple3<String, String, String>> joinKVAndDim = kvPairDStream.join(selectPairDStream)
                .map(new Function<Tuple2<String, Tuple2<Tuple2<String, String>, Tuple2<String, String>>>, Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call(Tuple2<String, Tuple2<Tuple2<String, String>, Tuple2<String, String>>> v1) throws Exception {
                        String key = v1._1();
                        Tuple2<Tuple2<String, String>, Tuple2<String, String>> joinTuple2 = v1._2();
                        Tuple2<String, String> source = joinTuple2._1();
                        Tuple2<String, String> dim = joinTuple2._2();
                        return new Tuple3<>(key, source._2(), dim._2());
                    }
                });
        joinKVAndDim.foreachRDD(new VoidFunction<JavaRDD<Tuple3<String, String, String>>>() {
            @Override
            public void call(JavaRDD<Tuple3<String, String, String>> tuple3JavaRDD) throws Exception {
                tuple3JavaRDD.foreach(record -> {
                    String key = record._1();
                    String sourceValue = record._2();
                    String dimValue = record._3();
                    System.err.println("key=" + key + "\nsource-value=" + sourceValue + "\ndim-col1=" + dimValue);
                });
            }
        });

        jssc.start();
        jssc.awaitTermination();
    }*/
}

