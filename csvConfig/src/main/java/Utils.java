import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import tree.Tag;
import tree.TreeNode;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Utils {

    public static String[] readTags(String file) throws IOException {

        FileReader filereader = new FileReader(file);
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
        return csvReader.readNext();

    }

    public static List<String[]> readContent(String file) throws IOException {
        FileReader filereader = new FileReader(file);

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(filereader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();

        return csvReader.readAll();
    }

    public static void printData(List<String[]> allData) {
        for (String[] row : allData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }

    public static void printData(String[] row) {

        for (String cell : row) {
            System.out.print(cell + "\t");
        }
    }

    public static TreeNode<Tag> generateTagNameTree(String[] tags) {
        TreeNode<Tag> tree = new TreeNode<Tag>(new Tag(tags[0]));
        for (String tag : tags) {
            String[] tagStructure = tag.split("\\\\");
            for (int i = 1; i < tagStructure.length; i++) {
                if (tree.findNode(tree, tagStructure[i]) == null) {
                    if (tree.findNode(tree, tagStructure[i - 1]) != null) {
                        tree.findNode(tree, tagStructure[i - 1]).addChild(new Tag(tagStructure[i]));
                    }

                }
            }
        }
        return tree;
    }

    public static void writeDataToFile(List<String[]> allData, String[] tags, TreeNode<Tag> tree, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            StringBuffer config = new StringBuffer();
            bw.append("<events>");

            for (String[] event : allData) {

                for (int i = 1; i < event.length; i++) {

                    String[] eventNames = tags[i].split("\\\\");
                    String tag = eventNames[eventNames.length - 1];

                    if (tree.findNode(tree, tag) != null) {
                        ((Tag) tree.findNode(tree, tag).data).setValue(event[i]);
                    }
                }

                config = tree.getXML(2, config);
                config.append("\n");
                bw.append(config);
                bw.flush();
            }

            bw.append("</events>");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
