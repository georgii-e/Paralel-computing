package task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String DIRECTORY_PATH = "Lab4/texts";

    public static void main(String[] args) {
        measureTime(() -> seqAnalyseDir(DIRECTORY_PATH), "sequential");
        measureTime(() -> parAnalyseDir(DIRECTORY_PATH), "parallel");
    }

    private static void seqAnalyseDir(String dirPath) {
        var filePaths = new ArrayList<Path>();
        try {
            Files.walk(Paths.get(dirPath))
                    .filter(Files::isRegularFile)
                    .forEach(filePaths::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        var result = new HashMap<Integer, Integer>();

        for (Path filePath : filePaths) {
            try {
                var content = Files.readString(filePath);
                String[] words = content.split("\\s+");

                for (String word : words) {
                    int length = word.length();
                    result.put(length, result.getOrDefault(length, 0) + 1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        printStatistics(result);
    }

    private static void parAnalyseDir(String dirPath) {
        var pool = ForkJoinPool.commonPool();
        var task = new DirLengthStat(dirPath);

        var result = pool.invoke(task);

        pool.shutdown();
        printStatistics(result);
    }

    private static void measureTime(Runnable runnable, String type) {
        var startTime = System.currentTimeMillis();
        runnable.run();
        var endTime = System.currentTimeMillis();
        System.out.println("Time taken for " + type + " analyse " + ": " + (endTime - startTime) + " ms");
        System.out.println();
    }

    public static void printStatistics(HashMap<Integer, Integer> map) {
        var wordsAmount = 0;
        var charsAmount = 0.0;

        for (int lengthKey : map.keySet()) {
            wordsAmount += map.get(lengthKey);
            charsAmount += map.get(lengthKey) * lengthKey;
        }

        var meadWordsLength = charsAmount / wordsAmount;


        System.out.println("Total amount of words: " + wordsAmount);
        System.out.println("Avg length of words: " + Math.round(meadWordsLength * 100.0) / 100.0);
    }
}
