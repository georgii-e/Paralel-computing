public class PrintThread extends Thread {
    private final String character;

    public PrintThread(String character) {
        this.character = character;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                System.out.print(this.character);
            }
            System.out.println();
        }
    }
}
