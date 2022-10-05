package model;

import data.DataHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ID_List_JSON {
    private String sensorId;
    private List<JSON_Format> data = new ArrayList<>();


    public ID_List_JSON(String sensorId) {
        this.sensorId = sensorId;
        TreeMap<Long, Messwerte> map = DataHandler.getMap();
        List<Long> timeStampList = DataHandler.getTimeStampList();

        if(sensorId.equals("ID735")){
            for (int i = 0; i < timeStampList.size(); i++) {
                data.add(new JSON_Format(timeStampList.get(i), map.get(timeStampList.get(i)).getAbsoluteEinspeisung()));
            }
        }else if (sensorId.equals("ID742")){
            for (int i = 0; i < timeStampList.size(); i++) {
                data.add(new JSON_Format(timeStampList.get(i), map.get(timeStampList.get(i)).getAbsoluterBezug()));
            }
        }

    }

    public String getSensorId() {
        return sensorId;
    }

    public List<JSON_Format> getData() {
        return data;
    }

    private class JSON_Format{
        long ts;
        double value;
        public JSON_Format(long ts, double value) {
            this.ts = ts;
            this.value = value;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(long ts) {
            this.ts = ts;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
