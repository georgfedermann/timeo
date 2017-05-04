package org.poormanscastle.products.timeo.task.service;

import java.util.List;

import org.poormanscastle.products.timeo.task.domain.Resource;
import org.poormanscastle.products.timeo.task.domain.Task;

/**
 * Created by georg on 04/05/2017.
 */
public interface TaskService {
    
    List<Task> getTasksForResource(Resource resource);
    
    List<Task> getTasksForResource(String resourceId);
    
}
