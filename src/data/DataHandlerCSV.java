package data;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVWriter;
import model.Messwerte;

public class DataHandlerCSV {
    private static List<Messwerte> messwerteList=new ArrayList<>();
    private static final String path = "data/messwerte.csv";
    public static void main(String[] args) {

        List<Messwerte> csvData = createCsvDataExemple();

        List <String[]> finalData = toStringList(csvData);

        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            writer.writeAll(finalData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static List<Messwerte> createCsvDataExemple() {
        List<Messwerte> messwerteList=new ArrayList<>();
        /*
        messwerteList.add(new Messwerte("23.22.2021",100,20,102,30));
        messwerteList.add(new Messwerte("23.22.2021",100,20,102,30));
        messwerteList.add(new Messwerte("23.22.2021",100,20,102,30));
        */

        return messwerteList;
    }

    public static List<String[]> toStringList(List<Messwerte> messwerteList){
        List<String[]> stringList=new ArrayList<>();

        String[] header={"Timestamp","relative Einspeisung","absolute Einspeisung","relative Bezug","relative Bezug"};
        stringList.add(header);

        for (Messwerte messwerte:messwerteList) {
            stringList.add(messwerte.toStringArray());
        }

        return stringList;
    }
}