package org.poormanscastle.products.timeo.task.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.exception.InvalidDateStringException;

/**
 * Implements logic that can be used in the WebServices, UIs, JSPs, etc.
 * Created by georg on 10/05/2017.
 */
public class TaskServiceUtils {

    final static Logger logger = Logger.getLogger(TaskServiceUtils.class);

    final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date) {
        return TaskServiceUtils.dateFormat.format(date);
    }

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
