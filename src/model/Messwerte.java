package model;


//import jdk.nashorn.internal.objects.annotations.Property;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement()
public class Messwerte {

    private String timestamp; //yyyy-MM-dd-hh-mm
    private double relativeEinspeisung;
    private double absoluteEinspeisung;
    private double relativerBezug;
    private double absoluterBezug;


    public Messwerte() {

    }

    public Messwerte(String timestamp, double relativeEinspeisung, double absoluteEinspeisung, double relativerBezug, double absoluterBezug) {
        this.timestamp = timestamp;
        this.relativeEinspeisung = relativeEinspeisung;
        this.absoluteEinspeisung = absoluteEinspeisung;
        this.relativerBezug = relativerBezug;
        this.absoluterBezug = absoluterBezug;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getRelativeEinspeisung() {
        return relativeEinspeisung;
    }

    public void setRelativeEinspeisung(double relativeEinspeisung) {
        this.relativeEinspeisung = relativeEinspeisung;
    }

    public double getAbsoluteEinspeisung() {
        return absoluteEinspeisung;
    }

    public void setAbsoluteEinspeisung(double absoluteEinspeisung) {
        this.absoluteEinspeisung = absoluteEinspeisung;
    }

    public double getRelativerBezug() {
        return relativerBezug;
    }

    public void setRelativerBezug(double relativerBezug) {
        this.relativerBezug = relativerBezug;
    }

    public double getAbsoluterBezug() {
        return absoluterBezug;
    }

    public void setAbsoluterBezug(double absoluterBezug) {
        this.absoluterBezug = absoluterBezug;
    }

    public String[] toStringArray() {
        String[] stringArray={
                this.getTimestamp(),
                String.valueOf(this.getRelativeEinspeisung()),
                String.valueOf(this.getAbsoluteEinspeisung()),
                String.valueOf(this.getRelativerBezug()),
                String.valueOf(this.getAbsoluterBezug())
        };

        return stringArray;
    }
}
