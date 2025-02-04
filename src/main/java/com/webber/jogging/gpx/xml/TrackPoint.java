package com.webber.jogging.gpx.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class TrackPoint {
    @XmlAttribute
    private String lat;

    @XmlAttribute
    private String lon;

    @XmlElement
    private double ele;

    @XmlElement
    private String time;

    public TrackPoint(String lat, String lon, double ele, String time, Extensions extensions) {
        this.lat = lat;
        this.lon = lon;
        this.ele = ele;
        this.time = time;
        this.extensions = extensions;
    }

    public TrackPoint() {
    }

    @XmlElement(name = "extensions")
    private Extensions extensions = new Extensions();
}