package com.dan.bot.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Duration {
    public static Instant durationLeft(String s) {
        if (s.endsWith("d")) {
            try {
                return Instant.now().plus(Integer.parseInt(s.replace("d", "")), ChronoUnit.DAYS);
            } catch (NumberFormatException exception) {
                return null;
            }
        } else {
            if (s.endsWith("h")) {
                try {
                    return Instant.now().plus(Integer.parseInt(s.replace("h", "")), ChronoUnit.HOURS);
                } catch (NumberFormatException exception) {
                    return null;
                }
            } else {
                if (s.endsWith("m")) {
                    try {
                        return Instant.now().plus(Integer.parseInt(s.replace("m", "")), ChronoUnit.MINUTES);
                    } catch (NumberFormatException exception) {
                        return null;
                    }
                } else {
                    if (s.endsWith("s")) {
                        try {
                            return Instant.now().plusSeconds(Integer.parseInt(s.replace("s", "")));
                        } catch (NumberFormatException exception) {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }
            }
        }
    }
    public static String toMaxTime(long milliseconds) {
        if(milliseconds > 86400000) {
            long days;
            long hours;
            long minutes;
            long seconds;

            days = milliseconds / 86400000;

            hours = (milliseconds - (days*86400000)) / 3600000;

            minutes = (milliseconds - (hours*3600000) - (days*86400000)) / 60000;

            seconds = (milliseconds - (minutes*60000) - (hours*3600000) - (days*86400000)) / 1000;

            return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        }
        if(milliseconds > 3600000) {
            long hours;
            long minutes;
            long seconds;

            hours = milliseconds / 3600000;

            minutes = (milliseconds - (hours*3600000)) / 60000;

            seconds = (milliseconds - (minutes*60000) - (hours*3600000)) / 1000;

            return hours + "h " + minutes + "m " + seconds + "s";
        }
        if(milliseconds > 60000) {
            long minutes;
            long seconds;

            minutes = milliseconds / 60000;

            seconds = (milliseconds - (minutes*60000)) / 1000;

            return minutes + "m " + seconds + "s";
        }
        if(milliseconds > 1000) {
            long seconds;

            seconds = milliseconds / 1000;

            return seconds + "s";
        }
        return milliseconds + "ms";
    }
}
