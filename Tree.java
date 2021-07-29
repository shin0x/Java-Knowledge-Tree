import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Tree {

    public TreeNode root; // root of the tree

    public Tree() {
        root = new TreeNode();
    }

    // load data from file in a Tree instance
    public void loadTree(String filename) {
        File file = new File(filename);

        try (FileReader fileReader = new FileReader(file); BufferedReader buf = new BufferedReader(fileReader)) {
            buildTree(root, buf);
        } catch (Exception e) {
            System.out.println("Error during tree building : " + e.getMessage());
        }
    }

    // build the three from a buffered reader
    private void buildTree(TreeNode currentNode, BufferedReader buf) throws Exception {
        String line = buf.readLine();

        if (line != null) {
            currentNode.setData(line);

            if (currentNode.isQuestion()) {
                TreeNode yesNode = new TreeNode();
                TreeNode noNode = new TreeNode();
                currentNode.yes = yesNode;
                currentNode.no = noNode;
                buildTree(yesNode, buf);
                buildTree(noNode, buf);
            }
        }
    }

}