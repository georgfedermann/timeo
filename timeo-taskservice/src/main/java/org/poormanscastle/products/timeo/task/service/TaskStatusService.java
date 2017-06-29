package org.poormanscastle.products.timeo.task.service;

import java.util.List;

import org.poormanscastle.products.timeo.task.domain.Status;

/**
 * Created by georg on 28/06/2017.
 */
public interface TaskStatusService {

    List<Status> getAllTaskStatusOrderedByName();

}
