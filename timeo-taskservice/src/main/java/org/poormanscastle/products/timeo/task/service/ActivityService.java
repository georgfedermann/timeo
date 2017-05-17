package org.poormanscastle.products.timeo.task.service;

import java.util.List;

import org.poormanscastle.products.timeo.task.domain.Activity;

/**
 * Created by georg on 11/05/2017.
 */
public interface ActivityService {

    /**
     * this method accepts a bunch of information about an activity that's just been
     * performed, validates the input, tries to put the activity info into the
     * repository and returns a status messages which either starts with SUCCES: to
     * indicate a successful process or FAILURE: to indicate that something went south.
     *
     * @param activityId
     * @param timeInvestedInSeconds
     * @param startDateTime
     * @param endDateTime
     * @param newTaskStatus
     * @param comment
     * @return
     */
    String processAndStoreActivity(String activityId, String timeInvestedInSeconds,
                                   String startDateTime, String endDateTime, String newTaskStatus,
                                   String comment);

    List<Activity> getActivities(int firstResult, int sizeNo, String sortFieldName, String sortOrder);

    List<Activity> getAllActivities(String sortFieldName, String sortOrder);

}
