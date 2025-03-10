package week1.spark.labs.wordCountMapReduce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class demonstrates a simple MapReduce implementation in Java to count
 * the frequency of words in a text file.
 * It consists of methods to:
 * - stream(): Read a file line by line and return an array of lines.
 * - mapper(String line): Map a single line of text to a map of word counts.
 * - mapper(String[] lines): Map an array of lines to a list of word count maps.
 * - reduce(): Reduce a list of word count maps to a single word count map.
 */

public class WordCountMapReduce {

  static String[] stream(String filename) throws IOException {

    String[] result = new String[10];
    int count = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        result[count] = line;
        count++;
      }
    }
    return result;
  }

  static Map<String, Integer> mapper(String line) {
    HashMap<String, Integer> result = new HashMap<>();
    String[] words = line.split(" ");

    for (String word : words) {
      result.put(word, result.getOrDefault(word, 0) + 1);
    }
    return result;
  }

  static List<Map<String, Integer>> mapper(String[] lines) {
    List<Map<String, Integer>> result = new ArrayList<>();

    for (String line : lines) {
      result.add(mapper(line));
    }
    return result;
  }

  static Map<String, Integer> reduce(List<Map<String, Integer>> inputs) {
    HashMap<String, Integer> result = new HashMap<>();

    for (Map<String, Integer> input : inputs) {
      for (Map.Entry<String, Integer> entry : input.entrySet()) {
        result.put(entry.getKey(), result.getOrDefault(entry.getKey(), 0) + entry.getValue());
      }
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
    String filePath = "sample.txt";
    String[] streams = WordCountMapReduce.stream(filePath);

    List<Map<String, Integer>> inputs = WordCountMapReduce.mapper(streams);
    Map<String, Integer> result = WordCountMapReduce.reduce(inputs);

    System.out.println("word count result is ");
    for (Map.Entry<String, Integer> word : result.entrySet()) {
      System.out.println(word.getKey() + " -> " + word.getValue());
    }

  }

}
