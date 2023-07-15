import java.util.Scanner;

public class AIvsAI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = "";
        AI player1 = new AI(1);
        AI player2 = new AI(2);
        player1.setDepth(6);
        player2.setDepth(6);
        player1.setHeuristic(2);
        player2.setHeuristic(2);
        MancalaBoard mancalaBoard = new MancalaBoard();
        int player = 1;
        int slot;
        int stat;
        while(true){
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
            if(player == 1){
                System.out.println("AI-player1's move ");
                s = sc.nextLine();                      //Wait for key press
                mancalaBoard.setTurn(player1.getPlayer());       //Player1's turn
                AdversarialSearch adversarialSearch = new AdversarialSearch(player1.getPlayer());
                advSearchNode node = adversarialSearch.alphaBetaPruning(true,player1.getHeuristic(),-AdversarialSearch.infinity,AdversarialSearch.infinity,player1.getDepth(),mancalaBoard,player1.getPlayer());
                slot = node.bestMove;
                System.out.println(slot);
                mancalaBoard.setTurn(player1.getPlayer());       //Player1's turn
                stat = mancalaBoard.makeAMove(slot);
                if(stat == -1){
                    System.out.println("Invalid Move from AI-player 1");
                    String tmp = sc.nextLine();
                    continue;
                }
                else if(stat == 1){
                    mancalaBoard.printMancalaBoard();
                    System.out.println("Free Move for AI-player 1 !!!");
                    continue;
                }
                else{
                    mancalaBoard.printMancalaBoard();
                    player = 2;
                }
            }
            else{
                System.out.println("AI-player 2's move ");
                s = sc.nextLine();                       //Wait for key press
                mancalaBoard.setTurn(player2.getPlayer());       //Player2's turn
                AdversarialSearch adversarialSearch = new AdversarialSearch(player2.getPlayer());
                advSearchNode node = adversarialSearch.alphaBetaPruning(true,player2.getHeuristic(),-AdversarialSearch.infinity,AdversarialSearch.infinity,player2.getDepth(),mancalaBoard,player2.getPlayer());
                slot = node.bestMove;
                System.out.println(slot);
                mancalaBoard.setTurn(player2.getPlayer());       //Player2's turn
                stat = mancalaBoard.makeAMove(slot);
                if(stat == -1){
                    System.out.println("Invalid Move from AI-player 2");
                    String tmp = sc.nextLine();
                    continue;
                }
                else if(stat == 1){
                    mancalaBoard.printMancalaBoard();
                    System.out.println("Free Move for AI-player 2!!!");
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
