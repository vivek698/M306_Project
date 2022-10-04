package model;

import data.DataHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ID_List {
    List<Long> timestampList = DataHandler.getTimeStampList();
    TreeMap<Long, Messwerte> messwerteTreeMap  = DataHandler.getMap();
    String id;
    TreeMap<Long, Double> data = new TreeMap<>();

    public ID_List(String id) {
        this.id = id;


        if (id.equals("ID735")){
            for (Long timestamp:timestampList) {
                data.put(timestamp, messwerteTreeMap.get(timestamp).getRelativeEinspeisung());
            }

        } else if (id.equals("ID742")){
            for (Long timestamp:timestampList) {
                data.put(timestamp, messwerteTreeMap.get(timestamp).getRelativerBezug());
            }
        }

    }

    public static void main(String[] args) {
        DataHandler.main(args);
        ID_List id_list=new ID_List("ID735");
        System.out.println(id_list.data);
    }

    public String[] toStringArray(int i) {
        String[] stringArray={
                String.valueOf(timestampList.get(i)),
                String.valueOf(data.get(timestampList.get(i)))
        };

        return stringArray;
    }

    public int size(){
        return data.size();
    }
}
