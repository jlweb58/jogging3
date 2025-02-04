package com.webber.jogging.gpx.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class TrackSegment {
    @XmlElement(name = "trkpt")
    private List<TrackPoint> trackPoints;

    public TrackSegment(List<TrackPoint> trackPoints) {
        this.trackPoints = trackPoints;
    }

    public TrackSegment() {
    }
}