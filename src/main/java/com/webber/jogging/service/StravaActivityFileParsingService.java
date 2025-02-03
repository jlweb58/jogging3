package com.webber.jogging.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webber.jogging.domain.ActivityData;
import com.webber.jogging.domain.ActivityDataArray;
import com.webber.jogging.domain.GpxTrackElement;
import com.webber.jogging.domain.ParsedGpxTrack;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class StravaActivityFileParsingService {

    private final ObjectMapper objectMapper;

    public StravaActivityFileParsingService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public ParsedGpxTrack parseActivityFile(String jsonContent, Instant activityStartTime) throws IOException {
        ActivityData activityData = parseToActivityData(jsonContent, activityStartTime);

        return convertToGpxTrack(activityData);
    }


    private ActivityData parseToActivityData(String jsonContent, Instant startTime) throws IOException {
        // Previous parsing code remains the same
        List<ActivityDataArray> rawData = objectMapper.readValue(
                jsonContent,
                new TypeReference<List<ActivityDataArray>>() {}
        );

        // Initialize containers for each data type
        List<double[]> coordinates = null;
        List<Double> distances = new ArrayList<>();
        List<Double> altitudes = new ArrayList<>();
        List<Integer> heartRates = new ArrayList<>();
        List<Integer> elapsedSeconds = new ArrayList<>();

        // Process each data array based on its type
        for (ActivityDataArray array : rawData) {
            switch (array.getType()) {
                case "latlng":
                    coordinates = objectMapper.convertValue(
                            array.getData(),
                            new TypeReference<List<double[]>>() {}
                    );
                    break;
                case "distance":
                    List<Number> distanceData = objectMapper.convertValue(
                            array.getData(),
                            new TypeReference<List<Number>>() {}
                    );
                    distanceData.forEach(d -> distances.add(d.doubleValue()));
                    break;
                case "altitude":
                    List<Number> altitudeData = objectMapper.convertValue(
                            array.getData(),
                            new TypeReference<List<Number>>() {}
                    );
                    altitudeData.forEach(a -> altitudes.add(a.doubleValue()));
                    break;
                case "heartrate":
                    List<Integer> heartRateData = objectMapper.convertValue(
                            array.getData(),
                            new TypeReference<List<Integer>>() {}
                    );
                    heartRates.addAll(heartRateData);
                    break;
                case "time":
                    List<Integer> timeData = objectMapper.convertValue(
                            array.getData(),
                            new TypeReference<List<Integer>>() {}
                    );
                    elapsedSeconds.addAll(timeData);
                    break;
            }
        }

        ActivityData request = new ActivityData();
        request.setCoordinates(coordinates);
        request.setDistances(distances);
        request.setAltitudes(altitudes);
        request.setHeartRates(heartRates);
        request.setElapsedSeconds(elapsedSeconds);
        request.setStartTime(startTime);

        return request;
    }

    private ParsedGpxTrack convertToGpxTrack(ActivityData request) {
        List<GpxTrackElement> trackElements = IntStream.range(0, request.getCoordinates().size())
                .mapToObj(i -> new GpxTrackElement(
                        request.getCoordinates().get(i)[0],  // latitude
                        request.getCoordinates().get(i)[1],  // longitude
                        request.getAltitudes().get(i),       // elevation
                        request.getHeartRates().get(i),      // heart rate
                        request.getStartTime().plusSeconds(request.getElapsedSeconds().get(i)) // timestamp
                ))
                .toList();

        ParsedGpxTrack parsedGpxTrack = new ParsedGpxTrack();
        parsedGpxTrack.setTrackElements(new ArrayList<>(trackElements));
        return parsedGpxTrack;
    }

}
