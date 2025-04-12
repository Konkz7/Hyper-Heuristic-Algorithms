package src.heuristics;

import src.Problem;
import src.Solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Shuffle extends Heuristic {

    int IOM;
    int DOS;
    double dos = 0.5,iom = 1 ;


    public Shuffle(Problem problem, Random random) {
        super(problem, random);
        this.name = "Shuffle";
    }

    /*Similar to davis but after each search IOM 1s are added back into the solution*/

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

        int iomcount = 0;

        delta = s.getObjectiveValue();
        Solution temp = new Solution(problem);
        int mut = 0;

        int n = problem.getNo_sets();


        Integer range [] = new Integer[n];

        for (int p = 0 ; p < n ; p ++){
            range[p] = p;

        }

        parameter();



        for (int x = 0 ; x < DOS ; x ++) {
            List<Integer> intList = Arrays.asList(range);

            Collections.shuffle(intList,rand);

            intList.toArray(range);
            for (int i = 0; i < n; i++) {
                if (s.getValue()[range[i]] == 1) {
                    s.flip(range[i]);



                    if (s.feasible()) {
                        delta -= 1;
                    } else {
                        s.flip(range[i]);
                    }
                }
            }

            temp.copy(s);

            for (int y = 0 ; y < IOM ; y ++){
                do {
                    mut = rand.nextInt(n);
                    iomcount ++;
                }while(temp.getValue()[mut] != 0 || iomcount < 100);
                if(iomcount >= 100){break;}
                temp.flip(mut);
            }
            if ( x != DOS -1) {
                s.copy(temp);
                delta += IOM;
            }

        }

        s.setObjectiveValue(delta);
    }
}
