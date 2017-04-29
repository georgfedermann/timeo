package org.poormanscastle.products.timeo.webfrontend.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.poormanscastle.products.timeo.webfrontend.domain.TimeoUser;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by georg on 26/04/2017.
 */
@Service("timeoUserDetailsService")
@RooJavaBean
public class TimeoUserDetailsService implements UserDetailsService {

    final static Logger logger = Logger.getLogger(TimeoUserDetailsService.class);
    final static boolean debug = TimeoUserDetailsService.logger.isDebugEnabled();
    final static boolean info = TimeoUserDetailsService.logger.isInfoEnabled();

    /**
     * Locates the user based on the loginID, which typically is createc following the pattern
     * "firstname.lastname".
     *
     * @param username the loginId by which the user will be retrieved from user storage
     * @return actually a TimeoUser object
     * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<TimeoUser> timeoUsers = TimeoUser.findTimeoUsersByLoginIdEquals(username).getResultList();
        if (timeoUsers.size() == 1) {
            return timeoUsers.get(0);
        }
        throw new UsernameNotFoundException(StringUtils.join("No user found for username ", username, "."));
    }
    
}
