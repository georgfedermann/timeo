package org.poormanscastle.products.timeo.task.web.ajax;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.service.TaskService;
import org.poormanscastle.products.timeo.task.service.TaskServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by georg on 04/05/2017.
 */
@Controller
public class AjaxUserController {

    final static Logger logger = Logger.getLogger(AjaxUserController.class);

    @Autowired
    private TaskService taskService;

    /**
     * userId corresponds to the masterKey of a stakeholder in the stakeholder service.
     *
     * @param stakeholderId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tasksForUser/widget/{stakeholderId}")
    public String getTasksForUser(@PathVariable String stakeholderId, Model model) {
        logger.info(StringUtils.join(
                "Received a REST-ful WS request for the task list for the user identified by userId ", stakeholderId, "."));
        model.addAttribute("tasks", taskService.getTasksForResource(stakeholderId));
        model.addAttribute("Id", stakeholderId);
        model.addAttribute("taskUtils", new TaskServiceUtils());
        return "ajax/TasklistForUser";
    }

}
