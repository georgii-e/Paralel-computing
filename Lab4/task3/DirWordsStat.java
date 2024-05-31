package task3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DirWordsStat extends RecursiveTask<Set<String>> {
    private final String dirPath;

    private final List<String> filePaths = new ArrayList<>();

    public DirWordsStat(String dirPath) {
        this.dirPath = dirPath;

        File directory = new File(dirPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                    filePaths.add(file.getAbsolutePath());
            }
        }
    }

    @Override
    protected Set<String> compute() {
        var tasks = new ArrayList<RecursiveTask<Set<String>>>();

        var filesToResolve = new ArrayList<String>();
        int c = 0;
        for (var filePath : filePaths) {
            filesToResolve.add(filePath);
            c++;

            if (c >= 2) {
                var task = new FileWordsStat(new ArrayList<>(filesToResolve));
                tasks.add(task);
                task.fork();

                c = 0;
                filesToResolve.clear();
            }
        }
        if (!filesToResolve.isEmpty()) {
            var task = new FileWordsStat(new ArrayList<>(filesToResolve));
            tasks.add(task);
            task.fork();
        }

        var setsToIntersect = new ArrayList<Set<String>>();
        for (RecursiveTask<Set<String>> task : tasks) {
            setsToIntersect.add(task.join());
        }

        Set<String> intersectionOfSets = new HashSet<>(setsToIntersect.get(0));
        for (int i = 1; i < setsToIntersect.size(); i++) {
            intersectionOfSets.retainAll(setsToIntersect.get(i));
        }

        return intersectionOfSets;
    }
}
