package org.poormanscastle.products.timeo.webfrontend.service;

import java.util.List;

import org.poormanscastle.products.timeo.webfrontend.domain.Resource;

/**
 * exposes services to retrieve resources, which are beans holding user information merged with stakeholder
 * information, mapped via the respective masterKey values.
 * 
 * Created by georg on 20/03/2017.
 */
public interface ResourceService {

    /**
     * loads all available information for the user identified by masterKey and
     * merges them into a Resource object.
     * @param masterKey
     * @return
     */
    Resource loadResourceByMasterKey(String masterKey);

    /**
     * merges data retrieved from the user storage with data retrieved from the stakeholder service
     * and returns a Resource object holding all this information.
     * @param loginId
     * @return
     */
    Resource loadResourceByLoginId(String loginId);

    List<Resource> loadAllResources();

}
