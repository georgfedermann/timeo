package org.poormanscastle.products.timeo.webfrontend.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Maps to user roles as defined in the timeo-userservice for the respective installation of the system.
 *
 * Created by georg on 01/05/2017.
 */
public class TimeoWebfrontendUserRole implements GrantedAuthority {

    /**
     * this field gets filled with the name of the user role as defined for the respective usage
     * in the timeo-userservice user storage. This is configured for each timeo installation in
     * the user system.
     */
    private String roleName;

    public TimeoWebfrontendUserRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "TimeoWebfrontendUserRole{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
    
}
