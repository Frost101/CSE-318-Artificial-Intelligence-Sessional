import java.util.ArrayList;

public class AdversarialSearch {

    public static final int infinity = 999999;
    int player;

    AdversarialSearch(int player){
        this.player = player;
    }

    public advSearchNode alphaBetaPruning(boolean isMax,int whichHeuristic,int alpha,int beta,int depth,MancalaBoard mancalaBoard,int turn){
        if(isMax){

            if(depth == 0 || mancalaBoard.gameOver()){
                /*   These are the leaves.Base case of the recursion   */
                return new advSearchNode(mancalaBoard.getHeuristic(player,whichHeuristic),-1);
            }

            /*     Construct a node containing the best value and move of this current node     */
            advSearchNode bestNode = new advSearchNode(-infinity,-1);

            /*     Now create the children with 6 moves     */
            ArrayList<Integer> moves = new ArrayList<>();
            for(int i=0; i<6; i++){
                moves.add(i);
            }

            /*    Shuffle the moves for better pruning outcome    */
            //Collections.shuffle(moves);
            for(int i=0; i<6; i++){
                MancalaBoard temp = new MancalaBoard(mancalaBoard);
                temp.setTurn(turn);
                temp.setParent(mancalaBoard);
                int stat = temp.makeAMove(moves.get(i));
                advSearchNode returnNode;
                if(stat == 0){
                    /*      Switch player to min        */
                    if(turn == 1) returnNode = alphaBetaPruning(false,whichHeuristic,alpha,beta,depth-1,temp,2);
                    else returnNode = alphaBetaPruning(false,whichHeuristic,alpha,beta,depth-1,temp,1);
                    //System.out.println(returnNode.bestValue + "=======" + returnNode.bestMove + "======" + depth);
                }
                else if(stat == 1){
                    /*      You got a free move.So max's turn again     */
                    returnNode = alphaBetaPruning(true,whichHeuristic,alpha,beta,depth-1,temp,turn);
                    //System.out.println(returnNode.bestValue + "=======" + returnNode.bestMove + "======" + depth);
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

            if(depth == 0 || mancalaBoard.gameOver()){
                /*   These are the leaves.Base case of the recursion   */
                return new advSearchNode(mancalaBoard.getHeuristic(player,whichHeuristic),-1);
            }

            /*     Construct a node containing the best value and move of this current node     */
            advSearchNode bestNode = new advSearchNode(infinity,-1);

            /*     Now create the children with 6 moves     */
            ArrayList<Integer> moves = new ArrayList<>();
            for(int i=0; i<6; i++){
                moves.add(i);
            }

            /*    Shuffle the moves for better pruning outcome    */
            //Collections.shuffle(moves);
            for(int i=0; i<6; i++){
                MancalaBoard temp = new MancalaBoard(mancalaBoard);
                temp.setTurn(turn);
                temp.setParent(mancalaBoard);
                int stat = temp.makeAMove(moves.get(i));
                advSearchNode returnNode;
                if(stat == 0){
                    /*      Switch player to max        */
                    if(turn == 1) returnNode = alphaBetaPruning(true,whichHeuristic,alpha,beta,depth-1,temp,2);
                    else returnNode = alphaBetaPruning(true,whichHeuristic,alpha,beta,depth-1,temp,1);

                    //System.out.println(returnNode.bestValue + "=======" + returnNode.bestMove + "======" + depth);

                }
                else if(stat == 1){
                    /*      You got a free move.So min's turn again     */
                    returnNode = alphaBetaPruning(false,whichHeuristic,alpha,beta,depth-1,temp,turn);

                    //System.out.println(returnNode.bestValue + "=======" + returnNode.bestMove + "======" + depth);
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
