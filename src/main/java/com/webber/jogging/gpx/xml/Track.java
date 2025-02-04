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

    public Track(String name, String type, TrackSegment segment) {
        this.name = name;
        this.type = type;
        this.segment = segment;
    }

    public Track() {
    }
}