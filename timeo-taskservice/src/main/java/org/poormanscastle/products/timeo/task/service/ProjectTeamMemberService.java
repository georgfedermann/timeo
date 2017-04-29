package org.poormanscastle.products.timeo.task.service;

import java.util.List;

import org.poormanscastle.products.timeo.task.domain.Project;
import org.poormanscastle.products.timeo.task.domain.ProjectTeamMember;

/**
 * Created by georg on 26/02/2017.
 */
public interface ProjectTeamMemberService {

    List<ProjectTeamMember> getProjectTeamMembersForProject(Project project);

    List<ProjectTeamMember> getProjectTeamMembersForProject(String projectId);

    /**
     * get a subset of the persisted project team members, used for pagination in the team member list.
     *
     * @param firstResult   the row number of the first data set to use
     * @param sizeNo        the number of rows to be displayed
     * @param sortFieldName the column name by which the table shall be sorted
     * @param sortOrder     the sort order (asc or desc)
     * @return the corresponding result set
     */
    List<ProjectTeamMember> getProjectTeamMembers(int firstResult, int sizeNo, String sortFieldName, String sortOrder);

    List<ProjectTeamMember> getAllProjectTeamMembers(String sortFieldName, String sortOrder);

}
