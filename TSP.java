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
		    double dx = (points[i].x-points[i].x);
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
    
    private Point[] points;
    
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
        for (Point point : points) {
            g.setColor(Color.BLACK);
            g.fillOval(point.x, point.y, POINT_SIZE, POINT_SIZE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Points Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	TSP tsp = new TSP();
        frame.getContentPane().add(tsp);
	
	Solver solver = new griddy();
        int[] tour = solver.solveTSP(tsp.connections.getMatrix());
	
	frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
 
 
