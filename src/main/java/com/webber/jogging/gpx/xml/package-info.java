@XmlSchema(
        namespace = "http://www.topografix.com/GPX/1/1",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix = "gpxtpx", namespaceURI = "http://www.garmin.com/xmlschemas/TrackPointExtension/v1"),
                @XmlNs(prefix = "gpxx", namespaceURI = "http://www.garmin.com/xmlschemas/GpxExtensions/v3")
        }
)
package com.webber.jogging.gpx.xml;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;