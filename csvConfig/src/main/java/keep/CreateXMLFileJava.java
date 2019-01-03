package keep;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tree.Tag;
import tree.TreeNode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.Instant;

public class CreateXMLFileJava {

    public static final String xmlFilePath = "C:\\APPS\\projects\\csvConfig\\src\\main\\resources\\";

    public static void treeToFile(TreeNode tree) {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();
            document.setStrictErrorChecking(false);
            document.setXmlStandalone(false);

            document = transformConfigToDoc(document, tree);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File(xmlFilePath + Instant.now().toEpochMilli() + ".xml"));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public static Document transformConfigToDoc(Document document, TreeNode tree) {


        // root element
        Element element = document.createElement(((Tag) tree.data).getName());
        document.appendChild(element);

        // employee element
        if (tree.children.size() > 0) {
            for (Object nodeObj0 : tree.children) {
                TreeNode node0 = (TreeNode) nodeObj0;
                String elementName0 = ((Tag) node0.data).getName();
                Element element0 = document.createElement(elementName0);

                if (node0.children.size() > 0) {
                    for (Object nodeObj1 : node0.children) {
                        TreeNode node1 = (TreeNode) nodeObj1;
                        String elementName1 = ((Tag) node1.data).getName();
                        Element element1 = document.createElement(elementName1);


                        if (node1.children.size() > 0) {
                            for (Object nodeObj2 : node0.children) {
                                TreeNode node2 = (TreeNode) nodeObj2;
                                String elementName2 = ((Tag) node2.data).getName();
                                Element element2 = document.createElement(elementName2);


                                element1.appendChild(element2);
                            }

                        } else {
                            element1.appendChild(document.createTextNode(((Tag) node1.data).getValue()));
                            element0.appendChild(element1);
                        }
                        element0.appendChild(element1);

                    }

                } else {
                    element0.appendChild(document.createTextNode(((Tag) node0.data).getValue()));
                    element.appendChild(element0);
                }
                element.appendChild(element0);
            }


        } else {
            Element employee = document.createElement(((Tag) tree.data).getName());
            employee.appendChild(document.createTextNode(((Tag) tree.data).getValue()));
            element.appendChild(employee);
        }
        return document;
    }

}