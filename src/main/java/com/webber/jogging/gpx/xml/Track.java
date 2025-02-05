package com.webber.jogging.gpx.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Track {
    @XmlElement
    private String name;

    @XmlElement
    private String type;

    @XmlElement(name = "trkseg")
    private TrackSegment segment;

}