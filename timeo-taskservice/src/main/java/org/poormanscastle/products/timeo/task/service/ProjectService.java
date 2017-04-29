package org.poormanscastle.products.timeo.task.service;

import java.util.List;

import org.poormanscastle.products.timeo.task.domain.Project;

/**
 * Created by georg on 27/03/2017.
 */
public interface ProjectService {

    List<Project> getProjects(int firstResult, int sizeNo, String sortFieldName, String sortOrder);

    List<Project> getAllProjects(String sortFieldName, String sortOrder);

}
