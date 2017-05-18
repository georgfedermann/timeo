package org.poormanscastle.products.timeo.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooSerializable
@RooJpaActiveRecord(finders = { "findTasksByProjectTeamMember" })
public class Task {

    /**
     * using UUID as the primary key and entity id shall make the use case feasible to create
     * Task instances offline on client apps like on ios or android and later sync
     * data with servers.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    /**
     * A unique, short name used to identify this task and reference to it, e.g. in JIRA.
     */
    @NotNull
    @Size(min = 3)
    private String name;

    /**
     * A descriptive text of arbitrary length to describe what this task is about.
     */
    @NotNull
    @Size(min = 3)
    private String description;

    /**
     * This field can be used to store comments about task execution or status updates.
     */
    @NotNull
    private String comment;

    /**
     * Estimated effort for this task in seconds.
     */
    @NotNull
    @Min(0L)
    private Long effortEstimation;

    /**
     * An additive value resulting from all reported actual efforts.
     * this is calculated JIT when it's needed and only cached for short term
     * periods to avoid problems with stale values.
     * TODO make this field transient and update the database schema. caveat: old database export become incompatible.
     */
    private Long effortMeasured;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date doneDate;

    /**
     * The goal which this task promotes.
     */
    @NotNull
    @ManyToOne
    private Goal goal;

    /**
     * The status identifying the current state of this task
     */
    @NotNull
    @ManyToOne
    private Status status;

    /**
     * The current priority of this task
     */
    @NotNull
    @ManyToOne
    private Priority priority;

    /**
     * The project this Task belongs to.
     */
    @NotNull
    @ManyToOne
    private Project project;

    @NotNull
    @ManyToOne
    private ProjectTeamMember projectTeamMember;
}
