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

/**
 * Project status reflects the state of a given project. E.g. in pipeline, in progress,
 * on hold, rejected, done, cancelled.
 * Created by georg on 27/03/2017.
 */
@RooJavaBean
@RooToString
@RooSerializable
@RooJpaActiveRecord
public class ProjectStatus {

    /**
     * using UUID as the primary key and entity id shall make the use case feasible to create
     * ProjectTeamMember instances offline on client apps like on ios or android and later sync
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
    private String name;

    @NotNull
    @Size(min = 3)
    private String description;

}
