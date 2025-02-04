package com.webber.jogging.gpx.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "gpx", namespace = "http://www.topografix.com/GPX/1/1")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class GpxType {
    @XmlAttribute
    private String creator = "StravaGPX";

    @XmlAttribute(name = "version")
    private String version = "1.1";

    @XmlElement(name = "metadata")
    Metadata metadata;

    @XmlElement(name = "trk")
    Track track;

    public GpxType() {}
}

