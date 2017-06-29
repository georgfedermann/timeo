package org.poormanscastle.products.timeo.task.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.poormanscastle.products.timeo.task.domain.Activity;
import org.poormanscastle.products.timeo.task.domain.ActivityStatus;
import org.poormanscastle.products.timeo.task.domain.Resource;
import org.poormanscastle.products.timeo.task.domain.Status;
import org.poormanscastle.products.timeo.task.domain.Task;
import org.poormanscastle.products.timeo.task.exception.InvalidDateStringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by georg on 11/05/2017.
 */
@Service
public class ActivityServiceBean implements ActivityService {

    final static Logger logger = Logger.getLogger(ActivityServiceBean.class);

    @Autowired
    private ResourceService resourceService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String createAndStoreActivity(String taskId, String timeInvested, String startDateTimeString, String endDateTimeString, String newTaskStatusId, String comment) {
        if (StringUtils.isBlank(comment)) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    "comment is empty. Please provide a meaningful information here for your team lead.");
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

        int timeInvestedInSeconds = TaskServiceUtils.parseDurationString(timeInvested);
        if (timeInvestedInSeconds <= 0) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    "the given duration string was evaluated to 0. Please check again.");
        }

        Task task = Task.findTask(taskId);
        if (task == null) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    " sorry, the data repository is corrupted. No task is registered for the current activity. Please assemble all available information and contact your team lead and/or system administrator.");
        }
        task.setStatus(Status.findStatus(newTaskStatusId));

        Activity activity = new Activity();
        activity.setStartDateTime(startDateTime);
        activity.setEndDateTime(endDateTime);
        activity.setTimeInvested(timeInvestedInSeconds);
        activity.setComment(comment);
        activity.setActivityStatus(ActivityStatus.DONE);
        activity.setTask(task);
        activity.setProjectTeamMember(task.getProjectTeamMember());
        activity.setActivityStatus(ActivityStatus.DONE);
        activity.persist();

        return ActivityServiceStatusMessage.SUCCESS.getMessage();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String processAndStoreActivity(String activityId, String timeInvestedInSecondsString, String startDateTimeString,
                                          String endDateTimeString, String newTaskId, String newTaskStatusId, String comment) {
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
        
        Task task = Task.findTask(newTaskId);
        if (task == null){
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    " no task for the given taskId ", newTaskId, " exists in persistence. Where did you get that id from??");
        }

        activity.setStartDateTime(startDateTime);
        activity.setEndDateTime(endDateTime);
        activity.setTimeInvested(timeInvestedInSeconds);
        activity.setComment(comment);
        activity.setActivityStatus(ActivityStatus.DONE);

        if (activity.getTask() == null) {
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    " sorry, the data repository appears to be corrupted. No task is registered for the current activity. Please assemble all available information and contact your team lead and/or system administrator.");
        }
        if (activity.getProjectTeamMember() == null){
            return StringUtils.join(ActivityServiceStatusMessage.FAILURE.getMessage(),
                    " sorry, the data repository appears to be corrupted. No resource is registered for the current activity. Please assemble all available information and contact your team lead and/or system administrator.");
        }
        task.setStatus(Status.findStatus(newTaskStatusId));
        if (!StringUtils.equals(activity.getProjectTeamMember().getId(), activity.getTask().getProjectTeamMember().getId())){
            activity.setProjectTeamMember(activity.getTask().getProjectTeamMember());
        }
        
        activity.setTask(task);

        return ActivityServiceStatusMessage.SUCCESS.getMessage();
    }

    @Override
    public List<Activity> getActivities(int firstResult, int sizeNo, String sortFieldName, String sortOrder) {
        logger.info(StringUtils.join("Got a service request for ", sizeNo, " data sets of projects, starting with row ",
                firstResult, ", sorted by ", sortFieldName, " ", sortOrder, "."));
        List<Activity> activities = Activity.findActivityEntries(firstResult, sizeNo, sortFieldName, sortOrder);
        activities.forEach(activity -> setEmailForProjectTeamMemberOnActivity(activity));
        return activities;
    }

    void setEmailForProjectTeamMemberOnActivity(Activity activity) {
        Resource resource = resourceService.loadResourceByMasterKey(activity.getProjectTeamMember().getResourceId());
        if (resource != null) {
            activity.getProjectTeamMember().setLabel(resource.getEmail());
        } else {
            activity.getProjectTeamMember().setLabel(StringUtils.join("No email found for ", activity.getProjectTeamMember().getResourceId(), "."));
        }
    }

    @Override
    public List<Activity> getAllActivities(String sortFieldName, String sortOrder) {
        logger.info(StringUtils.join("Got a service request for all projects sorted by ", sortFieldName, " ", sortOrder, "."));
        List<Activity> activities = Activity.findAllActivitys(sortFieldName, sortOrder);
        activities.forEach(activity -> setEmailForProjectTeamMemberOnActivity(activity));
        return activities;
    }

    @Override
    public List<Activity> getActivitiesForStakeholderAndDay(String masterKey, DateTime day) {
        checkArgument(!StringUtils.isBlank(masterKey), "MasterKey is required!");
        checkNotNull(day, "Day is required!");
        EntityManager em = Activity.entityManager();
        TypedQuery<Activity> query = em.createQuery(
                "SELECT a FROM Activity AS a INNER JOIN a.projectTeamMember AS ptm WHERE date(a.startDateTime) = date(:myDate) and ptm.resourceId = :masterKey", Activity.class);
        query.setParameter("myDate", day.toDate());
        query.setParameter("masterKey", masterKey);
        List<Activity> result = query.getResultList();
        result.forEach(a -> a.setStartTimeSecondsFromMidnight(
                TaskServiceUtils.getSecondsFromMidnight(a.getStartDateTime())));
        return result;
    }

    @Override
    public List<List<Activity>> getActivitiesForStakeholderAndCalendarWeek(String masterKey, int year, int calendarWeek) {
        List<List<Activity>> workWeek = new LinkedList<>();
        List<DateTime> calendarWeekDays = TaskServiceUtils.getCalendarWeek(year, calendarWeek);
        calendarWeekDays.forEach(day -> workWeek.add(getActivitiesForStakeholderAndDay(masterKey, day)));
        return workWeek;
    }
}
