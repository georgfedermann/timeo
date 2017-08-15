package org.poormanscastle.products.timeo.task.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Project;
import org.poormanscastle.products.timeo.task.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by georg on 27/03/2017.
 */
@Service
public class ProjectServiceBean implements ProjectService {

    final static Logger logger = Logger.getLogger(Project.class);

    @Autowired
    private ResourceService resourceService;

    @Override
    public List<Project> getProjects(int firstResult, int sizeNo, String sortFieldName, String sortOrder) {
        logger.info(StringUtils.join("Got a service request for ", sizeNo, " data sets of projects, starting with row ",
                firstResult, ", sorted by ", sortFieldName, " ", sortOrder, "."));
        List<Project> projects = Project.findProjectEntries(firstResult, sizeNo, sortFieldName, sortOrder);
        projects.forEach(project -> setSpocLabelOnProject(project));
        return projects;
    }

    void setSpocLabelOnProject(Project project) {
        Resource resource = resourceService.loadResourceByMasterKey(project.getSpocMasterKey());
        if (resource != null) {
            project.setLabel(resource.getEmail());
        } else {
            project.setLabel(StringUtils.join("No email found for SPOC id ", project.getSpocMasterKey(), "."));
        }
    }

    @Override
    public List<Project> getAllProjects(String sortFieldName, String sortOrder) {
        logger.info(StringUtils.join("Got a service request for all projects sorted by ", sortFieldName, " ", sortOrder, "."));
        List<Project> projects = Project.findAllProjects(sortFieldName, sortOrder);
        projects.forEach(project -> setSpocLabelOnProject(project));
        return projects;
    }

    @Override
    public List<Project> getProjectsForUser(String masterKey) {
        checkArgument(!StringUtils.isBlank(masterKey), "MasterKey is required!");
        EntityManager em = Project.entityManager();
        TypedQuery<Project> query = em.createQuery(
                "SELECT p FROM Project AS p INNER JOIN p.projectTeamMembers AS ptm WHERE ptm.resourceId = :masterKey",
                Project.class);
        query.setParameter("masterKey", masterKey);
        List<Project> projects = query.getResultList();
        projects.forEach(project -> setSpocLabelOnProject(project));

        return projects;
    }

}
