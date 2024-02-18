public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball) {
        b = ball;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 2000; i++) {
                if (b.isStopped()) {
                    while (b.isStopped()) {
                        Thread.sleep(3);
                    }
                }
                b.move();
//                System.out.println("Thread name = "+ Thread.currentThread().getName());
                Thread.sleep(3);

            }
        } catch (InterruptedException ex) {
        }
    }

}
