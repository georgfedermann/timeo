package org.poormanscastle.products.timeo.task.web.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Project;
import org.poormanscastle.products.timeo.task.domain.Task;
import org.poormanscastle.products.timeo.task.service.ProjectService;
import org.poormanscastle.products.timeo.task.service.TaskService;
import org.poormanscastle.products.timeo.task.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * handles requests for the project browser.
 * Created by georg on 07/08/2017.
 */
@Controller
public class AjaxProjectBrowserController {

    final static Logger logger = Logger.getLogger(AjaxProjectBrowserController.class);

    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET, value = "/getProjectBrowserForUser/{masterKey}")
    public String getProjectBrowserForUser(@PathVariable("masterKey") final String masterKey, final Model model) {
        List<Project> projects = projectService.getProjectsForUser(masterKey);
        Map<Project, List<Task>> projectToTaskMap = new HashMap<>();
        projects.forEach(project -> {
            projectToTaskMap.put(project, taskService.getTasksForProjectAndUser(project.getId(), masterKey));
        });

        model.addAttribute("projects", projects);
        model.addAttribute("projectToTaskMap", projectToTaskMap);
        model.addAttribute("webUtils", new WebUtils());

        return "ajax/projectBrowser/projectBrowserForUserView";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getProjectBrowserForProjectLead/{masterKey}")
    public String getProjectBrowserForProjectLead(@PathVariable("masterKey") final String masterKey, final Model model) {

        return "ajax/projectBrowser/projectBrowserForProjectLeadView";
    }

}
