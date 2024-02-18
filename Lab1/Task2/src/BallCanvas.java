import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class BallCanvas extends JPanel {
    private ArrayList<Ball> balls = new ArrayList<>();
    @Getter
    private ArrayList<Pocket> pockets;
    private int droppedCounter = 0;
    JLabel droppedCounterLabel;

    public void add(Ball b) {
        this.balls.add(b);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        pockets = generatePockets();
        paintPockets(g2);
        for (Ball ball : balls) {
            if (ball.isInPocket()) {
                droppedCounter++;
            }
        }
        droppedCounterLabel.setText("Dropped balls: " + droppedCounter);
        balls.removeIf(Ball::isInPocket);
        for (Ball b : balls) {
            b.draw(g2);
        }
    }


    private ArrayList<Pocket> generatePockets() {
        ArrayList<Pocket> pockets = new ArrayList<>();
        pockets.add(new Pocket(0, 0, 15));
        pockets.add(new Pocket(0, this.getHeight() - 30, 15));
        pockets.add(new Pocket(this.getWidth() - 30, 0, 15));
        pockets.add(new Pocket(this.getWidth() - 30, this.getHeight() - 30, 15));
        return pockets;
    }

    private void paintPockets(Graphics2D g2) {
        for (Pocket p : pockets) {
            p.draw(g2);
        }
    }


}
