package com.webber.jogging.activity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Embeddable
public class ActivityDuration implements Serializable {
    private static final long serialVersionUID = -3364267670308865924L;

    @Column(name = "time", nullable = false)
    private Time time;

    private transient int hours;

    private transient int minutes;

    private transient int seconds;

    private transient String stringRep;

    /**
     * Create a new ActivityDuration object, checking validity of the supplied arguments
     *
     * @param hours   The number of hours the activity took (the assumption is made that no
     *                activity will take more than 5 hours).
     * @param minutes The number of minutes.
     * @param seconds The number of seconds.
     * @throws IllegalArgumentException if any argument is invalid, i.e. 61 minutes.
     */
    public ActivityDuration(int hours, int minutes, int seconds) {
        rangeCheck(hours, 5, "hours");
        rangeCheck(minutes, 59, "minutes");
        rangeCheck(seconds, 59, "seconds");
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        Calendar calendar = new GregorianCalendar(1970, Calendar.JANUARY, 1);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        this.time = new Time(calendar.getTimeInMillis());
        this.stringRep = makeStringRep();
    }

    public ActivityDuration() {
        //For serialization
    }

    public static ActivityDuration fromSeconds(long seconds) {
        return new ActivityDuration((int) (seconds / 3600), (int) ((seconds % 3600) / 60), (int) (seconds % 60));
    }

    private static void rangeCheck(int arg, int max, String name) {
        if (arg < 0 || arg > max) {
            throw new IllegalArgumentException(name + ": " + arg);
        }
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
        this.stringRep = makeStringRep();
    }

    private String makeStringRep() {
        StringBuilder sb = new StringBuilder("0");
        hours = time.getHours();
        minutes = time.getMinutes();
        seconds = time.getSeconds();
        sb.append(hours).append(":");
        if (minutes < 10) {
            sb.append("0");
        }
        sb.append(minutes).append(":");
        if (seconds < 10) {
            sb.append("0");
        }
        sb.append(seconds);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ActivityDuration)) return false;
        ActivityDuration other = (ActivityDuration) obj;
        return hours == other.hours && minutes == other.minutes
                && seconds == other.seconds;
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 31 * result + hours;
        result = 31 * result + minutes;
        result = 31 * result + seconds;
        return result;
    }

    @Override
    public String toString() {
        if (stringRep == null) {
            stringRep = makeStringRep();
        }
        return stringRep;

    }


}
