package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import static src.tester.ex;
import static src.tester.numberOf;

public class graphs extends JPanel {

    protected void paintComponent(Graphics grf) {


        int marg = 60;
        //create instance of the Graphics to use its methods
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D) grf;

        //Sets the value of a single preference for the rendering algorithms.
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // get width and height
        int width = getWidth();
        int height = getHeight();

        // draw graph
        graph.draw(new Line2D.Double(marg, marg, marg, height - marg));
        graph.draw(new Line2D.Double(marg, height - marg, width - marg, height - marg));

        //find value of x and scale to plot points
        double x = (double) (width - 2 * marg) / (numberOf - 1);
        double scale = (double) (height - 2 * marg) / getMax(ex.currentOf);





        // set points to the graph
        for (int i = 0; i < ex.numberOf; i++) {
            if(ex.bestOf[i] == 0){
                break;
            }
            graph.setPaint(Color.RED);
            double x1 = marg + i * x;
            double y1 = height - marg - scale * ex.bestOf[i];
            graph.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 4, 4));
            graph.setPaint(Color.BLUE);
            double x2 = marg + i * x;
            double y2 = height - marg - scale * ex.currentOf[i];
            graph.fill(new Ellipse2D.Double(x2 - 2, y2 - 2, 4, 4));
        }
    }

    private int getMax(int[] arr) {
        int max = -Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max)
                max = arr[i];

        }
        return max;
    }

}
