package org.poormanscastle.products.timeo.webfrontend.service;

import java.util.List;

import org.poormanscastle.products.timeo.webfrontend.domain.Resource;

/**
 * Created by georg on 20/03/2017.
 */
public interface ResourceService {

    Resource loadResourceByMasterKey(String masterKey);

    List<Resource> loadAllResources();

}
