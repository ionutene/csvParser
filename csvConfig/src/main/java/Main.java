import tree.Tag;
import tree.TreeNode;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] ar) {
        String csvInput = "C:\\APPS\\projects\\csvConfig\\src\\main\\resources\\all.csv";
        String xmlOutput = "C:\\APPS\\projects\\csvConfig\\src\\main\\resources\\all.xml";

        try {
            //get all tagValues
            List<String[]> allData = Utils.readContent(csvInput);

            //get tagNames
            String[] tags = Utils.readTags(csvInput);

            //populate a event
            TreeNode<Tag> tree = Utils.generateTagNameTree(tags);

            //print xml formatted data
            Utils.writeDataToFile(allData, tags, tree,xmlOutput);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
