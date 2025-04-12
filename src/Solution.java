package src;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solution {



    public Solution(Problem problem){

        this.value = new int[problem.getNo_sets()];
        this.problem = problem;

    }

    //where the actual solution is held
    private int value[];
    Problem problem;

    private int buffer[];
    private int objectiveValue;

    public int[] getValue() {
        return value;
    }

    public void flip(int index){
        if(value[index] == 1){
            value[index] = 0;
        }else {
            value[index] = 1;
        }
    }

    public boolean feasible(){

        buffer = new int[problem.getNo_var()];

        for(int i = 0; i < problem.getNo_sets(); i ++ ){

            if(value[i] == 1){
                for (int j = 0; j < problem.getNo_var(); j++){
                    if (problem.getFrame()[i][j] == 1){
                        buffer[j] = 1;
                    }
                }
            }

        }

        for (int x = 0; x < problem.getNo_var(); x++){
            if (buffer[x] == 0){
                return false;
            }
        }

        return true;
    }
    public int getObjectiveValue() {
        return objectiveValue;
    }

    public void setObjectiveValue(int objectiveValue) {
        this.objectiveValue = objectiveValue;
    }

    public int computeOV(){
        objectiveValue = 0;

        for (int x = 0; x < problem.getNo_sets(); x++){
            if (value[x] == 1){
                objectiveValue +=1;
            }
        }

        return objectiveValue;
    }

    public void randomInit(Random rand){

        int n = problem.getNo_sets();

        Integer range [] = new Integer[n];

        for (int p = 0 ; p < n ; p ++){
            range[p] = p;

        }

        List<Integer> intList = Arrays.asList(range);

        Collections.shuffle(intList,rand);

        intList.toArray(range);


        for (int i = 0; i < problem.getNo_sets(); i++) {

            this.value[i] = rand.nextInt(0, 2);

        }

        while (!feasible()) {
            for (int i = 0 ; i < problem.getNo_sets(); i ++){
                if(this.value[range[i]] == 0 ){
                    this.value[range[i]] = 1;
                }
            }
        }



        computeOV(); // calculates objective value and assigns it
    }
    public void copy(Solution s){
        for (int i = 0; i < problem.getNo_sets(); i ++){
            if (this.getValue()[i] != s.getValue()[i]){
                this.flip(i);
            }
        }

    }

    public String print(){

        String out = "";

        for (int i = 0; i < problem.getNo_sets();i++) {
            out += this.getValue()[i];
        }
        return out;
    }

    public void wipe(){
        for (int i = 0; i < problem.getNo_sets(); i ++){
            this.getValue()[i] = 0;
        }
    }


}
