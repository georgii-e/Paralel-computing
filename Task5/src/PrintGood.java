public class PrintGood extends Thread {
    private final String character;
    private static final Object monitor = new Object();

    public PrintGood(String character) {
        this.character = character;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                synchronized (monitor) {
                    monitor.notifyAll();
                    System.out.print(this.character);
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.exit(0);
    }
}
