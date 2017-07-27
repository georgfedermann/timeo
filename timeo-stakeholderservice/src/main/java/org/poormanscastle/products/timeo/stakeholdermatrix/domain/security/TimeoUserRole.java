package org.poormanscastle.products.timeo.stakeholdermatrix.domain.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Maps to user roles as defined in the timeo-userservice for the respective installation of the system
 * Created by georg on 24/07/2017.
 */
public class TimeoUserRole implements GrantedAuthority {

    /**
     * this field gets filled with the name of the user role as defined for the respective usage
     * in the timeo-userservice user storage. This is configured for each timeo installation in
     * the user system.
     */
    private String roleName;

    public TimeoUserRole(String roleName) {
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
        return "TimeoUserRole{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
    
}
