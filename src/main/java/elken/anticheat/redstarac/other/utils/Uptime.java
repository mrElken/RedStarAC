package elken.anticheat.redstarac.other.utils;

import static elken.anticheat.redstarac.RedStarAC.uptime;

public class Uptime {
    public static long diff = System.currentTimeMillis() - uptime;

    {
        int days = (int) (diff / 86400000L);
        int hours = (int) (diff / 3600000L % 24L);
        int minutes = (int) (diff / 60000L % 60L);
        int seconds = (int) (diff / 1000L % 60L);

        if (minutes == 0) {
            String UptimeCount = seconds + " sec";
        } else if (hours == 0) {
            String UptimeCount = minutes + " min " + seconds + " sec";
        } else if (days == 0) {
            String UptimeCount = hours + " h " + minutes + " min " + seconds + " sec";
        } else {
            String UptimeCount = days + " d " + hours + " h " + minutes + " min " + seconds + " sec";
        }
    }
}
