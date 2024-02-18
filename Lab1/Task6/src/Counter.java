public class Counter {
    private int count = 0;
    private final Object lock = new Object();

    public synchronized void increment() {
        count++;
    }

    public synchronized void decrement() {
        count--;
    }

    public void incSyncBlock() {
        synchronized (lock) {
            count++;
        }
    }

    public void decSyncBlock() {
        synchronized (lock) {
            count--;
        }
    }

    public synchronized int getCount() {
        return count;
    }

    public void badInc() {
        count++;
    }

    public void badDec() {
        count--;
    }
}
