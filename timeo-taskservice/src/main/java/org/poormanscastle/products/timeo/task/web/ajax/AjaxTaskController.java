package org.poormanscastle.products.timeo.task.web.ajax;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Activity;
import org.poormanscastle.products.timeo.task.domain.Project;
import org.poormanscastle.products.timeo.task.domain.Status;
import org.poormanscastle.products.timeo.task.domain.Task;
import org.poormanscastle.products.timeo.task.service.ActivityService;
import org.poormanscastle.products.timeo.task.service.ProjectService;
import org.poormanscastle.products.timeo.task.service.TaskService;
import org.poormanscastle.products.timeo.task.service.TaskServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by georg on 07/05/2017.
 */
@Controller
public class AjaxTaskController {

    final static Logger logger = Logger.getLogger(AjaxTaskController.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ProjectService projectService;

    /**
     * On accepting a task in the task browser a new activity is registered with the server. The idea is: if the
     * web session gets interrupted and the user looses the activity information, she can login again and will then be
     * presented with the activity she was working on before everything went south.
     * @param taskId
     * @param teamMemberId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/registerActivity/{taskId}/{teamMemberId}")
    public @ResponseBody String registerActivity(@PathVariable("taskId") String taskId, @PathVariable("teamMemberId") String teamMemberId) {
        logger.info(StringUtils.join("Received request to create new activity for task ",
                taskId, " and team member ", teamMemberId, "."));
        return taskService.createActivityForTask(taskId, teamMemberId);
    }

    /**
     * When working on an activity which was initiated in the task browser, this ws can be
     * used to finish the respective activity.
     * @param activityId
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getFinishActivityForm/{activityId}")
    public String getFinishActivityForm(@PathVariable("activityId") String activityId, Model model) {
        logger.info(StringUtils.join("Received request to create finishActivityForm for activityId ", activityId, "."));
        Activity activity = taskService.getActivityForFinishForm(activityId);
        model.addAttribute("activity", activity);
        model.addAttribute("activityId", activityId);
        model.addAttribute("taskServiceUtils", new TaskServiceUtils());
        model.addAttribute("applicableStatusList", taskService.getApplicableStatuslistForTask(activity.getTask()));
        return "ajax/FinishActivityForm";
    }

    /**
     * Call this method to return the form data given by the user on finishing an activity.
     * All information is expected to be contained in the body of the corresponding POST request
     *
     * @return a status message SUCCESS or ERROR
     */
    @RequestMapping(method = RequestMethod.POST, value = "/finishActivity")
    public @ResponseBody String finishActivity(
            @RequestParam("activityId") String activityId,
            @RequestParam("timeInvested") String timeInvested,
            @RequestParam("startDateTime") String startDateTime,
            @RequestParam("endDateTime") String endDateTime,
            @RequestParam("status") String status,
            @RequestParam("comment") String comment) {
        logger.info(StringUtils.join("Received WS request to save activity with following data: ",
                "activityId: ", activityId, "; timeInvested: ", timeInvested,
                "; startDateTime: ", startDateTime, "; endDateTime: ", endDateTime,
                "; status: ", status, "; comment: ", comment, " - Done!"
        ));
        
        return activityService.processAndStoreActivity(activityId, timeInvested,
                startDateTime, endDateTime, status, comment);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/createNewActivity")
    public @ResponseBody String createNewActivity(
            @RequestParam("timeInvested") String timeInvested,
            @RequestParam("startDateTime") String startDateTime,
            @RequestParam("endDateTime") String endDateTime,
            @RequestParam("comment") String comment,
            @RequestParam("task") String taskId){
        logger.info(StringUtils.join("Received WS request to create and store new activity for following data: ",
                "timeInvested: ", timeInvested, "; startDateTime: ", startDateTime, "; endDateTime: ",
                endDateTime, "; comment: ", comment, "; task: ", taskId, "."));
        return activityService.createAndStoreActivity(taskId, timeInvested,
                startDateTime, endDateTime, comment);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/createNewActivityForm/{masterKey}")
    public String createnewActivityForm(@PathVariable("masterKey") String masterKey, Model model){
        model.addAttribute("projectListForUser", projectService.getProjectsForUser(masterKey));
        model.addAttribute("statusList", Status.findAllStatuses());
        return "ajax/CreateNewActivityForm";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/getTasksForProjectAndUser/{projectId}/{masterKey}")
    public String getTasksForProjectAndUser(@PathVariable("masterKey") String masterKey,
                                            @PathVariable("projectId") String projectId,
                                            Model model){
        List<Task> taskList = taskService.getTasksForProjectAndUser(projectId, masterKey);
        model.addAttribute("tasks", taskList);
        return "ajax/TasklistAsSelectOptions";
    }

}
