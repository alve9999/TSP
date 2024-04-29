import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.lang.Math;
import java.util.Timer;
import java.util.TimerTask;

class FloatPoint{
    public float x,y;
    FloatPoint(float x,float y){
        this.x = x;
        this.y = y;
    }
}

class PointMatrix {
    private double[][] matrix;

    public PointMatrix(FloatPoint[] points) {
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

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int POINT_SIZE = 5;
    private static final int NUM_POINTS = 100;
    private static PointMatrix connections;
    
    private FloatPoint[] points;
    private int[] tour;
    public TSP() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        generateRandomPoints();
	    connections = new PointMatrix(points);
    }
    
    private void generateRandomPoints() {
        points = new FloatPoint[NUM_POINTS];
        Random random = new Random();
        for (int i = 0; i < NUM_POINTS; i++) {
            float x = random.nextFloat(10.0f);
            float y = random.nextFloat(10.0f);
            points[i] = new FloatPoint(x, y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (FloatPoint point : points) {
            g.setColor(Color.BLACK);
            g.fillOval((int) (point.x*WIDTH/10), (int) (point.y*HEIGHT/10), POINT_SIZE, POINT_SIZE);
        }
	g.setColor(Color.BLUE);
        for (int i = 0; i < tour.length - 1; i++) {
            FloatPoint p1 = points[tour[i]];
            FloatPoint p2 = points[tour[i + 1]];
            g.drawLine((int) (p1.x*WIDTH/10 +POINT_SIZE/2), (int) (p1.y*HEIGHT/10 + POINT_SIZE/2), (int) (p2.x*WIDTH/10 + POINT_SIZE/2), (int) (p2.y*HEIGHT/10 + POINT_SIZE/2));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Points Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    TSP tsp = new TSP();
	    Solver solver = new SimulatedAnneling();
        TimerTask stop = new TimerTask() {
            @Override
            public void run() {
                solver.lock.lock();
                solver.timer=false;
                solver.lock.unlock();
            }
        };

        Timer timer = new Timer();
        timer.schedule(stop, 10000);
        tsp.tour = solver.solveTSP(tsp.connections.getMatrix());

        frame.getContentPane().add(tsp);
	
	    frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
 
 
