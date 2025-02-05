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

    @XmlElement(name = "extensions")
    private Extensions extensions = new Extensions();
}