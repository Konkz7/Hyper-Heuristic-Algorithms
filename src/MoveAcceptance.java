package src;

public class MoveAcceptance {

    Problem problem;

    boolean ToggleRejectPoints = false;
    static final int  MEMORY_SIZE = 10;
    public MoveAcceptance(Problem problem, Solution s){
        this.problem = problem;
        this.move = new Solution(problem);
        this.move.copy(s);
        this.move.setObjectiveValue(s.getObjectiveValue());
        for(int i = 0 ; i < MEMORY_SIZE ; i++){
            memory[i] = move.getObjectiveValue();
        }
    }

    Solution move ;

    int memory[] = new int[MEMORY_SIZE];

    int idle = 0;
    int count = 0;

    boolean rejected = false;

    public boolean Late_Acceptance(Solution s){

        if(!(idle  > count * 0.02  && count > 100000000)) {
            if (s.getObjectiveValue() >= move.getObjectiveValue()) {
                idle++;

            } else {
                idle = 0;
            }


            int v = count % MEMORY_SIZE;

            if (s.getObjectiveValue() < memory[v] || s.getObjectiveValue() <= move.getObjectiveValue()) {
                move.copy(s);
                move.setObjectiveValue(s.getObjectiveValue());
            }else{
                if(ToggleRejectPoints){rejected = true;}
            }

            if (move.getObjectiveValue() < memory[v]) {
                memory[v] = move.getObjectiveValue();
            }

            count++;


        }else{
            return false;
        }

        return true;
    }



}
