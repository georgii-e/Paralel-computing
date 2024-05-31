package task4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;


public class DirSearchKWord extends RecursiveTask<ArrayList<String>> {
    private final String dirPath;
    private final List<String> filePaths;
    private final String[] keyWords;

    public DirSearchKWord(String dirPath, String[] keyWords) {
        this.dirPath = dirPath;
        this.keyWords = keyWords;

        File directory = new File(dirPath);
        File[] files = directory.listFiles();

        filePaths = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                filePaths.add(file.getAbsolutePath());
            }
        }
    }

    @Override
    protected ArrayList<String> compute() {
        var filesTasks = new ArrayList<FileSearchKWord>();


        for (String filePath : filePaths) {
            var task = new FileSearchKWord(filePath, keyWords);
            filesTasks.add(task);

            task.fork();
        }

        var results = new ArrayList<String>();

        for (FileSearchKWord task : filesTasks) {
            if (task.join()) {
                results.add(task.filePath);
            }
        }

        return results;
    }
}
