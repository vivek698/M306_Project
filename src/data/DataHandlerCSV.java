package data;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVWriter;
import model.ID_List_CSV;
import model.Main;

public class DataHandlerCSV {
    private static final String path735 = "data/ID_Einspeisung.csv";
    private static final String path742 = "data/ID_Bezug.csv";
    public static void main(String[] args) {

        Main.main(args);
        DataHandlerCSV.writeCSV();

    }

    public static void writeCSV(){
        List <String[]> finalDataID735 = toStringList(new ID_List_CSV("ID735"));
        List <String[]> finalDataID742 = toStringList(new ID_List_CSV("ID742"));

        try {
            CSVWriter writer735 = new CSVWriter(new FileWriter(path735));
            writer735.writeAll(finalDataID735);

            CSVWriter writer742 = new CSVWriter(new FileWriter(path742));
            writer742.writeAll(finalDataID742);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> toStringList(ID_List_CSV id){
        List<String[]> stringList=new ArrayList<>();

        String[] header={"ts","value"};
        stringList.add(header);


        for (int i = 0; i < id.size(); i++) {
            stringList.add(id.toStringArray(i));
        }

        return stringList;
    }
}