import java.util.Scanner;

public class HumanvsAI {
    public static void main(String[] args) {
        MancalaBoard mancalaBoard = new MancalaBoard();
        int player = 1;
        int slot;
        int stat;
        Scanner sc = new Scanner(System.in);
        mancalaBoard.printMancalaBoard();
        while (true){
            if(player==1){
                mancalaBoard.setTurn(player);       //player1's turn
                System.out.println("Player 1:Enter slot id");
                String tmp = sc.nextLine();
                slot = Integer.parseInt(tmp);
                stat = mancalaBoard.makeAMove(slot);
                if(stat == -1){
                    System.out.println("Invalid Move");
                    continue;
                }
                else if(stat == 1){
                    mancalaBoard.printMancalaBoard();
                    System.out.println("Free Move!!!");
                    continue;
                }
                else{
                    mancalaBoard.printMancalaBoard();
                    player = 2;
                }
            }
            else{
                System.out.println("Player 2 - AI's move ");
                mancalaBoard.setTurn(player);       //Player2's turn
                AdversarialSearch adversarialSearch = new AdversarialSearch(player);
                advSearchNode node = adversarialSearch.alphaBetaPruning(true,1,-AdversarialSearch.infinity,AdversarialSearch.infinity,10,mancalaBoard,player);
                slot = node.bestMove;
                System.out.println(slot);
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
            if(mancalaBoard.gameOver()){
                System.out.println("Game Over!");
                break;
            }
        }

    }
}
