package src.heuristics;

import src.Problem;
import src.Solution;
import src.heuristics.Heuristic;

import java.util.Random;

public class Bit_Shift extends Heuristic {

    int IOM;
    int DOS;
    double dos = 0.5,iom = 1 ;

    public Bit_Shift(Problem problem, Random random) {
        super(problem, random);
        this.name = "Bit_Shift";
    }

    /*shifts solution and mutates until the solution becomes infeasible
    the same happens with the other side and the side which yields the best results is used
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


    public void shiftR (Solution s){
            int n = problem.getNo_sets() - 1;

            for(int i=0 ; i<=n ; i++)
            {
                int temp = s.getValue()[i];
                s.getValue()[i] = s.getValue()[n];
                s.getValue()[n] = temp;
                
            }

            s.getValue()[0] = 0;


    }
    
    public void shiftL  (Solution s){


        int n = problem.getNo_sets() ;

        for(int i=n-1 ; i>=0 ; i--)
        {
            int temp2 = s.getValue()[0];
            s.getValue()[0] = s.getValue()[i];
            s.getValue()[i] = temp2;

        }

        s.getValue()[n - 1] = 0;

    }

    @Override
    public void applyHeuristic(Solution s) {

        int iomcount = 0;

        parameter();

        int mut , holder;
        Solution tempR = new Solution(problem);
        Solution tempL = new Solution(problem);
        Solution sR = new Solution(problem);
        Solution sL = new Solution(problem);


        tempL.copy(s);
        tempL.setObjectiveValue(s.getObjectiveValue());
        tempR.copy(s);
        tempR.setObjectiveValue(s.getObjectiveValue());
        sR.copy(tempR);
        sL.copy(tempL);


        do{
            holder = tempR.getValue()[problem.getNo_sets()-1];
            shiftR(tempR);
            if(tempR.feasible() ){
                if(holder == 1){
                         tempR.setObjectiveValue(tempR.getObjectiveValue() - 1);
                }
            }else{
                break;
            }



            //could be moved to mutation stage in GA
            for (int j = 0; j < IOM ; j++){
                do {
                    iomcount ++;
                   mut = rand.nextInt(problem.getNo_sets());
                }while(tempR.getValue()[mut] != 1 || iomcount < 100);
                tempR.flip(mut);
                if(!tempR.feasible()){tempR.flip(mut);}else{tempR.setObjectiveValue(tempR.getObjectiveValue() - 1);}
            }

            sR.copy(tempR);


        }while(tempR.feasible());

        do{
            holder = tempL.getValue()[0];
            shiftL(tempL);

            if(tempL.feasible() ){
                if(holder == 1){
                    tempL.setObjectiveValue(tempL.getObjectiveValue() - 1);
                }
            }else{
                break;
            }

            for (int j = 0; j < IOM ; j++){
                do {
                    iomcount ++;
                    mut = rand.nextInt(problem.getNo_sets());

                }while(tempL.getValue()[mut] != 1 || iomcount < 100);
                if(iomcount >= 100){break;}
                tempL.flip(mut);
                if(!tempL.feasible()){tempL.flip(mut);}else{tempL.setObjectiveValue(tempL.getObjectiveValue() - 1);}
            }

            sL.copy(tempL);

        }while(tempL.feasible());


        if(tempL.getObjectiveValue() <= tempR.getObjectiveValue() ){
            s.copy(sL);
            s.setObjectiveValue(tempL.getObjectiveValue());

        }else{
            s.copy(sR);
            s.setObjectiveValue(tempR.getObjectiveValue());
        }



    }
}
