package com.apachesparks;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import java.util.Map;

public class App {

  public static void main(String[] args) {

    if (args.length < 1) {
      System.err.println("Usage: App <input_file>");
      System.exit(1);
    }

    String inputPath = args[0];

    SparkConf conf = new SparkConf()
            .setAppName("JavaWordCount")
            .setMaster("spark://spark-master:7077")
            .set("spark.executor.memory", "512m")
            .set("spark.executor.cores", "1");

    try (JavaSparkContext sc = new JavaSparkContext(conf)) {

      JavaRDD<String> lines = sc.textFile(inputPath);


      JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(s.split(" ")).iterator());

      JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
        public Tuple2<String, Integer> call(String s) {
          return new Tuple2<>(s, 1);
        }
      });

      JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
        public Integer call(Integer i1, Integer i2) {
          return i1 + i2;
        }
      });

      Map<String, Integer> wordCounts = counts.collectAsMap();
      for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
        System.out.println(entry.getKey() + " : " + entry.getValue());
      }

      sc.stop();
    }
  }
}
