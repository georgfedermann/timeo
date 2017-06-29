package org.poormanscastle.products.timeo.task.service;

import java.util.List;

import org.poormanscastle.products.timeo.task.domain.Status;
import org.springframework.stereotype.Service;

/**
 * Created by georg on 28/06/2017.
 */
@Service
public class TaskStatusServiceBean implements TaskStatusService {

    @Override
    public List<Status> getAllTaskStatusOrderedByName() {
        return Status.findAllStatuses("name", "asc");
    }

}
