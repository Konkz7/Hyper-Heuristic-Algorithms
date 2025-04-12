package src.heuristics;

import src.Problem;
import src.Solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;



public class Wander extends Heuristic {

    int IOM;
    int DOS;
    double dos = 0.5,iom = 1 ;

    public Wander(Problem problem, Random random) {
        super(problem, random);
        this.name = "Wander";
    }

    /*Starting with an empty solution, then flipping random bits until feasible

     */
    private void parameter() {
        if (iom > 0 && iom < 0.2) {
            IOM = 1;
        } else if (iom < 0.4) {
            IOM = 2;
        } else if (iom < 0.6) {
            IOM = 3;
        } else if (iom < 0.8) {
            IOM = 4;
        } else if (iom < 1) {
            IOM = 5;
        } else {
            IOM = 6;
        }

        if (dos > 0 && dos < 0.2) {
            DOS = 1;
        } else if (dos < 0.4) {
            DOS = 2;
        } else if (dos < 0.6) {
            DOS = 3;
        } else if (dos < 0.8) {
            DOS = 4;
        } else if (dos < 1) {
            DOS = 5;
        } else {
            DOS = 6;
        }

    }


    @Override
    public void applyHeuristic(Solution s) {

        int n = problem.getNo_sets();
        boolean improved = false;
        delta = s.getObjectiveValue();
        Solution temp = new Solution(problem);
        int mut = 0 , count = 0 , x , limit = 1000;


        Integer range[] = new Integer[n];

        for (int p = 0; p < n; p++) {
            range[p] = p;

        }

        parameter();

        for (int d = 0; d < DOS; d++) {
            int iomcount = 0;

            List<Integer> intList = Arrays.asList(range);

            Collections.shuffle(intList,rand);

            intList.toArray(range);

            temp.wipe();

            do {

                x = rand.nextInt(n);
                if(temp.getValue()[x] == 0){count ++;}else{count--;}
                temp.flip(x);

                limit --;



            } while (!temp.feasible() && limit > 0);

            for (int y = 0 ; y < IOM ; y ++){
                do {
                    mut = rand.nextInt(n);
                    iomcount ++;
                }while(temp.getValue()[mut] != 1 || iomcount < 100);
                temp.flip(mut);
                if(!temp.feasible()){temp.flip(mut);}else {count --;}
            }




            if (count <= delta && limit > 0){
                s.copy(temp);
                s.setObjectiveValue(count);
                delta = count;
                improved = true;
            }

            count = 0;


            if(!improved){return;}

        }

    }
}
