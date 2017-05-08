package org.poormanscastle.products.timeo.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
 * <p>
 * For any user there can only be one current activity at a given time (Since one can only
 * really do one thing at a time). Reasoning behind this is: if I lose the connection to the
 * server of close the user agent and start open the cockpit again, I can pick up where I left
 * things. Maybe it would also make sense to close an activity when the client stops sending
 * a heart beat, assuming that the user went home forgetting to log off the system. Anyway:
 * thing of a way that supports working with the system even if the user is not online all the
 * time and may report in data syncs when coming online again.
 * <p>
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
     * defines whether this activity is currently being worked on or
     * whether this activity has already been finished
     */
    @NotNull
    @Min(0L)
    @Max(2L)
    private Integer activityStatus;

    public ActivityStatus getActivityStatus() {
        return ActivityStatus.resolveStatus(activityStatus);
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus.getId();
    }

    /**
     * A short description explaining what has been done. Maybe refers to a JIRA ID or something.
     * has to be provided when the activityStatus switches from ONGOING to DONE.
     */
    @Size(min = 3)
    private String comment;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endDateTime;

    @NotNull
    @ManyToOne
    private ProjectTeamMember projectTeamMember;

    @NotNull
    @ManyToOne
    private Task task;

    /**
     * time in minutes invested into this activity.
     */
    @NotNull
    @Min(0L)
    private int timeInvested;

}
