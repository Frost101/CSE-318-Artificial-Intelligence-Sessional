import java.util.ArrayList;
import java.util.Collections;

public class AdversarialSearch {

    public static final int infinity = 999999;

    AdversarialSearch(){

    }

    public advSearchNode alphaBetaPruning(boolean isMax,int whichHeuristic,int alpha,int beta,int depth,MancalaBoard mancalaBoard){
        if(isMax){
            /*     Player 1 is max and Player 2 is min     */
            if(depth == 0 || mancalaBoard.gameOver()){
                /*   These are the leaves.Base case of the recursion   */
                return new advSearchNode(mancalaBoard.getHeuristic(1,whichHeuristic),-1);
            }

            /*     Construct a node containing the best value and move of this current node     */
            advSearchNode bestNode = new advSearchNode(-infinity,-1);

            /*     Now create the children with 6 moves     */
            ArrayList<Integer> moves = new ArrayList<>();
            for(int i=0; i<6; i++){
                moves.add(i);
            }

            /*    Shuffle the moves for better pruning outcome    */
            Collections.shuffle(moves);
            for(int i=0; i<6; i++){
                /*   First check if it's a valid move or not.Like if the slot is empty or not.
                *    If the slot is empty then dont take that move   */
                if(mancalaBoard.getBoard().get(moves.get(i)) == 0){
                    //That slot is empty.Invalid move
                    continue;
                }
                MancalaBoard temp = new MancalaBoard(mancalaBoard);
                int stat = temp.makeAMove(1,moves.get(i));
                advSearchNode returnNode;
                if(stat == 0){
                    /*      Switch player to min        */
                    returnNode = alphaBetaPruning(false,whichHeuristic,alpha,beta,depth-1,temp);
                }
                else if(stat == 1){
                    /*      You got a free move.So max's turn again     */
                    returnNode = alphaBetaPruning(true,whichHeuristic,alpha,beta,depth-1,temp);
                }
                else{
                    /*   Invalid move.continue   */
                    continue;
                }

                /*      Now update best value and move of the current node      */
                if(returnNode.bestValue > bestNode.bestValue){
                    bestNode.bestValue = returnNode.bestValue;
                    bestNode.bestMove = moves.get(i);
                }

                /*     Update alpha     */
                alpha = Math.max(alpha,returnNode.bestValue);

                /*         If at any point beta becomes smaller than alpha,prune         */
                if(beta <= alpha) break;
            }

            /*   Now return the best possible values from this node   */
            return bestNode;
        }
        else{
            /*     Player 1 is max and Player 2 is min     */
            if(depth == 0 || mancalaBoard.gameOver()){
                /*   These are the leaves.Base case of the recursion   */
                return new advSearchNode(mancalaBoard.getHeuristic(2,whichHeuristic),-1);
            }

            /*     Construct a node containing the best value and move of this current node     */
            advSearchNode bestNode = new advSearchNode(infinity,-1);

            /*     Now create the children with 6 moves     */
            ArrayList<Integer> moves = new ArrayList<>();
            for(int i=0; i<6; i++){
                moves.add(i);
            }

            /*    Shuffle the moves for better pruning outcome    */
            Collections.shuffle(moves);
            for(int i=0; i<6; i++){
                /*   First check if it's a valid move or not.Like if the slot is empty or not.
                 *    If the slot is empty then dont take that move   */
                if(mancalaBoard.getBoard().get(moves.get(i)+7) == 0){
                    //That slot is empty.Invalid move
                    continue;
                }
                MancalaBoard temp = new MancalaBoard(mancalaBoard);
                int stat = temp.makeAMove(2,moves.get(i));
                advSearchNode returnNode;
                if(stat == 0){
                    /*      Switch player to max        */
                    returnNode = alphaBetaPruning(true,whichHeuristic,alpha,beta,depth-1,temp);

                }
                else if(stat == 1){
                    /*      You got a free move.So min's turn again     */
                    returnNode = alphaBetaPruning(false,whichHeuristic,alpha,beta,depth-1,temp);
                }
                else{

                    /*   Invalid move.continue   */
                    continue;
                }

                /*      Now update best value and move of the current node      */
                if(returnNode.bestValue < bestNode.bestValue){
                    bestNode.bestValue = returnNode.bestValue;
                    bestNode.bestMove = moves.get(i);
                }

                /*     Update beta     */
                beta = Math.min(beta,returnNode.bestValue);

                /*         If at any point beta becomes smaller than alpha,prune         */
                if(beta <= alpha) break;
            }

            /*   Now return the best possible values from this node   */
            return bestNode;
        }
    }
}
