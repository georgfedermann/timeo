package org.poormanscastle.products.timeo.task.web.ajax;

import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by georg on 28/06/2017.
 */
@Controller
public class AjaxTaskStatusController {
    
    final static Logger logger = Logger.getLogger(AjaxTaskStatusController.class);
    
    @Autowired
    private TaskStatusService taskStatusService;
    
    @RequestMapping(method = RequestMethod.GET, value="/getAllTaskStatusValues")
    public String getAllTaskStatusValues(Model model){
        model.addAttribute("taskStatusValues", taskStatusService.getAllTaskStatusOrderedByName());
        return "ajax/TaskStatusList";
    }
    
}
