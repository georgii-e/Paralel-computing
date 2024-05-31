package task4;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Pattern;

@AllArgsConstructor
public class FileSearchKWord extends RecursiveTask<Boolean> {
    public final String filePath;
    private final String[] keyWords;
    private final List<String> wordsList;
    private final int start;
    private final int end;


    FileSearchKWord(String filePath, String[] keyWords) {
        this.filePath = filePath;
        this.keyWords = keyWords;

        Scanner scanner;
        try {
            scanner = new Scanner(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        var content = scanner.useDelimiter("\\A").next();
        scanner.close();

        wordsList = List.of(content.split("\\s+"));
        start = 0;
        end = wordsList.size();
    }

    @Override
    protected Boolean compute() {
        if (end - start < 200_000) {
            return wordsListContainsSearchWord();
        }

        var middleIndex = (end + start) / 2;

        var leftTask = new FileSearchKWord(
                filePath, keyWords, wordsList, start, middleIndex);
        leftTask.fork();

        var rightTask = new FileSearchKWord(
                filePath, keyWords, wordsList, middleIndex, end);

        return leftTask.join() || rightTask.compute();
    }

    private boolean wordsListContainsSearchWord() {
        var pattern = Pattern.compile("\\p{Punct}");  // any punctuation character.
        for (String str : wordsList) {
            String[] words = pattern.split(str.toLowerCase());
            for (String word : words) {
                for (var keyWord : keyWords) {
                    if (word.equals(keyWord.toLowerCase())) {
                        return true;
                    }
                }

            }
        }

        return false;
    }
}

