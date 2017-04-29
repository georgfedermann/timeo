package org.poormanscastle.products.timeo.task.web;

import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Project;
import org.poormanscastle.products.timeo.task.domain.ProjectStatus;
import org.poormanscastle.products.timeo.task.domain.Resource;
import org.poormanscastle.products.timeo.task.service.ProjectService;
import org.poormanscastle.products.timeo.task.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/projects")
@Controller
@RooWebScaffold(path = "projects", formBackingObject = Project.class)
public class ProjectController {

    final static Logger logger = Logger.getLogger(ProjectController.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ProjectService projectService;

    void populateEditForm(Model uiModel, Project project) {
        uiModel.addAttribute("project", project);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("projectstatuses", ProjectStatus.findAllProjectStatuses());
        uiModel.addAttribute("resources", resourceService.loadAllResources());
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("projects", projectService.getProjects(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) Project.countProjects() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("projects", projectService.getAllProjects(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "projects/list";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") String id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        Project project = Project.findProject(id);
        uiModel.addAttribute("project", project);
        uiModel.addAttribute("itemId", id);
        Resource spoc = resourceService.loadResourceByMasterKey(project.getSpocMasterKey());
        uiModel.addAttribute("resource", spoc == null ? new Resource() : spoc);
        return "projects/show";
    }

}
