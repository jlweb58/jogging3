package com.webber.jogging.activity;

public enum ActivityType {
    RUN,
    BIKE,
    SWIM,
    HIKE,
    OTHER;

    public static ActivityType fromStravaTypeString(String stravaTypeString) {
        return switch (stravaTypeString) {
            case "Run" -> RUN;
            case "Ride" -> BIKE;
            case "Swim" -> SWIM;
            case "Hike" -> HIKE;
            default -> OTHER;
        };
    }

}
