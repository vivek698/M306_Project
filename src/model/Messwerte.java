package model;


import jdk.nashorn.internal.objects.annotations.Property;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement()
public class Messwerte {

    private String timestamp;
    private double relativeEinspeisung;
    private double absoluteEinspeisung;
    private double relativerBezug;
    private double absoluterBezug;


    public Messwerte() {

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
}
