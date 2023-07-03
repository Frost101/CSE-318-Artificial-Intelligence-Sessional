import java.util.ArrayList;

public class MancalaBoard {
    ArrayList<Integer> board;
    int player1Store = 6;       //Player1's Mancala store is in 6 index of the board array
    int player2Store = 13;   //Player1's Mancala store is in 6 index of the board array

    MancalaBoard(){
        board = new ArrayList<>();
        /*       Initialize with 4 stones in each holes of Player 1      */
        for(int i=1; i<=6; i++){
            board.add(4);
        }
        /*      Add zero marbles to player1's Mancala store              */
        board.add(0);
        /*       Initialize with 4 stones in each holes of Player 1      */
        for(int i=1; i<=6; i++){
            board.add(4);
        }
        /*      Add zero marbles to player1's Mancala store               */
        board.add(0);
    }

    MancalaBoard(MancalaBoard mancalaBoard){
        /*     More like a copy constructor     */
        board = new ArrayList<>();
        for(int i=0; i<=13; i++){
            this.board.add(mancalaBoard.board.get(i));
        }
        this.player1Store = mancalaBoard.player1Store;
        this.player2Store = mancalaBoard.player2Store;
    }

    public void setBoard(ArrayList<Integer> board) {
        this.board = new ArrayList<>();
        for(int i=0; i<14; i++){
            this.board.add(board.get(i));
        }
    }

    public ArrayList<Integer> getBoard() {
        return board;
    }

    public void setPlayer1Store(int player1Store) {
        this.player1Store = player1Store;
    }

    public int getPlayer1Store() {
        return player1Store;
    }

    public void setPlayer2Store(int player2Store) {
        this.player2Store = player2Store;
    }

    public int getPlayer2Store() {
        return player2Store;
    }

    public void printMancalaBoard(){

        System.out.println("\t\t\t\tPlayer 2");
        for(int i=12; i>=7; i--){
            System.out.print("\t" + this.board.get(i) + "\t");
        }
        System.out.println();
        System.out.println();
        System.out.println(this.board.get(player2Store) +"\t\t\t\t\t\t\t\t\t\t\t\t"+ this.board.get(player1Store));
        System.out.println();
        for(int i=0; i<6; i++){
            System.out.print("\t" + this.board.get(i) + "\t");
        }
        System.out.println();
        System.out.println("\t\t\t\tPlayer 1");
    }


    public int makeAMove(int player,int slot){
        /*       Return -1 for Invalid Move                         */
        /*       Return  1 for extra/free move                      */
        /*       Return  0 for normal move and switch players       */
        /*       Player  1 is you and player 2 is the opponent      */

        if(!checkMoveValidity(player, slot)){
            return -1;
        }
        if(player == 1){
            int stoneCount = board.get(slot);
            int currentSlot = slot+1;
            while (stoneCount!=0){
                if(currentSlot == player2Store){
                    currentSlot = 0;
                    continue;
                }
                board.set(currentSlot, board.get(currentSlot)+1);   //Increase the no of stones in current slot
                stoneCount--;
                board.set(slot, board.get(slot)-1);                 //Decrease the no if stones from the first selected slot

                /*    Now Check for the free turn
                      If the last stone is dropped in the player1's mancala
                      Also,you have to check whether your last stone is dropped in an empty slot of yours,
                      If it does then opponents slot's stones are yours
                */
                if(stoneCount == 0){
                    if(currentSlot == player1Store) return 1;       //Last stone is dropped in the player1's mancala,return 1 for free move
                    /*
                        Our mancala board configuration
                        12  11  10  9   8   7
                    13                            6
                        0   1   2   3   4   5

                        So The Opposite Slot Formula is (12 - current slot no)
                    */
                    int oppositeSlot = 12 - currentSlot;
                    if(oppositeSlot >= 7 && oppositeSlot <= 12 && board.get(currentSlot) == 1 && board.get(oppositeSlot) > 0){
                        board.set(player1Store, board.get(player1Store) + board.get(oppositeSlot) + board.get(currentSlot));  //Player will get the stones of his current store and opposite store
                        board.set(currentSlot,0);
                        board.set(oppositeSlot,0);
                        return 0;       //Normal Move,Player switch
                    }
                }
                currentSlot++;
            }
            return 0;      //Normal Move,Player switch
        }
        else{
            slot += 7;
            int stoneCount = board.get(slot);
            int currentSlot = slot + 1;
            while (stoneCount!=0){
                if(currentSlot == player1Store){
                    currentSlot = player1Store + 1;
                    continue;
                }
                board.set(currentSlot, board.get(currentSlot)+1);   //Increase the no of stones in current slot
                stoneCount--;
                board.set(slot, board.get(slot)-1);                 //Decrease the no if stones from the first selected slot

                /*    Now Check for the free turn
                      If the last stone is dropped in the player1's mancala
                      Also,you have to check whether your last stone is dropped in an empty slot of yours,
                      If it does then opponents slot's stones are yours
                */
                if(stoneCount == 0){
                    if(currentSlot == player2Store) return 1;       //Last stone is dropped in the player1's mancala,return 1 for free move
                    /*
                        Our mancala board configuration
                        12  11  10  9   8   7
                    13                            6
                        0   1   2   3   4   5

                        So The Opposite Slot Formula is (12 - current slot no)
                    */
                    int oppositeSlot = 12 - currentSlot;
                    if(oppositeSlot >= 0 && oppositeSlot <= 5 && board.get(currentSlot) == 1 && board.get(oppositeSlot) > 0){
                        board.set(player2Store, board.get(player2Store) + board.get(oppositeSlot) + board.get(currentSlot));  //Player will get the stones of his current store and opposite store
                        board.set(currentSlot,0);
                        board.set(oppositeSlot,0);
                        return 0;       //Normal Move,Player switch
                    }
                }
                currentSlot++;
                if(currentSlot == player2Store + 1){
                    currentSlot = 0;
                }
            }
            return 0;
        }
    }

    public boolean checkMoveValidity(int player,int slotIndex){
        if(slotIndex < 0 || slotIndex >5) return false;
        if(player == 1){
            /*     If that particular slot is empty     */
            if(this.board.get(slotIndex) == 0)return false;
        }
        else{
            /*  For Player 2    */
            if(this.board.get(slotIndex +6 +1) == 0) return false;
        }
        return true;
    }
}
