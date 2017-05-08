package org.poormanscastle.products.timeo.task.web.ajax;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public
    @ResponseBody
    String registerActivity(@PathVariable("taskId") String taskId, @PathVariable("teamMemberId") String teamMemberId) {
        logger.info(StringUtils.join("Received request to create new activity for task ",
                taskId, " and team member ", teamMemberId, "."));
        return taskService.createActivityForTask(taskId, teamMemberId);
    }

}
