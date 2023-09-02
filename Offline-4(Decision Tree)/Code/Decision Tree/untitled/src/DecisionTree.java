import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DecisionTree {
    final String fileName = "car.data";
    int attributeCount = 0;

    Vector<HashMap<String,Integer>> attrMapper;     // Maps each value of an attribute to a unique number
    HashMap<String,Integer> outMapper;              // Map each unique output value to a unique integer
    HashMap<Integer,String> outMapper2;             // Reverse of outmapper

    Vector<HashMap<Integer, Integer>> examples;     // examples = train + test

    Vector<Integer> outExamples;                    // Output of the examples

    Set<Integer> trainIndex;                        // Selected examples to train the model
    Set<Integer> testIndex;                         // Selected examples to test the model

    Set<Integer> attrIndex;                         // List of attribute index

    Node root;

    DecisionTree(){
        attributeCount = -1;
        attrMapper = new Vector<>();
        outMapper = new HashMap<>();
        outMapper2 = new HashMap<>();
        examples = new Vector<>();
        outExamples = new Vector<>();
        trainIndex = new HashSet<>();
        testIndex = new HashSet<>();
        attrIndex = new HashSet<>();
        root = new Node();
    }


    public double log(double num){
        // Modified 2 base log
        // log2(0) will return 0, instead of undefined/infinity
        if(num <= 0.0){
            return 0.0;
        }
        else{
            double result = Math.log(num) / Math.log(2.0);
            return result;
        }
    }


    public void inputData(){
        File inputFile = new File(fileName);
        Vector<Integer> attrIndexes = new Vector<>();
        int outIndexes = 0;     // To get unique possible outputs
        Boolean flag = true;
        try {
            Scanner scanner = new Scanner(inputFile);
            int idx = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitArray = line.split(",");

                if(flag){
                    // Update attribute count
                    // last one is not attribute, it's the final result
                    attributeCount = splitArray.length - 1;

                    // Initialize First
                    attrMapper.clear();
                    attrIndexes.clear();
                    attrMapper = new Vector<>();
                    for(int i=0; i<attributeCount; i++){
                        attrMapper.add(new HashMap<>());
                        attrIndexes.add(0);
                    }

                    outMapper.clear();
                    outMapper = new HashMap<>();
                    flag = false; // Important
                }


                // Map each unique attribute to a unique integer
                for(int i=0; i<attributeCount; i++){
                    if(attrMapper.get(i).get(splitArray[i]) == null){
                        attrMapper.get(i).put(splitArray[i], attrIndexes.get(i));
                        attrIndexes.set(i, attrIndexes.get(i) + 1);     // Increase Unique attribute Count
                    }
                }

                // Map each unique output to a unique integer
                if(outMapper.get(splitArray[attributeCount]) == null){
                    outMapper.put(splitArray[attributeCount], outIndexes);
                    outMapper2.put(outIndexes, splitArray[attributeCount]);
                    outIndexes++;       // Update Index, Important
                }

                // Save Examples
                examples.add(new HashMap<>());
                for( int i=0; i<attributeCount; i++){
                    examples.get(idx).put(i, attrMapper.get(i).get(splitArray[i]));
                }

                // Save Examples Output
                outExamples.add(outMapper.get(splitArray[attributeCount]));

                idx++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
//        System.out.println(attrMapper.get(0).size() + "-----" + attrMapper.get(1).size() + "-----" + attrMapper.get(2).size() + "-----" + attrMapper.get(3).size() + "-----" + attrMapper.get(4).size() + "-----" +attrMapper.get(5).size() + "-----");
//        System.out.println(attrMapper.get(5).get("high"));
//        System.out.println(outMapper.get("unacc"));
//        System.out.println(outMapper2.get(outExamples.get(368)));
    }



    public void split(){
        testIndex.clear();
        trainIndex.clear();

        int upperRange = 100;
        Random random = new Random();
        int randomNumber = random.nextInt(upperRange + 1);

        for (int i=0; i<examples.size(); i++){
            // Split Data
            // Test set gets 20%
            // Train set gets 80%
            randomNumber = random.nextInt(upperRange + 1);
            if(randomNumber >= 80) testIndex.add(i);
            else trainIndex.add(i);
        }
    }


    public void trainModel(){
        this.inputData();
        this.split();

        // Unvisited attributes.. Add all
        attrIndex.clear();
        for(int i=0; i<attributeCount; i++){
            attrIndex.add(i);
        }

        root = learn(this.trainIndex, this.attrIndex, null);
    }




    public double getRemainder(int attr, Set<Integer> exmpl){
        int howManyValues = attrMapper.get(attr).size();
        double remainder = 0.0;

        for(int i=0; i<attrMapper.get(attr).size(); i++){
            Set<Integer> tmpExmpl = new HashSet<>();
            for(int e : exmpl){
                if(examples.get(e).get(attr) == i){
                    tmpExmpl.add(e);
                }
            }
            double entropy = getEntropy(tmpExmpl);
            entropy = (((tmpExmpl.size() * 1.0) / exmpl.size()) * entropy);
            remainder += entropy;
            tmpExmpl.clear();
        }

        return remainder;
    }


    public double getEntropy(Set<Integer> exmpl){
        int[] outCount = new int[outMapper.size()];

        if(exmpl.size() == 0) return 0.0;

        // Initialize
        for(int i=0; i<outMapper.size(); i++){
            outCount[i] = 0;
        }

        for (int i : exmpl) {
            outCount[outExamples.get(i)]++;
        }

        double entropy = 0.0;
        double total = exmpl.size() * 1.0;
        for(int i=0; i<outMapper.size(); i++){
            entropy += ((outCount[i]/total) * log(outCount[i]/total));
        }

        if(entropy < 0.0) entropy = -(entropy);
        return entropy;
    }


    public int getPluralityValue(Set<Integer> exmpl){
        int[] outCount = new int[outMapper.size()];

        // Initialize
        for(int i=0; i<outMapper.size(); i++){
            outCount[i] = 0;
        }

        for (int i : exmpl) {
            outCount[outExamples.get(i)]++;
        }

        int mx = Integer.MIN_VALUE;
        for(int i=0; i<outMapper.size(); i++){
            mx = Math.max(mx, outCount[i]);
        }

        for(int i=0; i<outMapper.size(); i++){
            if(outCount[i] == mx) return i;
        }
        return -1;
    }



    public Boolean isSameClassification(Set<Integer> exmpl){
        int first = -1;
        Boolean flag = true;
        Boolean rt = true;

        for(int i: exmpl){
            if(flag){
                first = outExamples.get(i);
                flag = false;
            }
            else{
                if(first != outExamples.get(i)){
                    rt = false;
                    return rt;
                }
            }
        }
        return rt;
    }


    public Node learn(Set<Integer> exmpl, Set<Integer> attributes, Set<Integer> parent_examples){
        if(exmpl.isEmpty()){
            // if example list is empty then return thr plurality value of parent examples
            if(parent_examples == null){
                System.out.println("Parent examples should not be null here..Needs debugging");
                return null;
            }
            else{
                int tmp = this.getPluralityValue(parent_examples);
                Node node = new Node();
                node.setLeaf(true);
                node.setOutput(tmp);
                return node;
            }
        }
        else if(isSameClassification(exmpl)){
            // If All examples have the same classification then return the classification
            Node node = new Node();
            node.setLeaf(true);
            for(int i : exmpl){
                node.setOutput(outExamples.get(i));
                break;
            }
            return node;
        }
        else if(attributes.isEmpty()){
            // If attribute list is empty then return the plurality value of examples
            int tmp = this.getPluralityValue(exmpl);
            Node node = new Node();
            node.setLeaf(true);
            node.setOutput(tmp);
            return node;
        }
        else{
            // Find the next best attribute and expand the tree;
            double intitialEntropy = getEntropy(exmpl);
            double maxInfoGain = - 1.0;
            int maxAttr = -1;

            // calculate the attribute for which the information gain is maximum
            for(int attr : attributes){
                double remainder = getRemainder(attr, exmpl);
                double infoGain = (intitialEntropy - remainder);
                if(infoGain > maxInfoGain){
                    maxInfoGain = infoGain;
                    maxAttr = attr;
                }
            }



            Node node = new Node();
            node.setAttrIndex(maxAttr); // This attribute to be tested in this node
            for(int i=0; i<attrMapper.get(maxAttr).size(); i++){
                // split examples based on attribute value
                Set<Integer> tmpExmpl = new HashSet<>();
                for(int e : exmpl){
                    if(examples.get(e).get(maxAttr) == i){
                        tmpExmpl.add(e);
                    }
                }

                // create new attribute set
                Set<Integer> tmpAttribute = new HashSet<>();
                for(int a: attributes){
                    if(a!=maxAttr){
                        tmpAttribute.add(a);
                    }
                }

                node.addChildren(i, learn(tmpExmpl, tmpAttribute, exmpl));
            }
            return node;
        }
    }


    public Boolean predict(int exampleIdx){

        int currResult = -1;
        int actualResult = outExamples.get(exampleIdx);

        Node tmp = this.root;

        while (tmp != null){
            if(tmp.getIsLeaf()){
                currResult = tmp.getOutput();
                break;
            }
            else{
                tmp = tmp.getChildren().get(examples.get(exampleIdx).get(tmp.getAttrIndex()));
            }
        }

        if(currResult == actualResult) return true;
        else return false;
    }

    public double predictTestData(){
        int crct = 0;
        for(int data : testIndex){
            if(predict(data)){
                crct++;
            }
        }
        double rate = ((crct * 1.0) / testIndex.size()) * 100.0;

        return rate;
    }
}
