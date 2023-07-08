public class Main {
    public static void main(String[] args) {
        MancalaBoard mancalaBoard = new MancalaBoard();
        /*    boolean isMax,int whichHeuristic,int alpha,int beta,int depth,MancalaBoard mancalaBoard */
        AdversarialSearch adversarialSearch = new AdversarialSearch();
        adversarialSearch.alphaBetaPruning(true,1,20,10,10,mancalaBoard);
    }
}
