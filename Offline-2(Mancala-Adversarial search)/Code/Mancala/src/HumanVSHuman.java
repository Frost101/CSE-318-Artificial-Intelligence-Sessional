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
            if(player==1){
                System.out.println("Player 1:Enter slot id");
                String tmp = sc.nextLine();
                slot = Integer.parseInt(tmp);
                stat = mancalaBoard.makeAMove(player,slot);
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
                stat = mancalaBoard.makeAMove(player,slot);
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
