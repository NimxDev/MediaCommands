package me.nimodev.mediaCommands.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Duration {


    private static final ThreadLocal<StringBuilder> BUILDER = ThreadLocal.withInitial(StringBuilder::new);

    public static long of(String input) {
        int duration = 0;

        Matcher matcher = Pattern.compile("\\d+\\D+").matcher(input);

        while (matcher.find()) {
            String[] group = matcher.group().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

            String type = group[1];
            long value = Long.parseLong(group[0]);

            switch (type) {
                case "s":
                    duration += value;
                    break;
                case "m":
                    duration += value * 60;
                    break;
                case "h":
                    duration += value * 3600;
                    break;
                case "d":
                    duration += value * 86400;
                    break;
            }
        }

        if (duration == 0) return -1;
        else return duration * 1000L;
    }

    public static String toRemaining(long input) {
        float value = input / 1000F;

        if (value > 60.0F) {
            int option = (int) value;

            int s = option % 60;

            option -= s;

            int first = option / 60;
            int m = first % 60;

            first -= m;

            int second = first / 60;
            int h = second % 24;

            second -= h;

            int d = second / 24;

            StringBuilder result = BUILDER.get();
            result.setLength(0);

            if (d > 0) {
                if (d < 10) {
                    result.append("0");
                }

                result.append(d);
                result.append(":");

                if (h > 0) {
                    if (h < 10) {
                        result.append("0");
                    }

                    result.append(h);
                } else {
                    result.append("0");
                    result.append("0");
                }

                result.append(":");
            } else {
                if (h > 0) {
                    if (h < 10) {
                        result.append("0");
                    }

                    result.append(h);
                    result.append(":");
                }
            }

            if (m < 10L) {
                result.append("0");
            }

            result.append(m);
            result.append(":");

            if (s < 10) {
                result.append("0");
            }

            result.append(s);

            return result.toString();
        } else {
            return Math.round(10.0 * value) / 10.0 + "s";
        }
    }
    public static String formatDurationLong(long input) {
        return DurationFormatUtils.formatDurationWords(input, true, true);
    }
}
