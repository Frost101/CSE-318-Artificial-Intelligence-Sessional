public class Main{
    public static void main(String[] args) {

        double meanAccuracy = 0.0;
        double sd = 0.0;

        for(int i=1; i<=20; i++){
            DecisionTree decisionTree = new DecisionTree();
            decisionTree.trainModel();
            double tmp = 0.0;
            tmp = decisionTree.predictTestData();
            meanAccuracy += tmp;
            sd += (tmp * tmp);
        }

        meanAccuracy /= 20.0;
        System.out.println("Mean Accuracy: " + meanAccuracy + "%") ;
        sd = Math.sqrt((sd/20.0)-(meanAccuracy * meanAccuracy));
        System.out.println("Standard Deviatin: " + sd);

    }
}