
import java.util.*;

public class MaxCut {

    Graph graph;
    int N;
    int M;
    Set<Integer> X;
    Set<Integer> Y;

    Vector<Vector<Integer>> sigmaArr;

    int statArr[] = {0,0,0,0,0,0,0,0,0};
    MaxCut(Graph graph) {
        X = new HashSet<>();
        Y = new HashSet<>();
        this.graph = graph;
        this.N = graph.getN();
        this.M = graph.getM();
        sigmaArr = new Vector<Vector<Integer>>();
        for(int i=0; i<3; i++){
            sigmaArr.add(new Vector<Integer>(N+1));
        }


        // For statistics
        statArr[0] = N;
        statArr[1] = M;
    }

    public int[] getStatArr() {
        return statArr;
    }

    private int sigmaX(Set<Integer> Y, int node){
        int total = 0;
        for(int i : Y) {
            if(this.graph.getGraphArr().get(node).get(i) != null) {
                total += this.graph.getGraphArr().get(node).get(i);
            }
        }
        return total;
    }


    private int sigmaY(Set<Integer> X, int node){
        int total = 0;
        for(int i : X) {
            if(this.graph.getGraphArr().get(node).get(i) != null) {
                total += this.graph.getGraphArr().get(node).get(i);
            }
        }
        return total;
    }


    public int getMaxCutWeight(){
        int total = 0;
        for(int u : this.X){
            for(int v : this.Y){
                if(this.graph.getGraphArr().get(u).get(v) != null) {
                    total += this.graph.getGraphArr().get(u).get(v);
                }
            }
        }
        return total;
    }






    public int semiGreedyMaxCut(){
        UniformRandomGenerator randomGenerator = new UniformRandomGenerator();
        double alpha = randomGenerator.getRandomNumber(0.0, 1.0);
        //alpha = 1.0;
        int minEdge = graph.getMaxEdge();
        int maxEdge = graph.getMinEdge();

        double miu = minEdge + alpha * (maxEdge - minEdge);

        // Construct restricted candidate list
        Vector<Pair> RCL = new Vector<>();
        for(int i=1; i<graph.getEdges().size(); i++){
            for(int j=0; j<graph.getEdges().get(i).size(); j++){
                int u1 = i;
                int v1 = graph.getEdges().get(i).get(j);
                int w1;
                if(graph.getGraphArr().get(u1).get(v1) != null){
                    w1 = graph.getGraphArr().get(u1).get(v1);
                    if(w1 >= miu){
                        RCL.add(new Pair(u1, v1));
                    }
                }
            }
        }

        // Randomly Select an edge from RCL
        Random random = new Random();
        int min = 0;
        int max = RCL.size()-1;
        int selectedEdge = random.nextInt(max - min + 1) + min;

        // Construct 2 set
        X.clear();
        Y.clear();
        Set<Integer> V_bar = new HashSet<>();       // Set of nodes who aren't assigned yet
        for(int i=1; i<=N; i++){
            V_bar.add(i);
        }

        this.sigmaArr.get(0).clear();
        this.sigmaArr.get(1).clear();
        this.sigmaArr.get(2).clear();
        for(int i=0; i<=N; i++){
            this.sigmaArr.get(0).add(0);
            this.sigmaArr.get(1).add(0);     // SigmaX
            this.sigmaArr.get(2).add(0);     // SigmaY
        }

        // Add them to X and Y set
        X.add(RCL.get(selectedEdge).getFirst());
        this.sigmaArr.get(0).set(RCL.get(selectedEdge).getFirst(), 1);
        Y.add(RCL.get(selectedEdge).getSecond());
        this.sigmaArr.get(0).set(RCL.get(selectedEdge).getSecond(), 2);

        // Remove those already assigned nodes from V_bar
        V_bar.remove(RCL.get(selectedEdge).getFirst());
        V_bar.remove(RCL.get(selectedEdge).getSecond());

        for(int i=1; i<=N; i++){
//            this.sigmaArr.get(1).set(i, sigmaX(Y,i));
//            this.sigmaArr.get(1).set(i, sigmaY(X,i));
            //Update sigmaX values;
            if(this.graph.getGraphArr().get(i).get(RCL.get(selectedEdge).getFirst()) != null){
                this.sigmaArr.get(2).set(i, this.graph.getGraphArr().get(i).get(RCL.get(selectedEdge).getFirst()));
            }

            // Update sigmaY values;
            if(this.graph.getGraphArr().get(i).get(RCL.get(selectedEdge).getSecond()) != null){
                this.sigmaArr.get(1).set(i, this.graph.getGraphArr().get(i).get(RCL.get(selectedEdge).getSecond()));
            }
        }

        int cnt = 0;

        while(!V_bar.isEmpty()){
            cnt++;
            //System.out.println(cnt);
            int minSigmaX, minSigmaY, maxSigmaX, maxSigmaY;

            //Initialize
            minSigmaX = minSigmaY = Integer.MAX_VALUE;
            maxSigmaX = maxSigmaY = Integer.MIN_VALUE;

            for (int node : V_bar) {
                int tmp = this.sigmaArr.get(1).get(node);       // Get sigmaX value of that node
                minSigmaX = Math.min(minSigmaX, tmp);
                maxSigmaX = Math.max(maxSigmaX, tmp);

                tmp = this.sigmaArr.get(2).get(node);           // Get sigmaY value of that node
                minSigmaY = Math.min(minSigmaY, tmp);
                maxSigmaY = Math.max(maxSigmaY, tmp);
            }

            int wMin = Math.min(minSigmaX, minSigmaY);
            int wMax = Math.max(maxSigmaX, maxSigmaY);
            miu = wMin + alpha * (wMax - wMin);


            // Restricted List construction
            Vector<Integer> RCLnodes = new Vector<>();
            for(int node : V_bar){
                if(Math.max(this.sigmaArr.get(1).get(node), this.sigmaArr.get(2).get(node)) >= miu){
                    RCLnodes.add(node);
                }
            }

            // Randomly Select a node from RCL
            random = new Random();
            min = 0;
            max = RCLnodes.size()-1;
            int idx = random.nextInt(max - min + 1) + min;
            int selectedNode = RCLnodes.get(idx);

            if(this.sigmaArr.get(1).get(selectedNode) > this.sigmaArr.get(2).get(selectedNode)){
                X.add(selectedNode);
                this.sigmaArr.get(0).set(selectedNode, 1);

                // Update sigmaX values
                for( int i=1; i<=N; i++){
                    if(this.graph.getGraphArr().get(i).get(selectedNode) != null){
                        this.sigmaArr.get(2).set(i, this.sigmaArr.get(2).get(i) + this.graph.getGraphArr().get(i).get(selectedNode));
                    }
                }
            }
            else {
                Y.add(selectedNode);
                this.sigmaArr.get(0).set(selectedNode, 2);

                // Update sigmaY values
                for( int i=1; i<=N; i++){
                    if(this.graph.getGraphArr().get(i).get(selectedNode) != null){
                        this.sigmaArr.get(1).set(i, this.sigmaArr.get(1).get(i) + this.graph.getGraphArr().get(i).get(selectedNode));
                    }
                }
            }
            V_bar.remove(selectedNode);
        }

        return this.getMaxCutWeight();
    }


    public int localSearchMaxCut(){
        boolean change = true;
        while(change){
            change = false;
            for(int i=1; i<=this.N; i++){
                if(change) break;

                if(this.X.contains(i) && this.sigmaArr.get(2).get(i) > this.sigmaArr.get(1).get(i)){
                    this.X.remove(i);
                    this.Y.add(i);
                    change = true;
                    statArr[5]++;       //Update stat;

                    // Update sigma values
                    for(int j=1; j<=N; j++){
                        if(this.graph.getGraphArr().get(i).get(j) != null){
                            this.sigmaArr.get(1).set(j, this.sigmaArr.get(1).get(j) + this.graph.getGraphArr().get(i).get(j));
                            this.sigmaArr.get(2).set(j, this.sigmaArr.get(2).get(j) - this.graph.getGraphArr().get(i).get(j));
                        }
                    }
                }
                else if(this.Y.contains(i) && this.sigmaArr.get(2).get(i) < this.sigmaArr.get(1).get(i)){
                    this.Y.remove(i);
                    this.X.add(i);
                    change = true;
                    statArr[5]++;       //Update stat;


                    // Update sigma values
                    for(int j=1; j<=N; j++){
                        if(this.graph.getGraphArr().get(i).get(j) != null){
                            this.sigmaArr.get(1).set(j, this.sigmaArr.get(1).get(j) - this.graph.getGraphArr().get(i).get(j));
                            this.sigmaArr.get(2).set(j, this.sigmaArr.get(2).get(j) + this.graph.getGraphArr().get(i).get(j));
                        }
                    }
                }
            }
        }
        return this.getMaxCutWeight();
    }


    public int simpleRandomizedMaxCut(){
        X.clear();
        Y.clear();
        Set<Integer> V_bar = new HashSet<>();       // Set of nodes who aren't assigned yet
        for(int i=1; i<=N; i++){
            V_bar.add(i);
            Random random = new Random();
            if(random.nextInt()%2 == 0){
                X.add(i);
            }
            else{
                Y.add(i);
            }
        }
        //System.out.println( X.size() + "  " + Y.size() + " ");
        int temp = this.getMaxCutWeight();
        statArr[2] += temp; // Update stat
        return temp;
    }


    public int simpleGreedyMaxCut(){
        int minEdge = graph.getMaxEdge();
        int maxEdge = graph.getMinEdge();

        double miu = minEdge + (maxEdge - minEdge);

        // Construct restricted candidate list
        Vector<Pair> RCL = new Vector<>();
        for(int i=1; i<graph.getEdges().size(); i++){
            for(int j=0; j<graph.getEdges().get(i).size(); j++){
                int u1 = i;
                int v1 = graph.getEdges().get(i).get(j);
                int w1;
                if(graph.getGraphArr().get(u1).get(v1) != null){
                    w1 = graph.getGraphArr().get(u1).get(v1);
                    if(w1 >= miu){
                        RCL.add(new Pair(u1, v1));
                        break;
                    }
                }
            }
        }


        int selectedEdge = 0;

        // Construct 2 set
        X.clear();
        Y.clear();
        Set<Integer> V_bar = new HashSet<>();       // Set of nodes who aren't assigned yet
        for(int i=1; i<=N; i++){
            V_bar.add(i);
        }

        this.sigmaArr.get(0).clear();
        this.sigmaArr.get(1).clear();
        this.sigmaArr.get(2).clear();
        for(int i=0; i<=N; i++){
            this.sigmaArr.get(0).add(0);
            this.sigmaArr.get(1).add(0);     // SigmaX
            this.sigmaArr.get(2).add(0);     // SigmaY
        }

        // Add them to X and Y set
        X.add(RCL.get(selectedEdge).getFirst());
        this.sigmaArr.get(0).set(RCL.get(selectedEdge).getFirst(), 1);
        Y.add(RCL.get(selectedEdge).getSecond());
        this.sigmaArr.get(0).set(RCL.get(selectedEdge).getSecond(), 2);

        // Remove those already assigned nodes from V_bar
        V_bar.remove(RCL.get(selectedEdge).getFirst());
        V_bar.remove(RCL.get(selectedEdge).getSecond());

        for(int i=1; i<=N; i++){
            //Update sigmaX values;
            if(this.graph.getGraphArr().get(i).get(RCL.get(selectedEdge).getFirst()) != null){
                this.sigmaArr.get(2).set(i, this.graph.getGraphArr().get(i).get(RCL.get(selectedEdge).getFirst()));
            }

            // Update sigmaY values;
            if(this.graph.getGraphArr().get(i).get(RCL.get(selectedEdge).getSecond()) != null){
                this.sigmaArr.get(1).set(i, this.graph.getGraphArr().get(i).get(RCL.get(selectedEdge).getSecond()));
            }
        }

        int cnt = 0;

        while(!V_bar.isEmpty()){
            cnt++;
            //System.out.println(cnt);
            int minSigmaX, minSigmaY, maxSigmaX, maxSigmaY;

            //Initialize
            minSigmaX = minSigmaY = Integer.MAX_VALUE;
            maxSigmaX = maxSigmaY = Integer.MIN_VALUE;

            for (int node : V_bar) {
                int tmp = this.sigmaArr.get(1).get(node);       // Get sigmaX value of that node
                minSigmaX = Math.min(minSigmaX, tmp);
                maxSigmaX = Math.max(maxSigmaX, tmp);

                tmp = this.sigmaArr.get(2).get(node);           // Get sigmaY value of that node
                minSigmaY = Math.min(minSigmaY, tmp);
                maxSigmaY = Math.max(maxSigmaY, tmp);
            }

            int wMin = Math.min(minSigmaX, minSigmaY);
            int wMax = Math.max(maxSigmaX, maxSigmaY);
            miu = wMin + (wMax - wMin);


            // Restricted List construction
            Vector<Integer> RCLnodes = new Vector<>();
            for(int node : V_bar){
                if(Math.max(this.sigmaArr.get(1).get(node), this.sigmaArr.get(2).get(node)) >= miu){
                    RCLnodes.add(node);
                    break;
                }
            }

            int selectedNode = RCLnodes.get(0);

            if(this.sigmaArr.get(1).get(selectedNode) > this.sigmaArr.get(2).get(selectedNode)){
                X.add(selectedNode);
                this.sigmaArr.get(0).set(selectedNode, 1);

                // Update sigmaX values
                for( int i=1; i<=N; i++){
                    if(this.graph.getGraphArr().get(i).get(selectedNode) != null){
                        this.sigmaArr.get(2).set(i, this.sigmaArr.get(2).get(i) + this.graph.getGraphArr().get(i).get(selectedNode));
                    }
                }
            }
            else {
                Y.add(selectedNode);
                this.sigmaArr.get(0).set(selectedNode, 2);

                // Update sigmaY values
                for( int i=1; i<=N; i++){
                    if(this.graph.getGraphArr().get(i).get(selectedNode) != null){
                        this.sigmaArr.get(1).set(i, this.sigmaArr.get(1).get(i) + this.graph.getGraphArr().get(i).get(selectedNode));
                    }
                }
            }
            V_bar.remove(selectedNode);
        }
        int temp = this.getMaxCutWeight();
        statArr[3] = temp;
        return temp;
    }

    public int GRASP(int maxIteration){
        int best = 0;
        for(int i=1; i<=maxIteration; i++){
            int semiGreedySol = this.semiGreedyMaxCut();
            statArr[4] += semiGreedySol;
            //System.out.println("Semi Greedy Solution: " + semiGreedySol);
            int afterLocalSearchValue = this.localSearchMaxCut();
            statArr[6] += afterLocalSearchValue;        // Update stat
            //System.out.println("After local search: " + afterLocalSearchValue);
            //System.out.println();
            best = Math.max(best, afterLocalSearchValue);
        }
        //System.out.println("Best : "+ best);

        //Update stat
        statArr[8] = best;
        statArr[7] = maxIteration;
        return best;
    }
}
