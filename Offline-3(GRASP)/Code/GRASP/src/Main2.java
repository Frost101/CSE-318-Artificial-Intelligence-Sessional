import java.io.*;

public class Main2 {
    public static void main(String[] args) {

        String inputFilePath = "g1.rud";
        int N,M;
        int u,v,w;
        Graph graph = null;
        try {
            FileReader fileReader = new FileReader(inputFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String in;
            while ((in = bufferedReader.readLine()) != null) {
                String tmp[] = in.split(" ");
                if(tmp.length == 2){
                  N = Integer.parseInt(tmp[0]);
                  M = Integer.parseInt(tmp[1]);
                  graph = new Graph(N,M);
                }
                else if(tmp.length == 3){
                    u = Integer.parseInt(tmp[0]);
                    v = Integer.parseInt(tmp[1]);
                    w = Integer.parseInt(tmp[2]);
                    assert graph != null;
                    graph.addEdges(u, v, w);
                }
                else {
                    System.out.println("Invalid file!!!");
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MaxCut maxCut = new MaxCut(graph);
        maxCut.GRASP(10);
        maxCut.GRASP(20);
        maxCut.GRASP(50);
        //System.out.println(maxCut.simpleRandomizedMaxCut());
        //System.out.println(maxCut.simpleGreedyMaxCut());



    }
}
