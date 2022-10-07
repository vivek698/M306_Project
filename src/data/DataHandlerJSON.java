package data;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ID_List_JSON;
import model.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * data handler for reading the User
 */
public class DataHandlerJSON {
    private static ArrayList messwerteList= new ArrayList();
    private static final String path = "data/messwerte.json";


    /**
     * writes the gameList to the JSON-file
     */
    public static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        try {
            fileOutputStream = new FileOutputStream(path);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            ID_List_JSON id_list1 = new ID_List_JSON("ID742");
            ID_List_JSON[] id_lists={new ID_List_JSON("ID735"), new ID_List_JSON("ID742")};
            objectWriter.writeValue(fileWriter,id_lists);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main.main(args);
        writeJSON();
    }
}