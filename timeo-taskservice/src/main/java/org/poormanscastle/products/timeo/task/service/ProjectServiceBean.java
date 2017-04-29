package org.poormanscastle.products.timeo.task.service;

import java.util.List;

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
        projects.forEach((p) -> {
            Resource resource = resourceService.loadResourceByMasterKey(p.getSpocMasterKey());
            if (resource != null) {
                p.setLabel(resource.getEmail());
            }
        });
        return projects;
    }

    @Override
    public List<Project> getAllProjects(String sortFieldName, String sortOrder) {
        logger.info(StringUtils.join("Got a service request for all projects sorted by ", sortFieldName, " ", sortOrder, "."));
        List<Project> projects = Project.findAllProjects(sortFieldName, sortOrder);
        projects.forEach((p) -> {
            Resource resource = resourceService.loadResourceByMasterKey(p.getSpocMasterKey());
            if (resource != null) {
                p.setLabel(resource.getEmail());
            }
        });
        return projects;
    }
}
