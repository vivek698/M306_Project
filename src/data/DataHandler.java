package data;

import model.Messwerte;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import view.Gui;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataHandler {
    static List<Long> timeStampList;
    static TreeMap<Long, Messwerte> map;
    static long millis;
    static long resolution;
    static String startDateTime;
    static boolean strombezogen;

    public DataHandler() {
        timeStampList = new ArrayList<> ();
        map = new TreeMap();
        readSDAT();
        readESL();
        loopTreeMap();
    }



    public static void readSDAT() {

        try {

            String myDirectoryPath = "data/sdat/";
            File dir = new File(myDirectoryPath);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File file : directoryListing) {
                    DocumentBuilderFactory dbf
                            = DocumentBuilderFactory.newInstance();

                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(file);

                    doc.getDocumentElement().normalize();

                    NodeList observationList
                            = doc.getElementsByTagName("rsm:Observation");
                    NodeList timeList
                            = doc.getElementsByTagName("rsm:MeteringData");
                    NodeList docIDList
                            = doc.getElementsByTagName("rsm:InstanceDocument");


                    for (int i = 0; i < timeList.getLength(); i++){
                        Node node = timeList.item(i);

                        if (node.getNodeType()
                                == Node.ELEMENT_NODE) {
                            Element tElement = (Element)node;

                            startDateTime = tElement
                                    .getElementsByTagName("rsm:StartDateTime")
                                    .item(0)
                                    .getTextContent();

                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
                            Date date = df.parse(startDateTime);



                            millis = date.getTime();



                            resolution = Integer.valueOf(tElement
                                    .getElementsByTagName("rsm:Resolution")
                                    .item(1)
                                    .getTextContent()) * 600000;
                        }
                    }


                    for (int i = 0; i < docIDList.getLength(); i++) {
                        Node node = docIDList.item(i);

                        if (node.getNodeType()
                                == Node.ELEMENT_NODE) {
                            Element tElement = (Element)node;
                            String docID = tElement
                                    .getElementsByTagName("rsm:DocumentID")
                                    .item(0)
                                    .getTextContent();
                            docID = docID.substring(docID.length() - 3);

                            if (docID.equals("742")){
                                strombezogen = true;
                            } else {
                                strombezogen = false;
                            }
                        }
                    }


                    for (int i = 0; i < observationList.getLength(); i++) {
                        Node node = observationList.item(i);

                        if (node.getNodeType()
                                == Node.ELEMENT_NODE) {
                            Element tElement = (Element)node;
                            long Sequence = Long.valueOf(tElement.getElementsByTagName("rsm:Sequence").item(0).getTextContent());

                            String volume = tElement
                                    .getElementsByTagName("rsm:Volume")
                                    .item(0)
                                    .getTextContent();


                            if (map.get(millis + ((Sequence - 1) * resolution)) == null) {
                                map.put(millis + ((Sequence - 1) * resolution), new Messwerte());
                                timeStampList.add(millis + ((Sequence - 1) * resolution));
                            }

                            map.get(millis + ((Sequence - 1) * resolution)).setTimestamp(millis + ((Sequence - 1) * resolution));

                            if (strombezogen) {
                                map.get(millis + ((Sequence - 1) * resolution)).setRelativerBezug(Double.valueOf(volume));

                            }   else {
                                map.get(millis + ((Sequence - 1) * resolution)).setRelativeEinspeisung(Double.valueOf(volume));
                            }


                        }

                    }

                }
            } else {
                System.out.println("Directory invalid");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loopTreeMap() {
        System.out.println("____________Treemap Loop_________________");

        for (Map.Entry<Long, Messwerte>
                entry : map.entrySet())
            System.out.println(
                    "[TS: " + entry.getKey()
                            + ", Relativer Bezug: " + entry.getValue().getRelativerBezug()
                            + ", Relative Einspeisung: " + entry.getValue().getRelativeEinspeisung()
                            + ", Absoluter Bezug: " + entry.getValue().getAbsoluterBezug()
                            + ", Absolute Einspeisung: " + entry.getValue().getAbsoluteEinspeisung()
                            + "]");
    }

    public static void readESL() {
        try {

            String myDirectoryPath = "data/esl/";
            File dir = new File(myDirectoryPath);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File file : directoryListing) {

                    //Absolute Werte
                    double absolutBezug = 0.0;
                    double absolutEinspeisung = 0.0;
                    String timestamp = "";

                    DocumentBuilderFactory dbf
                            = DocumentBuilderFactory.newInstance();

                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(file);

                    doc.getDocumentElement().normalize();

                    NodeList valueList
                            = doc.getElementsByTagName("ValueRow");
                    NodeList timeList
                            = doc.getElementsByTagName("TimePeriod");

                    for (int i = 0; i < timeList.getLength(); i++) {
                        Node node = timeList.item(i);

                        if (node.getNodeType()
                                == Node.ELEMENT_NODE) {
                            Element tElement = (Element)node;
                            timestamp = tElement.getAttribute("end");
                        }
                    }

                    for (int i = 0; i < valueList.getLength(); i++) {
                        Node node = valueList.item(i);

                        if (node.getNodeType()
                                == Node.ELEMENT_NODE) {
                            Element tElement = (Element)node;
                            if (tElement.getAttribute("obis").equals("1-1:1.8.1") ||
                                    tElement.getAttribute("obis").equals("1-1:1.8.2") ||
                                    tElement.getAttribute("obis").equals("1-1:2.8.1") ||
                                    tElement.getAttribute("obis").equals("1-1:2.8.2")){

                                //Absoluter Wert 1.8
                                if (tElement.getAttribute("obis").equals("1-1:1.8.1") ||
                                        tElement.getAttribute("obis").equals("1-1:1.8.2")){
                                    absolutBezug += Double.parseDouble(tElement.getAttribute("value"));
                                }
                                //Absoluter Wert 2.8
                                else {
                                    absolutEinspeisung += Double.parseDouble(tElement.getAttribute("value"));
                                }

                            }
                        }
                    }


                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                    Date date = df.parse(timestamp);



                    millis = date.getTime();


                    if (map.get(millis) == null) {
                        map.put((millis), new Messwerte());
                        timeStampList.add(millis);
                    }

                    map.get(millis).setTimestamp(millis);
                    map.get(millis).setAbsoluterBezug(absolutBezug);
                    map.get(millis).setAbsoluteEinspeisung(absolutEinspeisung);
                    System.out.println("ABezug: " + absolutBezug);
                    System.out.println("AEinspeisung: " + absolutEinspeisung);


                }
            } else {
                System.out.println("Directory invalid");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readJSON() {

    }

    public static List <Long> getTimeStampList() {
        timeStampList = new ArrayList<>(new HashSet<>(timeStampList));
        return timeStampList;
    }

    public static void setTimeStampList(List<Long> timeStampList) {
        DataHandler.timeStampList = timeStampList;
    }

    public static TreeMap<Long, Messwerte> getMap() {
        return map;
    }

    public static void setMap(TreeMap<Long, Messwerte> map) {
        DataHandler.map = map;
    }
}
