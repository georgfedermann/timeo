package org.poormanscastle.products.timeo.task.service;

import java.util.List;

import org.poormanscastle.products.timeo.task.domain.Resource;

/**
 * Created by georg on 20/02/2017.
 */
public interface ResourceService {

    /**
     * Creates a new resource and fills its fields with data retrieved from the stakeholder service for
     * the given masterKey. If masterKey is empty, this service returns null.
     * @param masterKey
     * @return a Resource instance or null if the masterKey is empty.
     */
    Resource loadResourceByMasterKey(String masterKey);

    List<Resource> loadAllResources();

}
