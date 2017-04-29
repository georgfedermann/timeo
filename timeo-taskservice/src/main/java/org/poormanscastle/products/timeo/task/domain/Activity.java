package org.poormanscastle.products.timeo.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Tasks are realized by a sequence of zero or more activities.
 * Whenever a resource processes a task, an activity is created to
 * reflect this effort.
 * Created by georg on 26/02/2017.
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooSerializable
public class Activity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    /**
     * A short description explaining what has been done. Maybe refers to a JIRA ticket or something.
     */
    @NotNull
    @Size(min = 3)
    private String comment;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startDateTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endDateTime;

    @NotNull
    @ManyToOne
    private ProjectTeamMember projectTeamMember;

    @NotNull
    @ManyToOne
    private Task task;

}
