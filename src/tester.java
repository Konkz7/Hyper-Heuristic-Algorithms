package src;

import src.heuristics.Greedy;
import src.heuristics.Heuristic;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.awt.geom.*;

import static src.tester.ex;
import static src.tester.numberOf;


public class tester {

    static int numberOf = 100;
    //number of max steps recorded
    static long seed = 1234567;
    static Random rand = new Random(seed);
    static int trials = 1;
    //number of times algorithm is repeated
    static  int count = 1;
    static String instance = "demo_instance1";
    static HyperHeuristic ex = new HyperHeuristic("src/test_instances/" + instance + ".txt", rand, numberOf);

    public static void main(String[] args) throws InterruptedException {


        /* This class is used to run the program and control certain central parameters*/

        try {
            FileWriter myWriter = new FileWriter(instance + "OUTPUT" + ".txt");
            for (int i = 0; i < trials; i++) {
                ex.execute1();
                ex.execute2();
                drawGraph();

                int best = 0;

                for (int s = 0; s < 8; s++) {
                    if (ex.accept[s].move.getObjectiveValue() <= ex.accept[best].move.getObjectiveValue()) {
                        best = s;
                    }
                }
                myWriter.write("#Trial: " + (i + 1) + "\n");
                myWriter.write(ex.accept[best].move.getObjectiveValue() + "\n");
                myWriter.write(ex.accept[best].move.print() + "\n");
                count ++;

            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

    public static void drawGraph(){
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new graphs());
        frame.setTitle(String.valueOf(count));
        frame.setSize(400, 400);
        frame.setLocation(200, 200);
        frame.setVisible(true);
    }
}



