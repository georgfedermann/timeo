package org.poormanscastle.products.timeo.task.service;

import java.util.List;

import org.poormanscastle.products.timeo.task.domain.Activity;
import org.poormanscastle.products.timeo.task.domain.Resource;
import org.poormanscastle.products.timeo.task.domain.Status;
import org.poormanscastle.products.timeo.task.domain.Task;

/**
 * Created by georg on 04/05/2017.
 */
public interface TaskService {

    List<Task> getTasksForResource(Resource resource);

    List<Task> getTasksForResource(String resourceId);

    /**
     * creates a new activity instance for the given task and persists
     * it to the repository. Also, returns the id of the newly created
     * activiy.
     *
     * @param taskId the task to book the current activity on
     * @param projectTeamMemberId If a task can be assigned to multiple resources, it is
     *                            necessary to distinguish who is actually booking an
     *                            activity on this task.
     * @return the unique ID of the newly created activity
     */
    String createActivityForTask(String taskId, String projectTeamMemberId);

    /**
     * gets the activity identified by activityId from the repository and
     * sets the end date for the activity. timeInvested will be set on client side
     * with the value tracked by the user agent.
     * @param activityId must be a valid activityId as listed in the repository
     * @return the activity identified by the activityId. If no such activity can be found
     * {@code null} will be returned.
     */
    Activity getActivityForFinishForm(String activityId);

    /**
     * based on the current status of a given task this service returns
     * a list of new status values the task can assume starting from its
     * current status. This service should evaluate a set of business rules
     * implementing a status state engine for the given system.
     * @param task a task whose fate shall be decided here
     * @return a list of applicable new status values for this task
     */
    List<Status> getApplicableStatuslistForTask(Task task);

    /**
     * probably mostlry used by the Spring MVC TaskController, this service returns a list of all Task entries
     * as specified by the method parameters.
     * @param firstResult
     * @param sizeNo
     * @param sortFieldName
     * @param sortOrder
     * @return
     */
    List<Task> getTasks(int firstResult, int sizeNo, String sortFieldName, String sortOrder);

    /**
     * probably mostly used by the Spring MVC TaskController, this service returns a list of all Task entries
     * in the repository sorted as specified by the method parameters.
     * @param sortFieldName
     * @param sortOrder
     * @return
     */
    List<Task> getAllTasks(String sortFieldName, String sortOrder);
    
}
