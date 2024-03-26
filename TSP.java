import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TSP extends JPanel {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final int POINT_SIZE = 5;
    private static final int NUM_POINTS = 100;

    private Point[] points;

    public TSP() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        generateRandomPoints();
    }

    private void generateRandomPoints() {
        points = new Point[NUM_POINTS];
        Random random = new Random();
        for (int i = 0; i < NUM_POINTS; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            points[i] = new Point(x, y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Point point : points) {
            g.setColor(Color.RED);
            g.fillOval(point.x, point.y, POINT_SIZE, POINT_SIZE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Points Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TSP());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
