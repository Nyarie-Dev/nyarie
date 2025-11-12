package eu.nyarie.core.util;

import java.time.Duration;

/// Provides utility methods for working with
/// the [java.time.Duration] class.
public class DurationUtils {

    /// Returns a String of the duration in the following format:
    ///
    /// ```
    /// 12h 4m 28s 312ms
    /// ```
    ///
    /// A time unit is omitted if it is `0` and all the larger time units
    /// are `0` as well. <br>
    /// For example, if we use the above example, just with `0` hours and minutes,
    /// the result would be:
    ///
    /// ```
    /// 28s 312ms
    /// ```
    ///
    /// If the hours are set to `1` now, the result is as follows:
    ///
    /// ```
    /// 1h 0m 28s 312ms
    /// ```
    ///
    /// The milliseconds are only included if they are >0 or
    /// if the `duration` is <1s (even is the ms are `0`).<br>
    /// Examples:
    ///
    /// ms = `0`:
    /// ```
    /// 1h 0m 28s
    /// ```
    ///
    /// entire duration = `0`:
    /// ```
    /// 0ms
    /// ```
    public static String toReadableString(Duration duration) {
        if(duration == null)
            return "";

        long millis = Math.abs(duration.toMillis());

        long hours = millis / (3600 * 1000);
        long minutes = (millis % (3600 * 1000)) / (60 * 1000);
        long seconds = (millis % (60 * 1000)) / 1000;
        long milliseconds = millis % 1000;

        StringBuilder sb = new StringBuilder();

        // Handle the negative sign if the original duration was negative
        if (duration.isNegative() && !duration.equals(Duration.ZERO)) {
            sb.append("-");
        }

        // Append hours, minutes, seconds, and milliseconds only if they are present or required

        // 1. Hours (always include if > 0)
        if (hours > 0) {
            sb.append(hours).append("h ");
        }

        // 2. Minutes (always include if > 0 OR if hours were included)
        if (minutes > 0 || hours > 0) {
            sb.append(minutes).append("m ");
        }

        // 3. Seconds
        // Include if > 0 OR if hours/minutes were included
        if (seconds > 0 || minutes > 0 || hours > 0) {
            sb.append(seconds).append("s ");
        }

        // 4. Milliseconds (always include if > 0 OR if the entire duration is less than 1 second)
        if (milliseconds > 0 || (hours == 0 && minutes == 0 && seconds == 0)) {
            sb.append(milliseconds).append("ms");
        }

        // Clean up any trailing space and return
        return sb.toString().trim();
    }
}
