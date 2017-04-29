package org.poormanscastle.products.timeo.task.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Goal;
import org.poormanscastle.products.timeo.task.domain.Priority;
import org.poormanscastle.products.timeo.task.domain.Project;
import org.poormanscastle.products.timeo.task.domain.ProjectTeamMember;
import org.poormanscastle.products.timeo.task.domain.Resource;
import org.poormanscastle.products.timeo.task.domain.Status;
import org.poormanscastle.products.timeo.task.domain.Task;
import org.poormanscastle.products.timeo.task.service.ProjectTeamMemberService;
import org.poormanscastle.products.timeo.task.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tasks")
@Controller
@RooWebScaffold(path = "tasks", formBackingObject = Task.class)
public class TaskController {

    final static Logger logger = Logger.getLogger(TaskController.class);

    @Autowired
    private ProjectTeamMemberService projectTeamMemberService;

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        logger.info(StringUtils.join("Received request for Task-Create-Form"));
        populateEditForm(uiModel, new Task());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (Goal.countGoals() == 0) {
            dependencies.add(new String[]{"goal", "goals"});
        }
        if (Status.countStatuses() == 0) {
            dependencies.add(new String[]{"status", "statuses"});
        }
        if (Priority.countPrioritys() == 0) {
            dependencies.add(new String[]{"priority", "prioritys"});
        }
        if (Project.countProjects() == 0) {
            dependencies.add(new String[]{"project", "projects"});
        }
        if (ProjectTeamMember.countProjectTeamMembers() == 0) {
            dependencies.add(new String[]{"projectTeamMember", "projectteammembers"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "tasks/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") String id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        Task task = Task.findTask(id);
        Resource resource = resourceService.loadResourceByMasterKey(task.getProjectTeamMember().getResourceId());
        task.getProjectTeamMember().setLabel(resourceService.loadResourceByMasterKey(task.getProjectTeamMember().getResourceId()).getEmail());
        uiModel.addAttribute("task", task);
        uiModel.addAttribute("itemId", id);
        uiModel.addAttribute("resource", resource);
        return "tasks/show";
    }

    void populateEditForm(Model uiModel, Task task) {
        uiModel.addAttribute("task", task);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("goals", Goal.findAllGoals());
        uiModel.addAttribute("prioritys", Priority.findAllPrioritys());
        uiModel.addAttribute("projects", Project.findAllProjects());
        uiModel.addAttribute("projectteammembers", projectTeamMemberService.getAllProjectTeamMembers("project", "asc"));
        uiModel.addAttribute("statuses", Status.findAllStatuses());
    }

}
