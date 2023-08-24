import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Graph {

    private Vector<HashMap<Integer,Integer>> graphArr;
    private Vector<Vector<Integer>> edges;
    private int N;
    private int M;
    private int maxEdge;
    private int minEdge;

    Graph(int N, int M){
        maxEdge = Integer.MIN_VALUE;
        minEdge = Integer.MAX_VALUE;
        this.N = N;
        this.M = M;

        graphArr = new Vector<HashMap<Integer,Integer>>();

        for(int i=0; i<=N; i++){
           graphArr.add(new HashMap<Integer,Integer>());
        }

        edges = new Vector<>();
        for(int i=0; i<=N; i++){
            edges.add(new Vector<>());
        }
    }

    public void addEdges(int u, int v, int w){
        this.graphArr.get(u).put(v,w);
        this.graphArr.get(v).put(u,w);
        edges.get(u).add(v);
        edges.get(v).add(u);
        maxEdge = Math.max(maxEdge, w);
        minEdge = Math.min(minEdge, w);
    }

    public int getEdgeWeight(int u, int v) {
        return graphArr.get(u).get(v);
    }

    public int getMaxEdge() {
        return maxEdge;
    }

    public int getMinEdge() {
        return minEdge;
    }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public Vector<HashMap<Integer, Integer>> getGraphArr() {
        return graphArr;
    }

    public Vector<Vector<Integer>> getEdges() {
        return edges;
    }
}
