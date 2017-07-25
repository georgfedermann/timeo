package org.poormanscastle.products.timeo.task.domain.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * On (successful) authentication a TimeoUser instance is created and filled
 * with authorization information via the timeo-userservice and with user information
 * via the timeo-stakeholderservice. The authorization information is used to grant access
 * to relevant parts of the system.The interface is kept in the session to provide access
 * to personal information like email address within the UI and the respective
 * use cases.
 * Created by georg on 24/07/2017.
 */
public class TimeoUser implements UserDetails {

    /**********************************************************
     **** information retrieved from the timeo-userservice
     ***********************************************************/
    /**
     * the principal
     */
    private String loginId;

    /**
     * needed as key to lookup resource information in the timeo-stakeholder service.
     */
    private String masterKey;

    private List<String> grantedAuthorities = new ArrayList<>();

    private List<TimeoUserRole> userRoles = new ArrayList<>();

    /**********************************************************
     **** information retrieved from the timeo-stakeholderservice
     ***********************************************************/
    private String name;

    private String email;

    private String phone;

    private String businessAddress;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles;
    }

    @Override
    public String getPassword() {
        return "********";
    }

    /**
     * Implements UserDetails.getUsername() and thus returns the loginId. User getName()
     * to retrieve the acual name of the authenticated user.
     *
     * @return the loginId
     */
    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "TimeoWebfrontendUser{" +
                "loginId='" + loginId + '\'' +
                ", masterKey='" + masterKey + '\'' +
                ", grantedAuthorities=" + grantedAuthorities +
                ", userRoles=" + userRoles +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", businessAddress='" + businessAddress + '\'' +
                '}';
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    public List<String> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(List<String> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
        userRoles.clear();
        for (String authority : grantedAuthorities) {
            userRoles.add(new TimeoUserRole(authority));
        }
    }

    public List<TimeoUserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<TimeoUserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }
    
}
