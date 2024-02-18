import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private static final int BLUE_PRIORITY = 1;
    private static final int RED_PRIORITY = 10;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Lab1");
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton priorityTest = new JButton("Priority test");
        JButton buttonStop = new JButton("Stop");
        priorityTest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < 600; i++) {
                    Ball b = new Ball(canvas);
                    b.setColor(Color.blue);
                    canvas.add(b);

                    BallThread thread = new BallThread(b, BLUE_PRIORITY);
                    thread.start();
                    System.out.println("Thread name = " +thread.getName());
                }
                Ball b = new Ball(canvas);
                b.setColor(Color.red);
                canvas.add(b);

                BallThread thread = new BallThread(b, RED_PRIORITY);
                thread.start();
                System.out.println("Thread name = " +thread.getName());
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        buttonPanel.add(priorityTest);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
