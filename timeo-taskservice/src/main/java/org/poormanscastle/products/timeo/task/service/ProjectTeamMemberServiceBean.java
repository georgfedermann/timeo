package org.poormanscastle.products.timeo.task.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Project;
import org.poormanscastle.products.timeo.task.domain.ProjectTeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by georg on 26/02/2017.
 */
@Service
public class ProjectTeamMemberServiceBean implements ProjectTeamMemberService {

    final static Logger logger = Logger.getLogger(ProjectTeamMemberServiceBean.class);

    @Autowired
    private ResourceService resourceService;

    @Override
    public List<ProjectTeamMember> getProjectTeamMembersForProject(Project project) {
        logger.info(StringUtils.join("Got a service request for the team members of the project ", project.toString(), "."));
        List<ProjectTeamMember> projectTeamMembers = ProjectTeamMember.findProjectTeamMembersByProject(project).getResultList();
        projectTeamMembers.forEach((t) -> {
            t.setLabel(resourceService.loadResourceByMasterKey(t.getResourceId()).getEmail());
        });
        return projectTeamMembers;
    }

    @Override
    public List<ProjectTeamMember> getProjectTeamMembersForProject(String projectId) {
        logger.info(StringUtils.join("Got a service request for the team members of the project with projectId ", projectId, "."));
        return getProjectTeamMembersForProject(Project.findProject(projectId));
    }

    @Override
    public List<ProjectTeamMember> getProjectTeamMembers(int firstResult, int sizeNo, String sortFieldName, String sortOrder) {
        logger.info(StringUtils.join("Got a service request for ", sizeNo, " data sets of team members, starting with row ",
                firstResult, ", sorted by ", sortFieldName, " ", sortOrder, "."));
        List<ProjectTeamMember> projectTeamMembers = ProjectTeamMember.findProjectTeamMemberEntries(firstResult, sizeNo, sortFieldName, sortOrder);
        projectTeamMembers.forEach((t) -> {
            t.setLabel(resourceService.loadResourceByMasterKey(t.getResourceId()).getEmail());
        });
        return projectTeamMembers;
    }

    @Override
    public List<ProjectTeamMember> getAllProjectTeamMembers(String sortFieldName, String sortOrder) {
        logger.info(StringUtils.join("Got a service request for all project team members sorted by ", sortFieldName, " ", sortOrder, "."));
        List<ProjectTeamMember> teamMembers = ProjectTeamMember.findAllProjectTeamMembers(sortFieldName, sortOrder);
        teamMembers.forEach((t) -> {
            t.setLabel(resourceService.loadResourceByMasterKey(t.getResourceId()).getEmail());
        });
        return teamMembers;
    }
}
