package com.webber.jogging.gpx.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class Extensions {
    @XmlElement(namespace = "http://www.garmin.com/xmlschemas/TrackPointExtension/v1")
    private TrackPointExtension TrackPointExtension = new TrackPointExtension();

    public Extensions(com.webber.jogging.gpx.xml.TrackPointExtension trackPointExtension) {
        TrackPointExtension = trackPointExtension;
    }

    public Extensions() {
    }
}

