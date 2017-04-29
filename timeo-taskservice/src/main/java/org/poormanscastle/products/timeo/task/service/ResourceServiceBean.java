package org.poormanscastle.products.timeo.task.service;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.task.domain.Resource;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by georg on 20/02/2017.
 */
@Service
public class ResourceServiceBean implements ResourceService, InitializingBean {

    final static Logger logger = Logger.getLogger(ResourceServiceBean.class);

    @Value("${webservice.hostname:https://localhost:14443/}")
    private String wsHostName = null;
    @Value("${webservice.resource.url:timeo-stakeholderservice/pages/matrix/stakeholder/}")
    private String resourceService = null;
    @Value("${webservice.resources.url:timeo-stakeholderservice/pages/matrix/stakeholders}")
    private String allResourcesService = null;

    private String resourceServiceUrl = null;
    private String allResourcesServiceUrl = null;

    @Override
    public Resource loadResourceByMasterKey(String masterKey) {
        String localResourceServiceUrl = new StringBuilder(resourceServiceUrl).append(masterKey).toString();
        logger.info(StringUtils.join("ResourceServiceBean ", toString(), " loading resource for masterKey '", masterKey, "' from URL ", localResourceServiceUrl, "."));

        if(StringUtils.isBlank(masterKey)){
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new URL(localResourceServiceUrl), Resource.class);
        } catch (IOException exception) {
            String errMsg = StringUtils.join("Could not load resource for master key '", masterKey, "' from URL ", localResourceServiceUrl, ".");
            logger.error(errMsg, exception);
            throw new ResourceNotFoundException(errMsg, exception);
        }
    }

    @Override
    public List<Resource> loadAllResources() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Resource> resources = mapper.readValue(new URL(allResourcesServiceUrl), new TypeReference<List<Resource>>() {
            });
            resources.sort((o1, o2) -> {
                return o1.getName().compareTo(o2.getName());
            });
            return resources;
        } catch (IOException exception) {
            String errMsg = StringUtils.join("Could not load collection of resources from URL ", allResourcesServiceUrl, ".");
            logger.error(errMsg, exception);
            throw new ResourceNotFoundException(errMsg, exception);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(wsHostName)) {
            String errMsg = StringUtils.join("The wsHostName must be configured on ResourceService. This is the name of the host on which the restful WS are running.");
            logger.error(errMsg);
            throw new BeanCreationException(errMsg);
        }
        if (StringUtils.isBlank(resourceService)) {
            String errMsg = StringUtils.join("The resourceService must be configured on ResourceService. This is the name of the restful WS which delivers resource information.");
            logger.error(errMsg);
            throw new BeanCreationException(errMsg);
        }
        if (StringUtils.isBlank(allResourcesService)) {
            String errMsg = StringUtils.join("The allResourceService must be configured on ResourceService. This is the name of the restful WS which delivers a collection of all available resources.");
            logger.error(errMsg);
            throw new BeanCreationException(errMsg);
        }
        resourceServiceUrl = new StringBuilder(wsHostName).append(resourceService).toString();
        allResourcesServiceUrl = new StringBuilder(wsHostName).append(allResourcesService).toString();
        logger.info(StringUtils.join("Initialized ResourceServiceBean ", toString(), " with resourceServiceUrl ", resourceServiceUrl,
                " and allResourcesServiceUrl ", allResourcesServiceUrl));
    }

}
