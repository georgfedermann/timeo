package org.poormanscastle.products.timeo.webfrontend.service;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.webfrontend.domain.Resource;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUser;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUserRole;
import org.poormanscastle.products.timeo.webfrontend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by georg on 20/03/2017.
 */
@Service
public class ResourceServiceBean implements ResourceService, InitializingBean {

    final static Logger logger = Logger.getLogger(ResourceServiceBean.class);

    /**
     * something like this: https://somehost.com:443/
     */
    @Value("${webservice.hostname}")
    private String wsHostName = null;

    /**
     * something like timeo-stakeholderservice/pages/matrix/stakeholder
     */
    @Value("${webservice.resource.url}")
    private String resourceService = null;

    /**
     * something like timeo-stakeholderservice/pages/matrix/stakeholders
     */
    @Value("${webservice.resources.url}")
    private String allResourcesService = null;

    private String resourceServiceUrl = null;
    private String allResourcesServiceUrl = null;

    @Override
    public Resource loadResourceByMasterKey(String masterKey) {
        String localResourceServiceUrl = StringUtils.join(resourceServiceUrl, masterKey);
        logger.info(StringUtils.join("ResourceServiceBean ", toString(), " loading resource for masterKey ", masterKey, 
                " from URL ", localResourceServiceUrl, "."));
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new URL(localResourceServiceUrl), Resource.class);
        } catch (IOException exception) {
            String errMsg = StringUtils.join("Could not load resource for master key ", masterKey, " from URL ", 
                    localResourceServiceUrl, ".");
            logger.error(errMsg, exception);
            throw new ResourceNotFoundException(errMsg, exception);
        }
    }

    @Override
    public Resource loadResourceByLoginId(String loginId) {
        List<TimeoUser> timeoUsers = TimeoUser.findTimeoUsersByLoginIdEquals(loginId).getResultList();
        checkState(timeoUsers.size()==1, StringUtils.join("Expected to find excactly 1 entry for loginId ", loginId, 
                ", but there were ", timeoUsers.size()));
        TimeoUser timeoUser = timeoUsers.get(0);
        Resource resource = loadResourceByMasterKey(timeoUser.getStakeholderId());
        resource.setLoginId(loginId);
        for(GrantedAuthority userRole : timeoUser.getAuthorities()){
            resource.addGrantedAuthority(userRole.getAuthority());
        }
        return resource;
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
            String errMsg = StringUtils.join(
                    "WebService hostname is not set for resource service. This is the name of the host on which the resource services are hosted. Please configure it in the file timeo.properties.");
            logger.error(errMsg);
            throw new BeanCreationException(errMsg);
        }
        if (StringUtils.isBlank(resourceService)) {
            String errMsg = StringUtils.join("The URL path of the resource service is not set. Please configure it in the file timeo.properties.");
            logger.error(errMsg);
            throw new BeanCreationException(errMsg);
        }
        if (StringUtils.isBlank(allResourcesService)) {
            String errMsg = StringUtils.join("The URL path of the allResources service is not set. Please configure it in the file timeo.properties.");
            logger.error(errMsg);
            throw new BeanCreationException(errMsg);
        }
        resourceServiceUrl = new StringBuilder(wsHostName).append(resourceService).toString();
        allResourcesServiceUrl = new StringBuilder(wsHostName).append(allResourcesService).toString();
        logger.info(StringUtils.join("Initialized ResourceServiceBean ", toString(), " with resourceServiceUrl ", resourceServiceUrl,
                " and allResourcesServiceUrl ", allResourcesServiceUrl));
    }

}
