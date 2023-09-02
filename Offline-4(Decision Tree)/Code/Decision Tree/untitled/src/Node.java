import java.util.HashMap;

public class Node {

    HashMap<Integer, Node> children;
    Boolean isLeaf;

    int attrIndex;

    int output;
    Node(){
        isLeaf = false;
        children = new HashMap<>();
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public HashMap<Integer, Node> getChildren() {
        return children;
    }

    public void addChildren(Integer attrIndex, Node node){
        this.children.put(attrIndex, node);
    }

    public void setAttrIndex(int attrIndex) {
        this.attrIndex = attrIndex;
    }

    public int getAttrIndex() {
        return attrIndex;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public int getOutput() {
        return output;
    }
}
