package org.poormanscastle.products.timeo.task.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Activity;
import org.poormanscastle.products.timeo.task.domain.ActivityStatus;
import org.poormanscastle.products.timeo.task.domain.ProjectTeamMember;
import org.poormanscastle.products.timeo.task.domain.Resource;
import org.poormanscastle.products.timeo.task.domain.Status;
import org.poormanscastle.products.timeo.task.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by georg on 04/05/2017.
 */
@Service
public class TaskServiceBean implements TaskService {

    final static Logger logger = Logger.getLogger(TaskService.class);
    final static boolean info = TaskServiceBean.logger.isInfoEnabled();
    final static boolean debug = TaskServiceBean.logger.isDebugEnabled();

    @Autowired
    private ResourceService resourceService;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        logger.info("setDataSource is being called.");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String createActivityForTask(String taskId, String projectTeamMemberId) {
        logger.info(StringUtils.join("Registered request for create activity for taskId ", taskId,
                " and projectTeamMemberId ", projectTeamMemberId));
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

    @Override
    public Activity getActivityForFinishForm(String activityId) {
        Activity activity = null;
        if (!StringUtils.isBlank(activityId)) {
            activity = Activity.findActivity(activityId);
        }
        if (activity == null) {
            logger.error(StringUtils.join("For activityId '", activityId, "' no activity was found in repository."));
            return null;
        }
        if (activity.getEndDateTime() == null) {
            activity.setEndDateTime(new Date());
        }
        return activity;
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
    public List<Task> getTasksForResource(Resource resource, String[] applicableTaskStatus) {
        logger.info(StringUtils.join("Got a service request for the tasks for the resource ",
                resource == null ? "null" : resource.toString(), "."));
        return resource == null ? null : getTasksForResource(resource.getMasterKey(), applicableTaskStatus);
    }

    /**
     * resourceId corresponds to the masterKey of a stakeholder in the stakeholder service.
     *
     * @param resourceId
     * @return
     */
    @Override
    public List<Task> getTasksForResource(String resourceId, String[] applicableTaskStatus) {
        logger.info(StringUtils.join("Got a service request for the tasks for the resource with resourceId ",
                resourceId, "."));
        // TODO implement the below using HQL instead of complex and imperformant Java logic
        List<ProjectTeamMember> projectTeamMemberList = ProjectTeamMember.
                findProjectTeamMembersByResourceIdEquals(resourceId).getResultList();
        List<Task> taskList = new ArrayList<>();
        for (ProjectTeamMember projectTeamMember : projectTeamMemberList) {
            taskList.addAll(Task.findTasksByProjectTeamMember(projectTeamMember).getResultList());
        }
        taskList.forEach(task -> task.setEffortMeasured(getTimeInvestedInTask(task)));

        if (applicableTaskStatus != null) {
            List<String> applicableTaskStatusList = Arrays.asList(applicableTaskStatus);
            if (!applicableTaskStatusList.isEmpty()) {
                taskList = taskList.stream().filter(o -> applicableTaskStatusList.contains(o.getStatus().getId())).collect(Collectors.toList());
            }
        }

        taskList.sort((o1, o2) -> {
            // sort tasks by priority, with the highest priority at the beginning
            return o2.getPriority().getRanking() - o1.getPriority().getRanking();
        });
        return taskList;
    }

    @Override
    public List<Status> getApplicableStatuslistForTask(Task task) {
        // TODO obviously a rule engine needs to be implemented here
        List<Status> statusList = Status.findAllStatuses();
        Collections.sort(statusList, (status1, status2) -> status1.getName().compareTo(status2.getName()));
        return statusList;
    }

    @Override
    public List<Task> getTasks(int firstResult, int sizeNo, String sortFieldName, String sortOrder) {
        logger.info(StringUtils.join("Got a service request for ", sizeNo, " data sets of tasks, starting with row ",
                firstResult, ", sorted by ", sortFieldName, " ", sortOrder, "."));
        List<Task> tasks = Task.findTaskEntries(firstResult, sizeNo, sortFieldName, sortOrder);
        tasks.forEach(task -> setEmailForProjectTeamMemberOnTask(task));
        return tasks;
    }

    void setEmailForProjectTeamMemberOnTask(Task task) {
        Resource resource = resourceService.loadResourceByMasterKey(task.getProjectTeamMember().getResourceId());
        if (resource != null) {
            task.getProjectTeamMember().setLabel(resource.getEmail());
            task.setEffortMeasured(getTimeInvestedInTask(task));
        } else {
            task.getProjectTeamMember().setLabel(StringUtils.join("No email found for ",
                    task.getProjectTeamMember().getResourceId(), "."));
        }
    }

    @Override
    public List<Task> getAllTasks(String sortFieldName, String sortOrder) {
        logger.info(StringUtils.join("Got a service request for all tasks sorted by ", sortFieldName, " ", sortOrder, "."));
        List<Task> tasks = Task.findAllTasks(sortFieldName, sortOrder);
        tasks.forEach(task -> setEmailForProjectTeamMemberOnTask(task));
        return tasks;
    }

    @Override
    public long getTimeInvestedInTask(Task task) {
        if (jdbcTemplate == null) {
            logger.error("JdbcTemplate has not been initialized!");
        }
        Long result = jdbcTemplate.queryForObject(
                "select sum(a.time_invested) as efforts from activity as a inner join task as t on a.task = t.id where t.id = ?",
                Long.class, task.getId());
        return result == null ? 0 : result;
    }

    @Override
    public List<Task> getTasksForProjectAndUser(String projectId, String masterKey) {
        checkArgument(!StringUtils.isBlank(projectId), "projectId is required.");
        checkArgument(!StringUtils.isBlank(masterKey), "masterKey is required.");
        // TODO: use this instead of list filter below:
        // select p.name, t.name, ptm.resource_id from project as p inner join task as t on t.project = p.id inner join project_team_member as ptm on t.project_team_member = ptm.id where ptm.resource_id = 149;
        EntityManager em = Task.entityManager();
        TypedQuery<Task> query = em.createQuery(
                "SELECT t FROM Task AS t INNER JOIN t.project AS p WHERE p.id = :projectId", Task.class
        );
        query.setParameter("projectId", projectId);
        List<Task> resultList = query.getResultList();
        return resultList.stream().filter(t ->
                masterKey.equals(t.getProjectTeamMember().getResourceId())).collect(Collectors.toList());
    }

}
