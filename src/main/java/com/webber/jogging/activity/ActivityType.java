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
            case "Bike" -> BIKE;
            case "Swim" -> SWIM;
            case "Hike" -> HIKE;
            default -> OTHER;
        };
    }

}
