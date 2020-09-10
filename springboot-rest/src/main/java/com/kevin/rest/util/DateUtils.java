package com.kevin.rest.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date Utils
 */
public class DateUtils {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * LocalDateTime -> 2020-07-21T16:15:00.000Z
     *
     * @param time time
     * @return String
     */
    public static String getUtCTime(LocalDateTime time) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(time, ZoneOffset.UTC);
        return zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    /**
     * 2020-07-21T16:15:00.000Z -> LocalDateTime
     *
     * @param time time
     * @return LocalDateTime
     */
    public static LocalDateTime getTimeByUtc(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    /**
     * LocalDateTime-> long
     *
     * @param time time
     * @return long
     */
    public static long getEpochMilliByTime(LocalDateTime time) {
        return time.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    /**
     * long-> LocalDateTime
     *
     * @param time time
     * @return LocalDateTime
     */
    public static LocalDateTime getEpochMilliByTime(long time) {
        return LocalDateTime.ofEpochSecond(time / 1000, 0, ZoneOffset.UTC);
    }

    /**
     * LocalDateTime.now()->2020-07-21T16:15:00
     *
     * @return String
     */
    public static String getUtcTimeBySecond() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime dateTime = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * yyyy-MM-dd HH:mm:ss -> LocalDateTime
     *
     * @return LocalDateTime
     */
    public static LocalDateTime getStringByLocalDateTime(String time) {
        return LocalDateTime.parse(time, TIME_FORMATTER);
    }

    /**
     * 将unix时间戳转换为字符串格式的时间
     *
     * @param milli unix时间戳
     * @return string time as:yyyy-MM-dd HH:mm:ss
     */
    public static String getTimeByEpochMilli(long milli) {
        LocalDateTime dateTime = getEpochMilliByTime(milli);
        return TIME_FORMATTER.format(dateTime);
    }

    /**
     * yyyy-MM-dd HH:mm -> LocalDateTime
     *
     * @return LocalDateTime
     */
    public static LocalDateTime getStringByLocalDateTime(LocalDateTime time) {
        return LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(),
                time.getHour(), time.getMinute());
    }

    /**
     * 时间string类型转为是时间戳
     *
     * @param timestamp 时间string类型
     * @return 时间戳
     */
    public static Long getEpochMillIByTime(String timestamp) {
        LocalDateTime localDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return getEpochMilliByTime(localDateTime);
    }
}
