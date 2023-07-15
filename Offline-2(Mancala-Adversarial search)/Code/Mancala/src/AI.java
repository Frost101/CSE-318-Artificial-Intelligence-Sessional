public class AI {
    private int depth;
    private int heuristic;
    private int player;

    AI(int player){
        /*   Default Constructor
             Default depth = 4;
             Default heuristic = 4; best one
        */
        this.player = player;
        this.depth = 4;
        this.heuristic = 1;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
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
