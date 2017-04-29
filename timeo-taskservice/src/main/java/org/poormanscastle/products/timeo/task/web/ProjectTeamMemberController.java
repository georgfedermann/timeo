package org.poormanscastle.products.timeo.task.web;

import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Project;
import org.poormanscastle.products.timeo.task.domain.ProjectTeamMember;
import org.poormanscastle.products.timeo.task.domain.TimeoRole;
import org.poormanscastle.products.timeo.task.service.ProjectTeamMemberService;
import org.poormanscastle.products.timeo.task.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/projectteammembers")
@Controller
@RooWebScaffold(path = "projectteammembers", formBackingObject = ProjectTeamMember.class)
public class ProjectTeamMemberController {

    public final static Logger logger = Logger.getLogger(ProjectTeamMemberController.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ProjectTeamMemberService projectTeamMemberService;

    void populateEditForm(Model uiModel, ProjectTeamMember projectTeamMember) {
        uiModel.addAttribute("projectTeamMember", projectTeamMember);
        uiModel.addAttribute("projects", Project.findAllProjects());
        uiModel.addAttribute("timeoroles", TimeoRole.findAllTimeoRoles());
        uiModel.addAttribute("resources", resourceService.loadAllResources());
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        logger.info("Received request to list all project team members.");
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("projectteammembers", projectTeamMemberService.getProjectTeamMembers(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) ProjectTeamMember.countProjectTeamMembers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("projectteammembers", projectTeamMemberService.getAllProjectTeamMembers(sortFieldName, sortOrder));
        }
        return "projectteammembers/list";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") String id, Model uiModel) {
        ProjectTeamMember projectTeamMember = ProjectTeamMember.findProjectTeamMember(id);
        uiModel.addAttribute("projectteammember", projectTeamMember);
        uiModel.addAttribute("itemId", id);
        uiModel.addAttribute("resource", resourceService.loadResourceByMasterKey(projectTeamMember.getResourceId()));
        return "projectteammembers/show";
    }

}
