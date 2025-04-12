package src;


import src.heuristics.*;

import java.util.Random;

public class HyperHeuristic {


    String instance;
    Problem pp ;

    int originalValue;

    int numberOf , bestOf[] , currentOf[];

    Solution initial ;
    Solution candidate ;


    int objectives[] = new int[8];
    long times[] = new long[8];

    int total[] = new int[8];
    int rank[] = new int[8];
    Heuristic[] heuristics = new Heuristic [8];

    MoveAcceptance accept [] = new MoveAcceptance[8];

    Random rand;
    int count = 0;
    boolean f [] = new boolean [8];

    public HyperHeuristic(String instance,Random random , int numberOf){

        this.rand = random;
        this.instance = instance;
        this.pp = new Problem(instance);
        this.candidate = new Solution(pp);
        this.initial = new Solution(pp);
        this.numberOf = numberOf;
        this.bestOf = new int[numberOf];
        this.currentOf = new int[numberOf];
        System.out.println("|Heuristic|Objective value rank|Time taken rank|Total rank|");


    }


//Method for executing "stage 1" low level heuristic selection
    public void execute1() throws InterruptedException {


        long START = System.currentTimeMillis();
        System.out.println("First stage");

        //initial solution
        initial.randomInit(rand);




        for (int y = 0 ; y < 8 ; y ++){
            accept[y] = new MoveAcceptance(pp,initial);
        }

        //making heuristic objects
        heuristics[0] = new Bit_Shift(pp,rand);
        heuristics[1] = new Shuffle(pp,rand);
        heuristics[2] = new Wander(pp,rand);
        heuristics[3] = new Davisbit(pp,rand);
        heuristics[4] = new Compression(pp,rand);
        heuristics[5] = new Greedy(pp,rand);
        heuristics[6] = new Heuristic(pp,rand) {

            @Override
            public void applyHeuristic(Solution s) {

            }

        };
        heuristics[7] = new Heuristic(pp,rand) {

            @Override
            public void applyHeuristic(Solution s) {

            }
        };

        //boolean finished = false;

        // executes steps until a certain time limit is reached
        while(!(System.currentTimeMillis() >= START + 5000)){
            candidate.randomInit(rand);
            originalValue = candidate.getObjectiveValue();


            for (int n = 0; n < 8; n++) {
                cancel(n);
            }


            process(candidate,originalValue,0,accept[0]);


            process(candidate,originalValue,1,accept[1]);


            process(candidate,originalValue,2,accept[2]);

            process(candidate,originalValue,3,accept[3]);


            process(candidate,originalValue,4,accept[4]);


            process(candidate,originalValue,5,accept[5]);








            //Objective comparison of all heuristics in this step
            int rankO[] = rankings(objectives);
            heuristics[rankO[5]].setObjective_score(-1);
            for(int x = 0 ; x < 6; x++) {
                if (objectives[rankO[4]] == objectives[rankO[x]]) {
                    heuristics[rankO[x]].setObjective_score(0);
                }
            }
            for(int x = 0 ; x < 6; x++) {
                if (objectives[rankO[3]] == objectives[rankO[x]]) {
                    heuristics[rankO[x]].setObjective_score(1);
                }
            }
            for(int x = 0 ; x < 6; x++) {
                if (objectives[rankO[2]] == objectives[rankO[x]]) {
                    heuristics[rankO[x]].setObjective_score(2);
                }
            }
            for(int x = 0 ; x < 6; x++) {
                if (objectives[rankO[1]] == objectives[rankO[x]]) {
                    heuristics[rankO[x]].setObjective_score(3);
                }
            }
            for(int x = 0 ; x < 6; x++) {
                if (objectives[rankO[0]] == objectives[rankO[x]]) {
                    heuristics[rankO[x]].setObjective_score(4);
                }
            }


            //TIME
            int rankT[] = rankingsT(times);
            heuristics[rankT[5]].setTime_score(0);
            heuristics[rankT[4]].setTime_score(0);
            heuristics[rankT[3]].setTime_score(0);
            for(int x = 0 ; x < 6; x++) {
                if (times[rankT[2]] == times[rankT[x]]) {
                    heuristics[rankT[x]].setTime_score(0);
                }
            }
            for(int x = 0 ; x < 6; x++) {
                if (times[rankT[1]] == times[rankT[x]]) {
                    heuristics[rankT[x]].setTime_score(1);
                }
            }
            for(int x = 0 ; x < 6; x++) {
                if (times[rankT[0]] == times[rankT[x]]) {
                    heuristics[rankT[x]].setTime_score(1);
                }
            }


            /*if(!(f[0] && f[1] && f[2] && f[3] && f[4])){
                finished = true;
            }*/


        }

        // ranks heuristics in another array based on overall performance
        for(int r = 0 ; r< 6 ; r++){
            total[r] = heuristics[r].getTotal_score();
        }


        rank = rankings(total);


        heuristics[0].print();
        heuristics[1].print();
        heuristics[2].print();
        heuristics[3].print();
        heuristics[4].print();
        heuristics[5].print();

    }

    public void execute2 () throws InterruptedException {

        long START = System.currentTimeMillis();
        System.out.println("Second stage");
        initial.randomInit(rand);

        /*Doing a second stage low level selection where the two best heuristics are picked
        and are compared to themselves merged.
         */

        //resetting best two heuristic fields
        heuristics[rank[7]].reset_score();
        heuristics[rank[6]].reset_score();
        resetMA(accept[rank[7]]);
        resetMA(accept[rank[6]]);

        heuristics[6].name = heuristics[rank[7]].getName() + " " + heuristics[rank[6]].getName();
        heuristics[7].name = heuristics[rank[6]].getName() + " " + heuristics[rank[7]].getName();

        //for graphing data
        for(int u = 0; u < numberOf; u++){
            bestOf[u] = 0;
            currentOf[u] = 0;
        }

        count = 0;




        //boolean finished = false;

        while(!(System.currentTimeMillis() >= START + 500)){
            candidate.randomInit(rand);
            originalValue = candidate.getObjectiveValue();

            if (count < numberOf) {
                currentOf[count] = candidate.getObjectiveValue();
            }

            for (int n = 0 ; n < 8; n++){
                cancel(n);
            }


            process(candidate,originalValue,rank[7],accept[rank[7]]);

            process(candidate,originalValue,rank[6],accept[rank[6]]);

            process2(candidate,originalValue,rank[7],rank[6] ,accept[6],6);

            process2(candidate,originalValue,rank[6],rank[7] ,accept[7],7);


            //Objective comparison

            int rankO[] = rankings(objectives);
            heuristics[rankO[3]].setObjective_score(1);
            for(int x = 0 ; x < 4; x++) {
                if (objectives[rankO[2]] == objectives[rankO[x]]) {
                    heuristics[rankO[x]].setObjective_score(2);
                }
            }
            for(int x = 0 ; x < 4; x++) {
                if (objectives[rankO[1]] == objectives[rankO[x]]) {
                    heuristics[rankO[x]].setObjective_score(3);
                }
            }
            for(int x = 0 ; x < 4; x++) {
                if (objectives[rankO[0]] == objectives[rankO[x]]) {
                    heuristics[rankO[x]].setObjective_score(4);
                }
            }



            int rankT[] = rankingsT(times);
            heuristics[rankT[3]].setTime_score(0);
            for(int x = 0 ; x < 4; x++) {
                if (times[rankT[2]] == times[rankT[x]]) {
                    heuristics[rankT[x]].setTime_score(0);
                }
            }
            for(int x = 0 ; x < 4; x++) {
                if (times[rankT[1]] == times[rankT[x]]) {
                    heuristics[rankT[x]].setTime_score(1);
                }
            }
            for(int x = 0 ; x < 4; x++) {
                if (times[rankT[0]] == times[rankT[x]]) {
                    heuristics[rankT[x]].setTime_score(2);
                }
            }

            if(count < numberOf) {
                int best = 0;
                int arr [] = {6, 7, rank[6], rank[7]};

                for (int s = 0; s < 4; s++) {
                    if (accept[arr[s]].move.getObjectiveValue() <= accept[arr[best]].move.getObjectiveValue()) {
                        best = s;
                    }
                }
                bestOf[count] = accept[arr[best]].move.getObjectiveValue();
            }


            /*if(!(f[6] && f[7] && f[rank[7]] && f[rank[6]])){
                finished = true;
            }*/

            count++;
        }

        heuristics[6].print();
        heuristics[7].print();
        heuristics[rank[6]].print();
        heuristics[rank[7]].print();
        System.out.println("---------");





    }


    public int[] rankings(int[] rank){

        int index [] = new int [8];
        int t ;

        for (int p = 0 ; p < 8 ; p ++){
            index[p] = p;

        }


        for (int y = 0 ; y < 8; y++ ){

            for (int x = 0; x < 7; x++) {
                if(rank[x] >= rank[x+1]) {

                    t = rank[x];
                    rank[x] = rank[x + 1];
                    rank[x + 1] = t;

                    t = index[x];
                    index[x] = index[x + 1];
                    index[x + 1] = t;

                }
            }
        }
        
        return index;
    }

    public int[] rankingsT(long[] rank){

        int index [] = new int [8];
        int t ;
        long f;

        for (int p = 0 ; p < 8 ; p ++){
            index[p] = p;

        }


        for (int y = 0 ; y < 8; y++ ){

            for (int x = 0; x < 8 -1 - y; x++) {
                if(rank[x] >= rank[x+1]) {

                    f = rank[x];
                    rank[x] = rank[x + 1];
                    rank[x + 1] = f;

                    t = index[x];
                    index[x] = index[x + 1];
                    index[x + 1] = t;

                }
            }
        }

        return index;
    }



    public void process(Solution candidate, int originalValue, int index, MoveAcceptance m){


        /* process of applying the heuristic to the solution and then apply late acceptance after
        while also measuring the performance of the heuristic
         */
        Solution copy = new Solution(pp);

        copy.copy(candidate);
        copy.setObjectiveValue(candidate.getObjectiveValue());
        long startTime = System.nanoTime();
        heuristics[index].applyHeuristic(copy);
        long endTime = System.nanoTime();
        objectives[index] = copy.getObjectiveValue();
        times[index] = endTime - startTime ;
        f[index] = m.Late_Acceptance(copy);
        if(m.rejected){
            heuristics[index].setTotal_score(-1);
            m.rejected = false;
        }


    }




    public void cancel(int index){
        objectives[index] = originalValue * 2;
        times[index] = System.nanoTime() * 2;
        total[index] = 0;
    }

    public void process2(Solution candidate, int originalValue, int i1, int i2, MoveAcceptance m , int index ){

        Solution copy = new Solution(pp);

        copy.copy(candidate);
        copy.setObjectiveValue(candidate.getObjectiveValue());
        long startTime = System.nanoTime();
        heuristics[i1].applyHeuristic(copy);
        heuristics[i2].applyHeuristic(copy);
        long endTime = System.nanoTime();
        objectives[index] = copy.getObjectiveValue();
        times[index] = endTime - startTime ;
        f[index] = m.Late_Acceptance(copy);
        //System.out.println(copy.getObjectiveValue() + " " + heuristics[index].getName() + " " + f[index]);
        if(m.rejected){
            heuristics[index].setTotal_score(-1);
            m.rejected = false;
        }


    }


    public void resetMA(MoveAcceptance m){
        m.idle = 0;
        m.move.randomInit(rand);
        m.count = 0;
        for(int i = 0 ; i < m.MEMORY_SIZE ; i++){
            m.memory[i] = m.move.getObjectiveValue();
        }
    }

}
