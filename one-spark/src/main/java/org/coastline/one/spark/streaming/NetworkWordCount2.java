package org.coastline.one.spark.streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Jay.H.Zou
 * @date 2022/8/1
 */
public class NetworkWordCount2 {

    /**
     * nc -lk 9999
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // Create a local StreamingContext with two working thread and batch interval of 1 second
        SparkSession sparkSession = SparkSession.builder()
                .master("spark://spark-master:7077")
                .appName("test-application")
                .config("spark.jars.packages", "io.acryl:datahub-spark-lineage:0.8.23")
                .config("spark.extraListeners", "datahub.spark.DatahubSparkListener")
                .config("spark.datahub.rest.server", "http://localhost:8080")
                .enableHiveSupport()
                .getOrCreate();
        SparkConf conf = new SparkConf()
                .setMaster("local[2]")
                //.setMaster("spark://xxx:7077")
                .setAppName("NetworkWordCount")
                .set("spark.jars.packages", "io.acryl:datahub-spark-lineage:0.8.43")
                .set("spark.extraListeners", "datahub.spark.DatahubSparkListener")
                .set("spark.datahub.rest.server", "http://localhost:8080");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(2));
        // Create a DStream that will connect to hostname:port, like localhost:9999
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);
        // Split each line into words
        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });

        // Count each word in each batch
        JavaPairDStream<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey(Integer::sum).cache();

        // Print the first ten elements of each RDD generated in this DStream to the console
        wordCounts.print();
        jssc.start();              // Start the computation
        jssc.awaitTermination();   // Wait for the computation to terminate
    }
}
