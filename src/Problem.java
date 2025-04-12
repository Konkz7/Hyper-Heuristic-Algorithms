package src;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
public class Problem {

    private int no_sets;

    private int no_var;

    private int frame[][];

    public int setSizes[];

    public int getNo_sets() {
        return no_sets;
    }

    public int getNo_var() {
        return no_var;
    }

    public int[][] getFrame() {
        return frame;
    }


    public Problem(String file){

       //reading file
       try {
           File myObj = new File(file);
           Scanner myReader = new Scanner(myObj);

           no_sets = Integer.valueOf(myReader.next());
           System.out.println(no_sets);

           no_var = Integer.valueOf(myReader.next());
           System.out.println(no_var);

           this.frame = new int[no_sets][no_var];
           this.setSizes = new int[no_sets];

           for(int i = 0 ; i < no_sets ; i ++){
               int temps = Integer.valueOf(myReader.next());
               setSizes[i] = temps;

               for (int k = 0; k < temps ; k++){
                   frame[i][Integer.valueOf(myReader.next()) - 1] = 1;
               }
           }

           myReader.close();
       } catch (FileNotFoundException e) {
           System.out.println("An error occurred.");
           e.printStackTrace();
       }

   }
}
