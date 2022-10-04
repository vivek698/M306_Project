package data;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVWriter;
import model.ID_List;
import model.Messwerte;

public class DataHandlerCSV {
    private static List<Messwerte> messwerteList=new ArrayList<>();
    private static final String path = "data/messwerte.csv";
    public static void main(String[] args) {

        ID_List.main(args);
        List <String[]> finalData = toStringList();

        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            writer.writeAll(finalData);
            System.out.println("Hello");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String[]> toStringList(){
        List<String[]> stringList=new ArrayList<>();

        String[] header={"ts","value"};
        stringList.add(header);

        ID_List id735=new ID_List("ID735");
        ID_List id742=new ID_List("ID742");

        //TODO
        for (int i = 0; i < id742.size(); i++) {
            stringList.add(id742.toStringArray(i));
        }

        return stringList;
    }
}