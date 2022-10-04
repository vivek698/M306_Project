package data;

import model.Messwerte;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.TreeMap;
import java.util.logging.FileHandler;

public class DataHandler {

    TreeMap map;

    public DataHandler() {
        map = new TreeMap();
    }


    public static void readSDAT() {

        try {
            File file = new File(
                    "data/sdat/20190313_093127_12X-0000001216-O_E66_12X-LIPPUNEREM-T_ESLEVU121963_-279617263.xml");

            DocumentBuilderFactory dbf
                    = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();
            System.out.println(
                    "Root element: "
                            + doc.getDocumentElement().getNodeName());

            NodeList nodeList
                    = doc.getElementsByTagName("rsm:Observation");

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                System.out.println("\nNode Name :"
                        + node.getNodeName());

                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element)node;
                    System.out.println("Sequence: "
                            + tElement
                            .getElementsByTagName("rsm:Sequence")
                            .item(0)
                            .getTextContent());
                    System.out.println("Volume: "
                            + tElement
                            .getElementsByTagName("rsm:Volume")
                            .item(0)
                            .getTextContent());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readSDAT();
    }

    public static void readESL() {

    }

    public static void readJSON() {

    }
}
