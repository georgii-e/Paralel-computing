package task2;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        var size = 500;
        var importantInfo = new int[size];
        for (int i = 0; i < importantInfo.length; i++) {
            importantInfo[i] = i + 1;
        }
        Random random = new Random();
        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(10/*random.nextInt(5000)*/);
            } catch (InterruptedException e) {
            }
        }
        drop.put(-1);
    }
}
