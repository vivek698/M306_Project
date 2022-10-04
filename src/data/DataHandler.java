package data;

import model.Messwerte;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import view.Gui;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TreeMap;
import java.util.logging.FileHandler;

public class DataHandler {

    static TreeMap<Long, Messwerte> map;
    static long millis;
    static long resolution;

    public DataHandler() {
        map = new TreeMap();

    }

    public static void main(String[] args) {

        Gui gui = new Gui();

        DataHandler dataHandler = new DataHandler();

        dataHandler.readSDAT();
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

            NodeList observationList
                    = doc.getElementsByTagName("rsm:Observation");
            NodeList timeList
                    = doc.getElementsByTagName("rsm:MeteringData");
            NodeList resolutionList
                    = doc.getElementsByTagName("rsm:Resolution");


            for (int i = 0; i < timeList.getLength(); i++){
                Node node = timeList.item(i);
                System.out.println("\nNode Name :"
                        + node.getNodeName());

                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element)node;
                    System.out.println("Start date: "
                            + tElement
                            .getElementsByTagName("rsm:StartDateTime")
                            .item(0)
                            .getTextContent());


                    Date date = new Date();

                    date.setYear(Integer.valueOf(tElement
                            .getElementsByTagName("rsm:StartDateTime")
                            .item(0)
                            .getTextContent().substring(0,4)));

                    date.setMonth(Integer.valueOf(tElement
                            .getElementsByTagName("rsm:StartDateTime")
                            .item(0)
                            .getTextContent().substring(5,7)) -1 );

                    date.setDate(Integer.valueOf(tElement
                            .getElementsByTagName("rsm:StartDateTime")
                            .item(0)
                            .getTextContent().substring(8,10)));

                    date.setHours(Integer.valueOf(tElement
                            .getElementsByTagName("rsm:StartDateTime")
                            .item(0)
                            .getTextContent().substring(11,13)));

                    date.setMinutes(Integer.valueOf(tElement
                            .getElementsByTagName("rsm:StartDateTime")
                            .item(0)
                            .getTextContent().substring(14,16)));

                    date.setSeconds(0);

                    System.out.println(date);


                    millis = date.toInstant().toEpochMilli();
                    System.out.println(millis);



                    System.out.println("End date: "
                            + tElement
                            .getElementsByTagName("rsm:EndDateTime")
                            .item(0)
                            .getTextContent());
                    System.out.println("Interval: "
                            + tElement
                            .getElementsByTagName("rsm:Resolution")
                            .item(1)
                            .getTextContent()
                    );

                    resolution = Integer.valueOf(tElement
                            .getElementsByTagName("rsm:Resolution")
                            .item(1)
                            .getTextContent()) * 60000;
                }
            }


            for (int i = 0; i < observationList.getLength(); i++) {
                Node node = observationList.item(i);
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

                    map.put(millis + ((Long.valueOf(tElement
                            .getElementsByTagName("rsm:Sequence")
                            .item(0)
                            .getTextContent()) - 1) * resolution), new Messwerte());
                }

                System.out.println("Ertan: " + map.get(millis + 900000 ));

            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readESL() {
        try {
            File file = new File(
                    "data/esl/EdmRegisterWertExport_20190131_eslevu_20190322160349.xml");

            DocumentBuilderFactory dbf
                    = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();
            System.out.println(
                    "Root element: "
                            + doc.getDocumentElement().getNodeName());

            NodeList valueList
                    = doc.getElementsByTagName("ValueRow");

            for (int i = 0; i < valueList.getLength(); i++) {
                Node node = valueList.item(i);

                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element)node;
                    if (tElement.getAttribute("obis").equals("1-1:1.8.1") ||
                            tElement.getAttribute("obis").equals("1-1:1.8.2") ||
                            tElement.getAttribute("obis").equals("1-1:2.8.1") ||
                            tElement.getAttribute("obis").equals("1-1:2.8.2")){
                        System.out.println("\nNode Name :" + node.getNodeName());
                        System.out.println(tElement.getAttribute("obis") + " " + tElement.getAttribute("value"));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readJSON() {

    }
}
