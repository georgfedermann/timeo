package org.poormanscastle.products.timeo.webfrontend.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RooJavaBean
@RooSerializable
@RooJpaActiveRecord(finders = {"findTimeoUsersByLoginIdEquals"})
public class TimeoUser implements UserDetails {

    /**
     * using UUID as the primary key and entity id shall make the use case feasible to create
     * BusinessSection instances offline on client apps like on ios or android and later sync
     * data with servers.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @NotNull
    @Column(unique = true)
    @Size(min = 1)
    private String loginId;

    @NotNull
    @Size(min = 3)
    private String password;

    @NotNull
    @Column(unique = true)
    private String stakeholderId;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<TimeoUserRole> timeoUserRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return timeoUserRoles;
    }

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
        return "TimeoUser{" + ", entityManager=" + entityManager + ", id='" + id + '\'' + ", loginId='" + loginId + '\'' + ", stakeholderId='" + stakeholderId + '\'' + ", timeoUserRoles=" + timeoUserRoles + '}';
    }
}
