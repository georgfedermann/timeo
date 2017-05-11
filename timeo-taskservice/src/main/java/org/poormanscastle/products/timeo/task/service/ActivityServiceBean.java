package org.poormanscastle.products.timeo.task.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Activity;
import org.poormanscastle.products.timeo.task.domain.ActivityStatus;
import org.poormanscastle.products.timeo.task.domain.Status;
import org.poormanscastle.products.timeo.task.domain.Task;
import org.poormanscastle.products.timeo.task.exception.InvalidDateStringException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by georg on 11/05/2017.
 */
@Service
public class ActivityServiceBean implements ActivityService {

    final static Logger logger = Logger.getLogger(ActivityServiceBean.class);

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String processAndStoreActivity(String activityId, String timeInvestedInSecondsString, String startDateTimeString,
                                          String endDateTimeString, String newTaskStatusId, String comment) {
        logger.info(StringUtils.join("Received service request to save activity with following data: ",
                "activityId: ", activityId, "; timeInvested: ", timeInvestedInSecondsString,
                "; startDateTimeString: ", startDateTimeString, "; endDateTimeString: ", endDateTimeString,
                "; newTaskStatusId: ", newTaskStatusId, "; comment: ", comment, " - Done!"
        ));

        if (StringUtils.isBlank(comment)) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    "comment is empty. Please provide a meaningful information here for your team lead.");
        }
        if (StringUtils.isBlank(activityId)) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    "activityId is empty. Please provide valid activityId!");
        }
        Date startDateTime = null;
        Date endDateTime = null;
        try {
            startDateTime = TaskServiceUtils.parseDate(startDateTimeString);
        } catch (InvalidDateStringException exception) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    "the given start date/time should be of format: yyyy-mm-dd hh:mm:ss. Please adapt.");
        }
        try {
            endDateTime = TaskServiceUtils.parseDate(endDateTimeString);
        } catch (InvalidDateStringException exception) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    "the given end date/time should be of format: yyyy-mm-dd hh:mm:ss. Please adapt.");
        }

        int timeInvestedInSeconds = TaskServiceUtils.parseDurationString(timeInvestedInSecondsString);
        if (timeInvestedInSeconds <= 0) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    "the given duration string was evaluated to 0. Please check again.");
        }

        Activity activity = Activity.findActivity(activityId);
        if (activity == null) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    "the given activityId could not be ressolved to an actual activity in storage. Please adapt. Ask you team lead or system administrator for help.");
        }

        activity.setStartDateTime(startDateTime);
        activity.setEndDateTime(endDateTime);
        activity.setTimeInvested(timeInvestedInSeconds);
        activity.setComment(comment);
        activity.setActivityStatus(ActivityStatus.DONE);

        Task task = activity.getTask();
        if (activity.getTask() == null) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    " sorry, the data repository is corrupted. No task is registered for the current activity. Please assemble all available information and contact your team lead and/or system administrator.");
        }
        activity.getTask().setStatus(Status.findStatus(newTaskStatusId));

        return ActivityServiceStatusMessage.SUCCESS.getMessage();
    }


}
