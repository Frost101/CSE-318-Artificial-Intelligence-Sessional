import java.util.Scanner;

public class HumanVSHuman {
    public static void main(String[] args) {
        MancalaBoard mancalaBoard = new MancalaBoard();
        int player = 1;
        int slot;
        int stat;
        Scanner sc = new Scanner(System.in);
        mancalaBoard.printMancalaBoard();
        while (true){
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
                System.out.println("Player 1:Enter slot id");
                String tmp = sc.nextLine();
                slot = Integer.parseInt(tmp);
                mancalaBoard.setTurn(player);
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
                System.out.println("Player 2 - Enter Slot ID: ");
                String tmp = sc.nextLine();
                slot = Integer.parseInt(tmp);
                mancalaBoard.setTurn(player);
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
                    player = 1;
                }
            }
        }
    }
}
