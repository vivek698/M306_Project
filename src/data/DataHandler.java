package data;

import model.Messwerte;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
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
            File file = new File("data/sdat/20190313_093127_12X-0000001216-O_E66_12X-LIPPUNEREM-T_ESLEVU121963_-279617263.xml");

            JAXBContext jaxbContext = JAXBContext.newInstance(Messwerte.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            unmarshaller.unmarshal(file);

            Messwerte messwerte = (Messwerte) unmarshaller.unmarshal(file);

            //System.out.println(messwerte.getRelativeEinspeisung());


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
