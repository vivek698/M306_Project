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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.FileHandler;

public class DataHandler {
    static List<Long> timeStamp;
    static TreeMap<Long, Messwerte> map;
    static long millis;
    static long resolution;
    static String startDateTime;
    static boolean strombezogen;

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
            NodeList docIDList
                    = doc.getElementsByTagName("rsm:InstanceDocument");


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

                    startDateTime = tElement
                                    .getElementsByTagName("rsm:StartDateTime")
                                    .item(0)
                                    .getTextContent();

                    Date date = new Date();

                    date.setYear(Integer.parseInt(startDateTime.substring(0,4)));

                    date.setMonth(Integer.valueOf(startDateTime.substring(5,7)) -1 );

                    date.setDate(Integer.valueOf(startDateTime.substring(8,10)));

                    date.setHours(Integer.valueOf(startDateTime.substring(11,13)));

                    date.setMinutes(Integer.valueOf(startDateTime.substring(14,16)));

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


            for (int i = 0; i < docIDList.getLength(); i++) {
                Node node = docIDList.item(i);
                System.out.println("\nNode Name :"
                        + node.getNodeName());

                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element)node;
                    String docID = tElement
                            .getElementsByTagName("rsm:DocumentID")
                            .item(0)
                            .getTextContent();
                    docID = docID.substring(docID.length() - 3);

                    if (docID.equals("742")){
                        System.out.println("Strom wird bezogen (" + docID + ")");
                        strombezogen = true;
                    } else {
                        System.out.println("Strom wird eingespeist (" + docID + ")");
                        strombezogen = false;
                    }
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
                    long Sequence = Long.valueOf(tElement.getElementsByTagName("rsm:Sequence").item(0).getTextContent());
                    System.out.println("Volume: "
                            + tElement
                            .getElementsByTagName("rsm:Volume")
                            .item(0)
                            .getTextContent());

                    String volume = tElement
                            .getElementsByTagName("rsm:Volume")
                            .item(0)
                            .getTextContent();


                    if (map.get(millis + ((Sequence - 1) * resolution)) == null) {
                        map.put(millis + ((Sequence - 1) * resolution), new Messwerte());
                    }

                    map.get(millis + ((Sequence - 1) * resolution)).setTimestamp(millis + ((Sequence - 1) * resolution));

                    if (strombezogen = true) {
                        map.get(millis + ((Sequence - 1) * resolution)).setRelativerBezug(Double.valueOf(volume));
                        System.out.println("Realtiver Bezug von: " + millis + ((Sequence - 1) * resolution) + " ist "
                                + map.get(millis + ((Sequence - 1) * resolution)).getRelativerBezug());

                    }   else {
                        map.get(millis + ((Sequence - 1) * resolution)).setRelativeEinspeisung(Double.valueOf(volume));
                        System.out.println("Realtive Einspeisung von: " + millis + ((Sequence - 1) * resolution) + " ist "
                                + map.get(millis + ((Sequence - 1) * resolution)).getRelativerBezug());
                    }




                }

                System.out.println("Ertan: " + map.get(millis + 900000 ));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("____________Treemap Loop_________________");

        for (Map.Entry<Long, Messwerte>
                entry : map.entrySet())
            System.out.println(
                    "[" + entry.getKey()
                            + ", " + entry.getValue().getRelativerBezug()
                            + ", " + entry.getValue().getRelativeEinspeisung()
                            + "]");



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

    public static List<Long> getTimeStamp() {
        return timeStamp;
    }

    public static void setTimeStamp(List<Long> timeStamp) {
        DataHandler.timeStamp = timeStamp;
    }

    public static TreeMap<Long, Messwerte> getMap() {
        return map;
    }

    public static void setMap(TreeMap<Long, Messwerte> map) {
        DataHandler.map = map;
    }
}
