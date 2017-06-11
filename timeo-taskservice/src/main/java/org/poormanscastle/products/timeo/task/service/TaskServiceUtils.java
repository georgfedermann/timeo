package org.poormanscastle.products.timeo.task.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.poormanscastle.products.timeo.task.exception.InvalidDateStringException;

/**
 * Implements logic that can be used in the WebServices, UIs, JSPs, etc.
 * Created by georg on 10/05/2017.
 */
public class TaskServiceUtils {

    final static Logger logger = Logger.getLogger(TaskServiceUtils.class);
    final static boolean debug = logger.isDebugEnabled();
    final static boolean info = logger.isInfoEnabled();

    /**
     * Dates in the task service are formatted using this pattern:
     * yyyy-MM-dd hh:mm:ss. Hours are given in 24 hours format. Months are numbered from
     * 1 (January) through 12 (December). No time zone is given ... grand!
     * TODO enable time zones!
     */
    final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * durations - e.g. in effort estimations - are given by using a string like 4d meaning 4 days,
     * or 20h, meaning 20 hours. If you should choose to combine different scales always proceed from
     * the large scale to the lower, as in 1d 4h, not 4h 1d.
     */
    private final static Pattern durationParsePattern = Pattern.compile("(\\d+)d\\s+(\\d+)h\\s+(\\d+)m\\s+(\\d+)s");

    private final static Pattern grepDaysPattern = Pattern.compile("(\\d+)d");
    private final static Pattern grepHoursPattern = Pattern.compile("(\\d+)h");
    private final static Pattern grepMinutesPattern = Pattern.compile("(\\d+)m");
    private final static Pattern grepSecondsPattern = Pattern.compile("(\\d+)s");

    public static int parseDurationString(String durationString) {
        // TODO implement some logic to reject invalid duration strings
        int numberOfSeconds = 0;
        // extract days
        Matcher matcher = grepDaysPattern.matcher(durationString);
        while (matcher.find()) {
            numberOfSeconds += 8 * 60 * 60 * Integer.parseInt(matcher.group(1));
        }
        // extract hours
        matcher = grepHoursPattern.matcher(durationString);
        while (matcher.find()) {
            numberOfSeconds += 60 * 60 * Integer.parseInt(matcher.group(1));
        }
        // extract minutes
        matcher = grepMinutesPattern.matcher(durationString);
        while (matcher.find()) {
            numberOfSeconds += 60 * Integer.parseInt(matcher.group(1));
        }
        // extract seconds
        matcher = grepSecondsPattern.matcher(durationString);
        while (matcher.find()) {
            numberOfSeconds += Integer.parseInt(matcher.group(1));
        }

        return numberOfSeconds;
    }

    public static String createDurationStringFromSeconds(long secondsInput) {
        checkArgument(secondsInput >= 0, StringUtils.join("time invested must be greater or equal 0 but was ",
                secondsInput, "."));

        if (secondsInput == 0) {
            return "0s";
        }

        StringBuilder builder = new StringBuilder();

        long days = secondsInput / (8 * 3600);
        long remainder = secondsInput % (8 * 3600);
        long hours = remainder / 3600;
        remainder = remainder % 3600;
        long minutes = remainder / 60;
        long seconds = remainder % 60;

        builder.append(days == 0 ? "" : days + "d")
                .append(hours == 0 ? "" : hours + "h")
                .append(minutes == 0 ? "" : minutes + "m")
                .append(seconds == 0 ? "" : seconds + "s");

        return builder.toString();
    }

    public static String formatDate(Date date) {
        return TaskServiceUtils.dateFormat.format(date);
    }

    /**
     * Dates in the task service are formatted using this pattern:
     * yyyy-MM-dd hh:mm:ss. Hours are given in 24 hours format. Months are numbered from
     * 1 (January) through 12 (December). No time zone is given ... grand!
     * TODO enable time zones!
     *
     * @param dateString
     * @return
     */
    public static Date parseDate(String dateString) {
        try {
            return TaskServiceUtils.dateFormat.parse(dateString);
        } catch (ParseException exception) {
            String errMsg = StringUtils.join("Could not parse date ", dateString,
                    ", because: ", exception.getMessage());
            logger.error(errMsg, exception);
            throw new InvalidDateStringException(errMsg, exception);
        }
    }

    /**
     * delivers a list of 7 days Monday through Sunday for the given year and calendar week.
     *
     * @param year
     * @param calendarWeek
     * @return
     */
    public static List<DateTime> getCalendarWeek(int year, int calendarWeek) {
        List<DateTime> result = new ArrayList<>();
        DateTime dateTime = new DateTime().withYear(year).withWeekOfWeekyear(calendarWeek).withDayOfWeek(DateTimeConstants.MONDAY);
        result.add(dateTime);
        for (int c = 1; c <= 6; c++) {
            result.add(dateTime.plusDays(c));
        }

        return result;
    }

    public static int getSecondsFromMidnight(Date startDateTime) {
        int result = new DateTime(startDateTime).getSecondOfDay();
        if (logger.isDebugEnabled()) {
            logger.debug("Seconds from midnight=" + result + " for startDateTime " +
                    new DateTime(startDateTime).toString("HH:mm:ss"));
        }
        return result;
    }
}
