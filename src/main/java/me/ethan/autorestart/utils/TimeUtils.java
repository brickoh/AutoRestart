package me.ethan.autorestart.utils;

import me.ethan.autorestart.AutoRestart;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    /*
    To have the entire Runnable and this in sync we're going to add 1 to our minutes,
    So when we do /ar it'll give us the correct time.
     */
    public static long getTime() {
        FileConfiguration config = AutoRestart.getInstance().getConfig();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, config.getInt("restart.hours"));
        today.set(Calendar.MINUTE, config.getInt("restart.minutes") +1);
        today.set(Calendar.SECOND, config.getInt("restart.seconds"));
        today.set(Calendar.MILLISECOND, 0);
        if (config.getBoolean("restart.am")) {
            today.set(Calendar.AM_PM, Calendar.AM);
        } else {
            today.set(Calendar.AM_PM, Calendar.PM);
        }

        long currentTime = new Date().getTime();

        if (today.getTime().getTime() < currentTime) {
            today.add(Calendar.DATE, 1);
        }
        return today.getTime().getTime() - currentTime;
    }

    public static String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);

        if (hours == 1) {
            sb.append(hours);
            sb.append(" hour ");
        } else if (hours != 0) {
            sb.append(hours);
            sb.append(" hours ");
        }

        if (minutes == 1) {
            sb.append(minutes);
            sb.append(" minute ");
        } else {
            if (minutes != 0) {
                sb.append(minutes);
                sb.append(" minutes ");
            }
        }

        sb.append(seconds);
        sb.append(" seconds");
        return (sb.toString());
    }

    public static String getTimeLeft() {
        return getDurationBreakdown(getTime());
    }

}
