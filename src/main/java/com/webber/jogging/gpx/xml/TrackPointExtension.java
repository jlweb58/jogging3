package com.webber.jogging.gpx.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class TrackPointExtension {

    @XmlElement(namespace = "http://www.garmin.com/xmlschemas/TrackPointExtension/v1")
    private int hr;
}