import java.io.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String,String> upperBounds = new HashMap<>();
        upperBounds.put("g1.rud","12078");
        upperBounds.put("g2.rud","12084");
        upperBounds.put("g3.rud","12077");
        upperBounds.put("g4.rud","Not Available");
        upperBounds.put("g5.rud","Not Available");
        upperBounds.put("g6.rud","Not Available");
        upperBounds.put("g7.rud","Not Available");
        upperBounds.put("g8.rud","Not Available");
        upperBounds.put("g9.rud","Not Available");
        upperBounds.put("g10.rud","Not Available");
        upperBounds.put("g11.rud","627");
        upperBounds.put("g12.rud","621");
        upperBounds.put("g13.rud","645");
        upperBounds.put("g14.rud","3187");
        upperBounds.put("g15.rud","3169");
        upperBounds.put("g16.rud","3172");
        upperBounds.put("g17.rud","Not Available");
        upperBounds.put("g18.rud","Not Available");
        upperBounds.put("g19.rud","Not Available");
        upperBounds.put("g20.rud","Not Available");
        upperBounds.put("g21.rud","Not Available");
        upperBounds.put("g22.rud","14123");
        upperBounds.put("g23.rud","14129");
        upperBounds.put("g24.rud","14131");
        upperBounds.put("g25.rud","Not Available");
        upperBounds.put("g26.rud","Not Available");
        upperBounds.put("g27.rud","Not Available");
        upperBounds.put("g28.rud","Not Available");
        upperBounds.put("g29.rud","Not Available");
        upperBounds.put("g30.rud","Not Available");
        upperBounds.put("g31.rud","Not Available");
        upperBounds.put("g32.rud","1560");
        upperBounds.put("g33.rud","1537");
        upperBounds.put("g34.rud","1541");
        upperBounds.put("g35.rud","8000");
        upperBounds.put("g36.rud","7996");
        upperBounds.put("g37.rud","8009");
        upperBounds.put("g38.rud","Not Available");
        upperBounds.put("g39.rud","Not Available");
        upperBounds.put("g40.rud","Not Available");
        upperBounds.put("g41.rud","Not Available");
        upperBounds.put("g42.rud","Not Available");
        upperBounds.put("g43.rud","7027");
        upperBounds.put("g44.rud","7022");
        upperBounds.put("g45.rud","7020");
        upperBounds.put("g46.rud","Not Available");
        upperBounds.put("g47.rud","Not Available");
        upperBounds.put("g48.rud","6000");
        upperBounds.put("g49.rud","6000");
        upperBounds.put("g50.rud","5988");
        upperBounds.put("g51.rud","Not Available");
        upperBounds.put("g52.rud","Not Available");
        upperBounds.put("g53.rud","Not Available");
        upperBounds.put("g54.rud","Not Available");




        String fileName = "out.csv";
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(" ,Problem, , ,Constructive algorithm, ,Local Search, ,GRASP, ,Known best solution or upper bound\n");
            writer.write("Name,|V| or n,|E| or m,Randomized-1,Greedy-1,Semi-greedy-1,No. of iterations,Best Value,No of iterations,Best Value, ,\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(int fileCount=1; fileCount<=54; fileCount++){
            String inputFilePath = "g"+fileCount+".rud";
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

            // Iteration count
            int iterationArr[] = {10,20,50};
            for(int i = 0; i < iterationArr.length; i++ ){
                System.out.println(inputFilePath + "--------"+iterationArr[i]);
                MaxCut maxCut = new MaxCut(graph);
                for(int j=1; j <= iterationArr[i]; j++){
                    maxCut.simpleRandomizedMaxCut();
                }
                maxCut.simpleGreedyMaxCut();
                // Calculate the average
                maxCut.getStatArr()[2] /= iterationArr[i];

                maxCut.GRASP(iterationArr[i]);

                //Calculate the average
                maxCut.getStatArr()[4] /= iterationArr[i];
                maxCut.getStatArr()[5] /= iterationArr[i];
                maxCut.getStatArr()[6] /= iterationArr[i];
                try{
                    writer.write(inputFilePath + "," + maxCut.getStatArr()[0] + "," + maxCut.getStatArr()[1] + "," + maxCut.getStatArr()[2] + "," + maxCut.getStatArr()[3] + "," + maxCut.getStatArr()[4]+ "," + maxCut.getStatArr()[5]+ "," + maxCut.getStatArr()[6]+ "," + maxCut.getStatArr()[7]+ "," + maxCut.getStatArr()[8]+","+upperBounds.get(inputFilePath)+"\n");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
