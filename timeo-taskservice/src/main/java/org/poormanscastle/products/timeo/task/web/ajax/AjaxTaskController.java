package org.poormanscastle.products.timeo.task.web.ajax;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Activity;
import org.poormanscastle.products.timeo.task.service.TaskService;
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

    @RequestMapping(method = RequestMethod.GET, value = "/registerActivity/{taskId}/{teamMemberId}")
    public @ResponseBody String registerActivity(@PathVariable("taskId") String taskId, @PathVariable("teamMemberId") String teamMemberId) {
        logger.info(StringUtils.join("Received request to create new activity for task ",
                taskId, " and team member ", teamMemberId, "."));
        return taskService.createActivityForTask(taskId, teamMemberId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFinishActivityForm/{activityId}")
    public String getFinishActivityForm(@PathVariable("activityId") String activityId, Model model) {
        logger.info(StringUtils.join("Received request to create finishActivityForm for activityId ", activityId, "."));
        Activity activity = taskService.getActivityForFinishForm(activityId);
        model.addAttribute("activity", activity);
        model.addAttribute("activityId", activityId);
        model.addAttribute("ajaxUtils", new AjaxUtils());
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
            @RequestParam("activityId") String activityId) {
        logger.info(StringUtils.join("Received request to save activity with id ", activityId, "."));
        return "SUCCESS";
    }

}
