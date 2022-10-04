package data;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Messwerte;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

/**
 * data handler for reading the User
 */
public class DataHandlerJSON {
    private static ArrayList messwerteList= new ArrayList();
    private static final String path = "data/messwerte.json";
    /**
     * reads the game from the JSON-file
     */
    public static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            path
                    )
            );

            ObjectMapper objectMapper = new ObjectMapper();
            Messwerte[] messwerte = objectMapper.readValue(jsonData, Messwerte[].class);
            Collections.addAll(messwerteList, messwerte);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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
            objectWriter.writeValue(fileWriter, messwerteList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}