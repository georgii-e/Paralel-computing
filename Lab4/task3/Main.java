package task3;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String DIRECTORY_PATH = "Lab4/texts";

    public static void main(String[] args) {
        var pool = ForkJoinPool.commonPool();
        var task = new DirWordsStat(DIRECTORY_PATH);

        Set<String> words = pool.invoke(task);
        pool.shutdown();

        System.out.println("Common words for all files: " + words.toString());
        System.out.println("Total amount: " + words.size());
    }
}
