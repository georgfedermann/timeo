package org.poormanscastle.products.timeo.task.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Activity;
import org.poormanscastle.products.timeo.task.domain.ActivityStatus;
import org.poormanscastle.products.timeo.task.domain.ProjectTeamMember;
import org.poormanscastle.products.timeo.task.domain.Resource;
import org.poormanscastle.products.timeo.task.domain.Task;
import org.springframework.stereotype.Service;

/**
 * Created by georg on 04/05/2017.
 */
@Service
public class TaskServiceBean implements TaskService {

    final static Logger logger = Logger.getLogger(TaskService.class);
    final static boolean info = TaskServiceBean.logger.isInfoEnabled();
    final static boolean debug = TaskServiceBean.logger.isDebugEnabled();

    @Override
    public String createActivityForTask(String taskId, String projectTeamMemberId) {
        Activity activity = new Activity();

        checkArgument(!StringUtils.isBlank(taskId), "A valid taskId must be stated.");
        checkArgument(!StringUtils.isBlank(projectTeamMemberId), "A valid team member id must be stated.");

        Task task = Task.findTask(taskId);
        ProjectTeamMember projectTeamMember = ProjectTeamMember.findProjectTeamMember(projectTeamMemberId);

        // check that the registered / planned team member is booking activities on this task
        // and not somebody else
        if (!projectTeamMemberId.equals(task.getProjectTeamMember().getId())) {
            String errMsg = StringUtils.join("ERROR: Only the projectTeamMember with id ",
                    task.getProjectTeamMember().getId(),
                    " can book activities on the task ", task.getId(), ":", task.getName(),
                    ", but the given teamMemberId was ", projectTeamMemberId,
                    ". This incident is logged on the server!");
            logger.error(errMsg);
            return errMsg;
        }
        
        // TODO check whether the teamMember's resource is active in some other activity at the time?
        
        activity.setActivityStatus(ActivityStatus.ONGOING);
        activity.setStartDateTime(new Date());
        activity.setProjectTeamMember(projectTeamMember);
        activity.setTask(task);

        activity.persist();
        return activity.getId();
    }

    /**
     * Resource should better be filled with the information exposed by corresponding
     * web services within the stakeholder service. This service will make use of the
     * masterKey field which corresponds to the masterKey of stakeholders in the
     * stakeholder service.
     *
     * @param resource
     * @return
     */
    @Override
    public List<Task> getTasksForResource(Resource resource) {
        logger.info(StringUtils.join("Got a service request for the tasks for the resource ",
                resource == null ? "null" : resource.toString(), "."));
        return resource == null ? null : getTasksForResource(resource.getMasterKey());
    }

    /**
     * resourceId corresponds to the masterKey of a stakeholder in the stakeholder service.
     *
     * @param resourceId
     * @return
     */
    @Override
    public List<Task> getTasksForResource(String resourceId) {
        logger.info(StringUtils.join("Got a service request for the tasks for the resource with resourceId ",
                resourceId, "."));
        List<ProjectTeamMember> projectTeamMemberList = ProjectTeamMember.
                findProjectTeamMembersByResourceIdEquals(resourceId).getResultList();
        List<Task> taskList = new ArrayList<>();
        for (ProjectTeamMember projectTeamMember : projectTeamMemberList) {
            taskList.addAll(Task.findTasksByProjectTeamMember(projectTeamMember).getResultList());
        }
        return taskList;
    }

}
