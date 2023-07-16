import java.util.Scanner;

public class HumanvsAI {
    public static void main(String[] args) {
        MancalaBoard mancalaBoard = new MancalaBoard();
        AI player2 = new AI(2);
        player2.setDepth(10);
        player2.setHeuristic(4);
        int player = 1;
        int slot;
        int stat;
        Scanner sc = new Scanner(System.in);
        mancalaBoard.printMancalaBoard();
        while (true){
            //Setup for heuristic 3 & 4
            mancalaBoard.setMoveEarned_p1(0);
            mancalaBoard.setMoveEarned_p2(0);
            mancalaBoard.setStonesCaptured_p1(0);
            mancalaBoard.setStonesCaptured_p2(0);

            /*      Check is game is over       */
            if(mancalaBoard.gameOver()){
                System.out.println("---------  Game Over!  --------");
                mancalaBoard.printMancalaBoard();
                int win = mancalaBoard.whoWon();
                if(win == 1){
                    System.out.println("****** Player 1 wins  *******");
                }
                else if(win == 2){
                    System.out.println("****** Player 2 wins  *******");
                }
                else{
                    System.out.println("*******  Match drawn  ********");
                }
                break;
            }

            if(player==1){
                mancalaBoard.setTurn(player);       //player1's turn
                System.out.println("--------- Player 1:Enter slot id ---------");
                String tmp = sc.nextLine();
                slot = Integer.parseInt(tmp);
                stat = mancalaBoard.makeAMove(slot);
                if(stat == -1){
                    System.out.println("Invalid Move");
                    continue;
                }
                else if(stat == 1){
                    mancalaBoard.printMancalaBoard();
                    System.out.println("**********  Free Move!!! ***************");
                    continue;
                }
                else{
                    mancalaBoard.printMancalaBoard();
                    player = 2;
                }
            }
            else{
                System.out.println("-------  Player 2 - AI's move ---------");
                mancalaBoard.setTurn(player);       //Player2's turn
                AdversarialSearch adversarialSearch = new AdversarialSearch(player);
                advSearchNode node = adversarialSearch.alphaBetaPruning(true,player2.getHeuristic(),-AdversarialSearch.infinity,AdversarialSearch.infinity,player2.getDepth(),mancalaBoard,player);
                slot = node.bestMove;
                System.out.println("AI's move: " + slot);
                mancalaBoard.setTurn(player);       //Player2's turn
                stat = mancalaBoard.makeAMove(slot);
                if(stat == -1){
                    System.out.println("Invalid Move from AI");
                    String tmp = sc.nextLine();
                    continue;
                }
                else if(stat == 1){
                    mancalaBoard.printMancalaBoard();
                    System.out.println("Free Move!!!");
                    continue;
                }
                else{
                    mancalaBoard.printMancalaBoard();
                    player = 1;
                }
            }
        }

    }
}
