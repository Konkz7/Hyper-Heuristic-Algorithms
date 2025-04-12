package src.heuristics;

import src.Problem;
import src.Solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Davisbit extends Heuristic {

    int IOM;
    int DOS;
    double dos = 0.5,iom = 1 ;

    public Davisbit(Problem problem, Random random) {
        super(problem, random);
        this.name = "Davisbit";
    }

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

        boolean improved = false;
        int n = problem.getNo_sets();

        Integer range [] = new Integer[n];

        for (int p = 0 ; p < n ; p ++){
            range[p] = p;

        }

        parameter();

        // need to try each bit exactly once
        for (int d = 0 ; d < DOS; d++ ) {

            List<Integer> intList = Arrays.asList(range);

            Collections.shuffle(intList,rand);

            intList.toArray(range);



            for (int i = 0; i < n; i++) {

                s.flip(range[i]);




                if (s.feasible() && s.getValue()[range[i]] == 0) {
                    improved = true;
                    s.setObjectiveValue(s.getObjectiveValue() - 1);
                } else {
                    s.flip(range[i]);
                }
            }

            if(!improved){
                return;
            }


        }

    }

}
