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

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public final class ParseFSImageJob {

    public static void main(String[] args) throws Exception {
        String  path = "file:///Users/zouhuajian/Jay/project/zouhuajian/one-is-all/one-spark/sql/fsimage.csv";
        SparkSession spark = SparkSession
                .builder()
                .appName("parse-fsimage")
                .master("local[2]")
                .getOrCreate();
        Dataset<Row> csv = spark.read()
                .option("header", "true")
                .option("delimiter", "\t")
                .csv(path);
        csv.show( Integer.MAX_VALUE);
    }

}
