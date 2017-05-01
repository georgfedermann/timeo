package org.poormanscastle.products.timeo.webfrontend.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoWebfrontendUser;
import org.poormanscastle.products.timeo.webfrontend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by georg on 01/05/2017.
 */
@Service("timeoUserDetailsService")
public class TimeoUserDetailsServiceBean implements UserDetailsService, InitializingBean {

    final static Logger logger = Logger.getLogger(TimeoUserDetailsServiceBean.class);
    final static boolean debug = logger.isDebugEnabled();
    final static boolean info = logger.isInfoEnabled();

    @Value("${ws.userservice.hostname}")
    private String wsUserserviceHostname = null;

    @Value("${ws.userservice.byLoginId.path}")
    private String wsUserserviceByLoginIdPath = null;

    /*
     * for my convenience, this String gets concatenated in afterPropertiesSet().
     */
    private String wsUserserviceByLoginIdUrl = null;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        checkNotNull(username, "Username must not be null.");
        String localWsUserserviceByLoginIdUrl = StringUtils.join(wsUserserviceByLoginIdUrl, username);
        logger.info(StringUtils.join(getClass().getSimpleName(), " ", toString(), " loading resource for username ",
                username, " from URL ", localWsUserserviceByLoginIdUrl, "."));

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new URL(localWsUserserviceByLoginIdUrl), TimeoWebfrontendUser.class);
        } catch (IOException exception) {
            String errMsg = StringUtils.join("Could not load resorce for username ", username, " from URL ",
                    localWsUserserviceByLoginIdUrl, ".");
            logger.error(errMsg, exception);
            throw new ResourceNotFoundException(errMsg, exception);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(wsUserserviceHostname)) {
            String errMsg = StringUtils.join("WebService hostname for the timeo-userservice is not set. It should be something like https://somehost.com:443/ and must be configured in timeo.properties.");
            logger.error(errMsg);
            throw new BeanCreationException(errMsg);
        }
        if (StringUtils.isBlank(wsUserserviceByLoginIdPath)) {
            String errMsg = StringUtils.join("The URL path for the resource/byLoginId webservice is not set. It should be something like timeo-userservice/ajax/resource/byLoginId/ and must be configured in timeo.properties.");
            logger.error(errMsg);
            throw new BeanCreationException(errMsg);
        }
        wsUserserviceByLoginIdUrl = StringUtils.join(wsUserserviceHostname, wsUserserviceByLoginIdPath);
        logger.info(StringUtils.join("Initialized TimeoUserDeatilsService Bean ", toString(), " with wsUserserviceByLoginIdUrl ",
                wsUserserviceByLoginIdUrl, "."));
    }

}
