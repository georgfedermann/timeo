package org.poormanscastle.products.timeo.task.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.exception.InvalidDateStringException;

/**
 * Implements logic that can be used in the WebServices, UIs, JSPs, etc.
 * Created by georg on 10/05/2017.
 */
public class TaskServiceUtils {

    final static Logger logger = Logger.getLogger(TaskServiceUtils.class);

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
}
