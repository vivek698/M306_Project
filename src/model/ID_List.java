package model;

import data.DataHandler;

import java.util.List;
import java.util.TreeMap;

public class ID_List {
    List<Long> timestampList = DataHandler.getTimeStampList();
    TreeMap<Long, Messwerte> messwerteTreeMap  = DataHandler.getMap();
    String sensorId;
    TreeMap<Long, Double> data = new TreeMap<>();

    public ID_List(String sensorId) {
        this.sensorId = sensorId;


        if (sensorId.equals("ID735")){
            for (Long timestamp:timestampList) {
                data.put(timestamp, messwerteTreeMap.get(timestamp).getRelativeEinspeisung());
            }

        } else if (sensorId.equals("ID742")){
            for (Long timestamp:timestampList) {
                data.put(timestamp, messwerteTreeMap.get(timestamp).getRelativerBezug());
            }
        }

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
