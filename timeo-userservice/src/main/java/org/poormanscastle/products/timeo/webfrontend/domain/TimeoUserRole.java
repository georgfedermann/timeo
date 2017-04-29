package org.poormanscastle.products.timeo.webfrontend.domain;

import java.util.HashSet;
import java.util.Set;

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
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by georg on 03/04/2017.
 */
@RooToString
@RooJavaBean
@RooJpaActiveRecord
@RooSerializable
public class TimeoUserRole implements GrantedAuthority {


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
    @Size(min = 3)
    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }

    /**
     */
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "timeoUserRoles")
    private Set<TimeoUser> timeoUsers = new HashSet<TimeoUser>();
}
