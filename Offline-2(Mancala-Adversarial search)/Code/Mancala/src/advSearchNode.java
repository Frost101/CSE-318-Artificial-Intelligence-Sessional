public class advSearchNode {
    int bestValue;
    int bestMove;
    advSearchNode(int bestValue, int bestMove){
        this.bestValue = bestValue;
        this.bestMove = bestMove;
    }

    public void setBestMove(int bestMove) {
        this.bestMove = bestMove;
    }

    public int getBestMove() {
        return bestMove;
    }

    public void setBestValue(int bestValue) {
        this.bestValue = bestValue;
    }

    public int getBestValue() {
        return bestValue;
    }
}
