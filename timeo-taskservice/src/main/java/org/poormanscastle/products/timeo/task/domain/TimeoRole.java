package org.poormanscastle.products.timeo.task.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooSerializable
public class TimeoRole {

    /**
     * using UUID as the primary key and entity id shall make the use case feasible to create
     * TimeoRole instances offline on client apps like on ios or android and later sync
     * data with servers.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    /**
     * The name of this role
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 3)
    private String name;

    /**
     * A descriptive text to explain the meaning of this role.
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 3)
    private String description;
}
