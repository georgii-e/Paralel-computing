
public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball) {
        b = ball;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 10000; i++) {
                if (b.isInPocket()) {
//                    this.interrupt();
                    return;
                }
                b.move();
//                System.out.println("Thread name = "+ Thread.currentThread().getName());
                Thread.sleep(3);

            }
        } catch (InterruptedException ex) {
        }
    }
}
