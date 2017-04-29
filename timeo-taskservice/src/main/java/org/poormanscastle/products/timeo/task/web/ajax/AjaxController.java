package org.poormanscastle.products.timeo.task.web.ajax;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.ProjectTeamMember;
import org.poormanscastle.products.timeo.task.service.ProjectTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * as long as the project is so small, one single controller to handle all
 * AJAX requests may well suffice.
 * Created by georg on 26/02/2017.
 */
@RequestMapping("/projectTeamMember")
@Controller
public class AjaxController {

    final static Logger logger = Logger.getLogger(AjaxController.class);

    @Autowired
    private ProjectTeamMemberService projectTeamMemberService;

    @RequestMapping(method = RequestMethod.GET, value = "/forProject/{projectId}")
    public
    @ResponseBody
    List<ProjectTeamMember> getPojectTeamMemberForProject(@PathVariable String projectId) {
        logger.info(StringUtils.join("Got a REST-ful WS request for the project team members of project with id ", projectId, "."));
        return projectTeamMemberService.getProjectTeamMembersForProject(projectId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/widget/forProject/{projectId}")
    public String getWidgetPojectTeamMemberForProject(@PathVariable String projectId, Model modelUi) {
        logger.info(StringUtils.join("Got a REST-ful WS request for the project team members of project with id ", projectId, "."));
        modelUi.addAttribute("projectTeamMembers", projectTeamMemberService.getProjectTeamMembersForProject(projectId));
        return "ajax/ProjectTeamMemberWidget";
    }

}
