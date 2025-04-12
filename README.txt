Find class 'tester' in the src to run algorithm by pressing the run button (in the top right in intellij)
you can manipulate certain factors in this class such as the numberOf parameter and the amount of trials   and the instance you want to test

IOM and DOS parameters are changed through the classes found in the heuristics package

togglerejectpoints can be found in the move acceptance class , this is used to decide whether to punish rejected moves

After run is pressed the output should be the name of the heuristic followed by the 
- objective value ranking
- time taken ranking
- total ranking

This is done in two stages, the second stage being the best two heuristics from the first stage combined and compared.

You can also change the allowed time each stage can have by changing the number in the condition of the while loop
