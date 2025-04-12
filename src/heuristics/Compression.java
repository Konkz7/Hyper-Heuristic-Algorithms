package src.heuristics;

import src.Problem;
import src.Solution;

import java.util.Random;

public class Compression extends Heuristic {



    int IOM;
    int DOS;
    double dos = 0.5,iom = 1 ;

    public Compression(Problem problem, Random random) {
        super(problem, random);
        this.name = "Compression";

    }

    /*Orders an index of sets based on their size, then shaves of the smaller sets, then the bigger ones.
    Attempts to jumble the 1s up , then mutates.
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
    public void applyHeuristic(Solution s ){


        int delta = s.getObjectiveValue() ;
        Solution temp = new Solution(problem);
        Solution cross = new Solution(problem);
        int index [] = new int [problem.getNo_sets()];
        int t , mut = 0;
        boolean improved = false;

        for (int p = 0 ; p < problem.getNo_sets() ; p ++){
            index[p] = p;

        }

        parameter();



        for (int y = 0 ; y < problem.getNo_sets(); y++ ){

            for (int x = 0; x < problem.getNo_sets() -1 - y; x++) {
                if(problem.setSizes[x] >= problem.setSizes[x+1]) {

                    t = problem.setSizes[x];
                    problem.setSizes[x] = problem.setSizes[x + 1];
                    problem.setSizes[x + 1] = t;

                    t = index[x];
                    index[x] = index[x + 1];
                    index[x + 1] = t;

                }
            }
        }



        for (int i = 0 ; i < DOS ; i ++){
            int lb , ub;
            int iomcount = 0;


            temp.copy(s);
            temp.setObjectiveValue(s.getObjectiveValue());

            cross.randomInit(rand);

            for (int y = 0; y < problem.getNo_sets(); y++){
                if(cross.getValue()[y] == 1 && temp.getValue()[y] == 0){
                    temp.getValue()[y] = 1;
                    temp.setObjectiveValue(temp.getObjectiveValue() + 1);

                }
            }

            for (lb = 0; lb < problem.getNo_sets() ; lb++ ){
                if( temp.getValue()[index[lb]] == 1){
                    temp.flip(index[lb]);
                    temp.setObjectiveValue(temp.getObjectiveValue() - 1);

                }

                if(!temp.feasible()){
                    temp.flip(index[lb]);
                    temp.setObjectiveValue(temp.getObjectiveValue() + 1);
                    break;
                }
            }

            for (ub = problem.getNo_sets() - 1; ub > 0 ; ub-- ){
                if( temp.getValue()[index[ub]] == 1){
                    temp.flip(index[ub]);
                    temp.setObjectiveValue(temp.getObjectiveValue() - 1);

                }

                if(!temp.feasible()){
                    temp.flip(index[ub]);
                    temp.setObjectiveValue(temp.getObjectiveValue() + 1);
                    break;
                }
            }



            if(temp.feasible() && temp.getObjectiveValue() <= delta){
                delta = temp.getObjectiveValue();
                s.copy(temp);
                s.setObjectiveValue(temp.getObjectiveValue());
                improved = true;
            }



            for (int y = 0 ; y < IOM ; y ++){
                do {
                    mut = rand.nextInt(problem.getNo_sets());
                    iomcount ++;
                }while(temp.getValue()[mut] != 1 || iomcount < 100);
                temp.flip(mut);
                if(!temp.feasible()){temp.flip(mut);}else{temp.setObjectiveValue(temp.getObjectiveValue() - 1);}
            }

            if(temp.getObjectiveValue() <= delta){
                delta = temp.getObjectiveValue();
                s.copy(temp);
                s.setObjectiveValue(delta);
                improved = true;
            }

            if(!improved){return;}


        }


    }
}
