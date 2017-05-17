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

    /**
     * probably mostly used by the Spring MVC ActivityController, this service returns a subset of the Activity entries
     * in the repository as specified by the method parameters.
     * @param firstResult
     * @param sizeNo
     * @param sortFieldName
     * @param sortOrder
     * @return
     */
    List<Activity> getActivities(int firstResult, int sizeNo, String sortFieldName, String sortOrder);

    /**
     * probably mostly used by the Spring MVC ActivityController, this services returns a list of all Activity
     * entries in the repository sorted as specified by the method parameters.
     * @param sortFieldName
     * @param sortOrder
     * @return
     */
    List<Activity> getAllActivities(String sortFieldName, String sortOrder);

}
