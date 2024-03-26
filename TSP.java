import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.lang.Math;

class PointMatrix {
    private double[][] matrix;

    public PointMatrix(Point[] points) {
        matrix = new double[points.length][points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i != j) {
		    double dx = (points[i].x-points[j].x);
		    double dy = (points[i].y-points[j].y);
                    matrix[i][j] = Math.sqrt(dx*dx+dy*dy);
                } else {
                    matrix[i][j] = 0.0;
                }
            }
        }
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%.2f\t", matrix[i][j]);
            }
            System.out.println();
        }
    }
}

public class TSP extends JPanel {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final int POINT_SIZE = 5;
    private static final int NUM_POINTS = 100;
    private static PointMatrix connections;
    
    public Point[] points;
    public int[] index;
    
    public TSP() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        generateRandomPoints();
	connections = new PointMatrix(points);
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
        Graphics2D g2d = (Graphics2D) g;

        // Draw points
        g.setColor(Color.RED);
        for (Point point : points) {
            g.fillOval(point.x - 5, point.y - 5, 10, 10);
        }

        // Draw path
        g.setColor(Color.BLUE);
        for (int i = 0; i < index.length - 1; i++) {
            int index1 = index[i];
            int index2 = index[i + 1];
            Point p1 = points[index1];
            Point p2 = points[index2];
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Points Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	TSP tsp = new TSP();
	Solver solver = new Opt2();
        int[] tour = solver.solveTSP(tsp.connections.getMatrix());
	tsp.index = tour;

	frame.getContentPane().add(tsp);
	
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
 
 
