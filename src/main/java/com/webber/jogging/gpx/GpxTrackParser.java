package com.webber.jogging.gpx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class GpxTrackParser {


    private static final String TRKPT = "trkpt";
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String ELE = "ele";
    private static final String TIME = "time";
    private static final String EXTENSIONS = "extensions";
    private static final String TRACK_POINT_EXTENSION = "gpxtpx:TrackPointExtension";
    private static final String HR = "gpxtpx:hr";

    public ParsedGpxTrack parseGpxTrack(InputStream gpxTrackStream) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(gpxTrackStream);
        Element gpx = document.getDocumentElement();
        NodeList trkPtList = gpx.getElementsByTagName(TRKPT);
        ParsedGpxTrack gpxTrack = new ParsedGpxTrack();
        List<GpxTrackElement> childTracks = getGpxTrackElementList(trkPtList);
        gpxTrack.setTrackElements(childTracks);
        return gpxTrack;
    }

    private List<GpxTrackElement> getGpxTrackElementList(NodeList trkPtList) {
        List<GpxTrackElement> trackElementList = new ArrayList<>();
        for (int i = 0; i < trkPtList.getLength(); i++) {
            Element trkPt = (Element)trkPtList.item(i);
            GpxTrackElement gpxTrackElement = getGpxTrackElement(trkPt);
            trackElementList.add(gpxTrackElement);
        }
        return trackElementList;
    }

    private GpxTrackElement getGpxTrackElement(Element trkPt) {
        double latitude = Double.parseDouble(trkPt.getAttribute(LAT));
        double longitude = Double.parseDouble(trkPt.getAttribute(LON));
        Element elevationElement = (Element) trkPt.getElementsByTagName(ELE).item(0);
        double elevation = Double.parseDouble(elevationElement.getTextContent());
        Element timestampElement = (Element) trkPt.getElementsByTagName(TIME).item(0);
        Instant timestamp = (Instant.parse(timestampElement.getTextContent()));
        int heartRate = getHeartRate((Element) trkPt.getElementsByTagName(EXTENSIONS).item(0));
        return new GpxTrackElement(latitude, longitude, elevation, heartRate, timestamp);
    }

    private int getHeartRate(Element extensions) {
        Element trackPointExtension = (Element) extensions.getElementsByTagName(TRACK_POINT_EXTENSION).item(0);
        Element heartRateElement = (Element) trackPointExtension.getElementsByTagName(HR).item(0);
        return Integer.parseInt(heartRateElement.getTextContent());
    }
}
