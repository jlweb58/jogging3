package com.webber.jogging.gpx.xml;

import com.webber.jogging.gpx.GpxTrackElement;
import com.webber.jogging.gpx.ParsedGpxTrack;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.stream.Collectors;

@Component
public class GpxConverter {

    public String convertToGpx(ParsedGpxTrack parsedTrack, String activityName) {
        try {
            JAXBContext context = JAXBContext.newInstance(GpxType.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            GpxType gpx = createGpxType(parsedTrack, activityName);

            StringWriter writer = new StringWriter();
            XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
            xmlStreamWriter.setNamespaceContext(new NamespaceContext() {
                @Override
                public Iterator<String> getPrefixes(String namespaceURI) {
                    return null;
                }

                @Override
                public String getPrefix(String namespaceURI) {
                    return "";
                }

                public String getNamespaceURI(String prefix) {
                    return null;
                }
            });
            marshaller.marshal(gpx, xmlStreamWriter);

            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to convert to GPX", e);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private GpxType createGpxType(ParsedGpxTrack parsedTrack, String activityName) {
        GpxType gpx = new GpxType();

        // Set metadata
        Metadata metadata = new Metadata();
        metadata.setTime(parsedTrack.getTrackElements().get(0).timestamp().toString());
        gpx.setMetadata(metadata);

        // Create track
        Track track = new Track();
        track.setName(activityName);

        // Create track segment with points
        TrackSegment segment = new TrackSegment();
        segment.setTrackPoints(parsedTrack.getTrackElements().stream()
                .map(this::convertToTrackPoint)
                .collect(Collectors.toList()));

        track.setSegment(segment);
        gpx.setTrack(track);

        return gpx;
    }

    private TrackPoint convertToTrackPoint(GpxTrackElement element) {
        TrackPoint point = new TrackPoint();
        point.setLat(String.format("%.7f", element.latitude()));
        point.setLon(String.format("%.7f", element.longitude()));
       point.setEle(element.elevation());
        point.setTime(element.timestamp().toString());

        // Set heart rate
        TrackPointExtension tpExt = new TrackPointExtension();
        tpExt.setHr(element.heartRate());

        Extensions extensions = new Extensions();
        extensions.setTrackPointExtension(tpExt);

        point.setExtensions(extensions);

        return point;
    }
}
