package src.heuristics;

import src.Problem;
import src.Solution;

import java.util.Random;

public abstract class Heuristic {

    Random rand;
    public int Total_score, time_score, objective_score;
    public String name;
    Problem problem;
    int delta;
    public Heuristic(Problem problem , Random random){
        this.problem = problem;
        this.rand = random;
    }
    public abstract void applyHeuristic(Solution s);

    public int getTotal_score() {
        return Total_score;
    }


    public int getTime_score() {
        return time_score;
    }

    public int getObjective_score() {
        return objective_score;
    }

    public void setTime_score(int change) {
        this.time_score += change;
        this.Total_score += change;
    }


    public void setObjective_score(int change) {
        this.objective_score += change;
        this.Total_score += change;
    }

    public void setTotal_score(int total_score) {
        Total_score = total_score;
    }

    public void reset_score() {
        this.objective_score = 0;
        this.Total_score = 0;
        this.time_score = 0;

    }
    public void print(){
        System.out.println(getName() + " " + getObjective_score() + " " + getTime_score() + " " + getTotal_score());
    }

    public String getName() {
        return name;
    }
}
