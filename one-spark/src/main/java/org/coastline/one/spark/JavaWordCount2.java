/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coastline.one.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class JavaWordCount2 {
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf()
                .setAppName("JavaWordCount")
                .setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> rdd1Data = sc.parallelize(Arrays.asList("1", "2", "3"));
        JavaRDD<String> sample = rdd1Data.sample(false, 0.1);
        sc.broadcast(rdd1Data);
        List<Tuple2<String, Iterable<Integer>>> collect = sc.parallelize(Arrays.asList("one", "two", "two"))
                .flatMap(s -> Arrays.asList(SPACE.split(s)).iterator())
                .map(new Function<String, String>() {
                    @Override
                    public String call(String v1) throws Exception {
                        System.out.println(v1);
                        return "prefix_" + v1;
                    }
                })
                .mapToPair(new PairFunction<String, String, Integer>() {
                    @Override
                    public Tuple2<String, Integer> call(String s) throws Exception {
                        return new Tuple2<>(s, 1);
                    }
                })
                //.mapToPair(s -> new Tuple2<>(s, 1))
                .persist(StorageLevel.DISK_ONLY())
                .groupByKey(1)
                .collect();

        for (Tuple2<String, Iterable<Integer>> stringIterableTuple2 : collect) {
            System.out.println(stringIterableTuple2._1 + " ___ " + stringIterableTuple2._2);
        }
        //sc.stop();
    }

    private void main2() {
        SparkConf conf = new SparkConf().setAppName("JavaWordCount").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("");

        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));

        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?, ?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        sc.stop();
    }
}
