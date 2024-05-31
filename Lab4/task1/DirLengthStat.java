package task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirLengthStat extends RecursiveTask<HashMap<Integer, Integer>> {
    private final List<String> filePaths;
    public DirLengthStat(String dirPath) {
        try (Stream<Path> walk = Files.walk(Paths.get(dirPath))) {
            filePaths = walk.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading files");
        }
    }

    @Override
    protected HashMap<Integer, Integer> compute() {
        var tasks = new ArrayList<FileLengthStat>();

        for(String filePath : filePaths) {
            var task = new FileLengthStat(filePath);
            task.fork();
            tasks.add(task);
        }

        var result = new HashMap<Integer, Integer>();

        for(FileLengthStat task : tasks) {
            task.join().forEach((lengthKey, count) ->
                    result.merge(lengthKey, count, Integer::sum)
            );
        }

        return result;
    }
}
