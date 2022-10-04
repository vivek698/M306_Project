package model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ID_List {
    List<Long> timestampList = new ArrayList<>();//TODO get from DataHandler
    TreeMap<Long, Messwerte> messwerteTreeMap  = new TreeMap<>(); //TODO get from DataHandler
    String id;
    TreeMap<Long, Double> data = new TreeMap<>();

    public ID_List(String id) {
        this.id = id;

        timestampList.add(1000L);
        timestampList.add(2000L);
        timestampList.add(3000L);

        messwerteTreeMap.put(timestampList.get(0), new Messwerte("23.22.2021",100,20,102,30));
        messwerteTreeMap.put(timestampList.get(1), new Messwerte("23.22.2021",100,20,102,30));
        messwerteTreeMap.put(timestampList.get(2), new Messwerte("23.22.2021",100,20,102,30));

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
        ID_List id_list=new ID_List("ID735");
        System.out.println(id_list.data);
    }
}
