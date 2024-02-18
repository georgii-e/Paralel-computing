public class Main {
    public static void main(String[] args) {
        PrintGood thread1 = new PrintGood("|");
        PrintGood thread2 = new PrintGood("-");

//        PrintThread thread1 = new PrintThread("|");
//        PrintThread thread2 = new PrintThread("-");

        thread1.start();
        thread2.start();

    }
}
