public class AI {
    private int depth;
    private int heuristic;

    AI(){
        /*   Default Constructor
             Default depth = 4;
             Default heuristic = 4; best one
        */
        this.depth = 4;
        this.heuristic = 4;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getHeuristic() {
        return heuristic;
    }

    int getNextMove(MancalaBoard mancalaBoard){
        return 0;
    }
}
