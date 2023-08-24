import java.util.Random;
import java.util.Scanner;

public class HeuristicsVsHeuristics {
    public static final int MIN_DEPTH = 3;
    public static final int MAX_DEPTH = 10;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter heuristic number for AI 1 (Available heuristics:1,2,3,4)");
        String s = sc.nextLine();
        int heursiticAI1 = Integer.parseInt(s);
        System.out.println("Enter heuristic number for AI 2 (Available heuristics:1,2,3,4)");
        s = sc.nextLine();
        int heursiticAI2 = Integer.parseInt(s);

        Random random = new Random();
        int randomNumber = random.nextInt(MAX_DEPTH - MIN_DEPTH + 1) + MIN_DEPTH;
        int depthAI1 = randomNumber;
        randomNumber = random.nextInt(MAX_DEPTH - MIN_DEPTH + 1) + MIN_DEPTH;
        int depthAI2 = randomNumber;


        AI player1 = new AI(1);
        AI player2 = new AI(2);
        player1.setDepth(depthAI1);
        player2.setDepth(depthAI2);
        player1.setHeuristic(heursiticAI1);
        player2.setHeuristic(heursiticAI2);
        int matchcount = 100;
        int player1win = 0;
        int player2win = 0;
        int draw = 0;

        while(matchcount>0){
            matchcount--;
            MancalaBoard mancalaBoard = new MancalaBoard();
            int player = 1;
            int slot;
            int stat;
            randomNumber = random.nextInt();
            if (randomNumber % 2 == 0) {
                player = 1;     //First move of AI 1
            } else {
                player = 2;     //First move of AI 2
            }

            while (true) {
                //Setup for heuristic 3 & 4
                mancalaBoard.setMoveEarned_p1(0);
                mancalaBoard.setMoveEarned_p2(0);
                mancalaBoard.setStonesCaptured_p1(0);
                mancalaBoard.setStonesCaptured_p2(0);
                if (mancalaBoard.gameOver()) {
                    //System.out.println("---------  Game Over!  --------");
                    //mancalaBoard.printMancalaBoard();
                    int win = mancalaBoard.whoWon();
                    if (win == 1) {
                        player1win++;
                        //System.out.println("****** Player 1 wins  *******");
                    } else if (win == 2) {

                        player2win++;
                    // System.out.println("****** Player 2 wins  *******");
                    } else {
                        draw++;
                        //System.out.println("*******  Match drawn  ********");
                    }
                    break;
                }
                if (player == 1) {
                    //System.out.println("-------- AI-player1's move ---------");
                    //s = sc.nextLine();                      //Wait for key press
                    mancalaBoard.setTurn(player1.getPlayer());       //Player1's turn

                    AdversarialSearch adversarialSearch = new AdversarialSearch(player1.getPlayer());
                    advSearchNode node = adversarialSearch.alphaBetaPruning(true, player1.getHeuristic(), -AdversarialSearch.infinity, AdversarialSearch.infinity, player1.getDepth(), mancalaBoard, player1.getPlayer());
                    slot = node.bestMove;
                    //System.out.println(slot);
                    mancalaBoard.setTurn(player1.getPlayer());       //Player1's turn
                    stat = mancalaBoard.makeAMove(slot);
                    if (stat == -1) {
                        //System.out.println("Invalid Move from AI-player 1");
                        //String tmp = sc.nextLine();
                        continue;
                    } else if (stat == 1) {
                        // mancalaBoard.printMancalaBoard();
                        // System.out.println("*********  Free Move for AI-player 1 !!!  **********");
                        continue;
                    } else {
                        //mancalaBoard.printMancalaBoard();
                        player = 2;
                    }
                } else {
                    //System.out.println("---------- AI-player 2's move  ---------");
                    //s = sc.nextLine();                       //Wait for key press
                    mancalaBoard.setTurn(player2.getPlayer());       //Player2's turn
                    AdversarialSearch adversarialSearch = new AdversarialSearch(player2.getPlayer());
                    advSearchNode node = adversarialSearch.alphaBetaPruning(true, player2.getHeuristic(), -AdversarialSearch.infinity, AdversarialSearch.infinity, player2.getDepth(), mancalaBoard, player2.getPlayer());
                    slot = node.bestMove;
                    //System.out.println(slot);
                    mancalaBoard.setTurn(player2.getPlayer());       //Player2's turn
                    stat = mancalaBoard.makeAMove(slot);
                    if (stat == -1) {
                        //System.out.println("Invalid Move from AI-player 2");
                        //String tmp = sc.nextLine();
                        continue;
                    } else if (stat == 1) {
                        //mancalaBoard.printMancalaBoard();
                        //System.out.println("********** Free Move for AI-player 2!!!  **********");
                        continue;
                    } else {
                        //mancalaBoard.printMancalaBoard();
                        player = 1;
                    }
                }

        }
    }
        System.out.println("Heuristic " +heursiticAI1+" win:"+player1win+" ---  Heuristic "+ heursiticAI2+" win: "+player2win + " ---   Draw: "+draw);
    }
}
