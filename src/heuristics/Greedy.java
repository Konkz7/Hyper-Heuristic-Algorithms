package src.heuristics;

import src.Problem;
import src.Solution;
import src.heuristics.Heuristic;

import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class Greedy extends Heuristic {


    public Greedy(Problem problem, Random random) {
        super(problem, random);
        this.name = "Greedy";
    }

    /*Constructs very optimal solution by prioritising sets that cover the variables the best each iteration*/

    @Override
    public void applyHeuristic(Solution s) {

        int n = problem.getNo_sets();
        int buffer [] = new int [problem.getNo_var()];
        int debt, best , bestPayment;


        s.wipe();
        s.setObjectiveValue(0);

        do {
            best = 0;


            bestPayment = omission(buffer);

            for (int f = 0; f < n; f++){
                if(s.getValue()[f] == 0) {
                    s.flip(f);
                    fillBuffer(buffer, s.getValue());
                    debt = omission(buffer);

                    if (debt <= bestPayment) {
                        bestPayment = debt;
                        best = f;
                    }

                    s.flip(f);


                }

                emptyBuffer(buffer);

            }

            s.flip(best);

            s.setObjectiveValue(s.getObjectiveValue() + 1);

        }while(!s.feasible());

    }


    public int omission ( int[] buffer){
        int count = 0;
        for (int i = 0 ; i < problem.getNo_var(); i ++){
            if(buffer[i] == 0){
                count ++;
            }
        }
        return count;
    }

    public void fillBuffer( int [] buffer, int [] value){

        for(int i = 0; i < problem.getNo_sets(); i ++ ){

            if(value[i] == 1){
                for (int j = 0; j < problem.getNo_var(); j++){
                    if (problem.getFrame()[i][j] == 1){
                        buffer[j] = 1;
                    }
                }
            }

        }
    }

    public void emptyBuffer( int [] buffer){

        for(int i = 0; i < problem.getNo_var(); i ++ ){

            buffer[i] = 0;

        }
    }

}
