package org.poormanscastle.products.timeo.task.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import com.fasterxml.jackson.annotation.JsonInclude;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooSerializable
public class Project {

    /**
     * using UUID as the primary key and entity id shall make the use case feasible to create
     * Project instances offline on client apps like on ios or android and later sync
     * data with servers.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    /**
     * This project's name.
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 3)
    private String name;

    /**
     * A descriptive text explaining the reason for this project.
     */
    @NotNull
    @Size(min = 2)
    private String description;

    /**
     * The date when the project was entered into the project management tool.
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
     * The id of the dbClarity entry that provides budget for this project.
     */
    // @Column(unique = true)
    // @Size(min = 3)
    private String dbClarityId;

    /**
     * The plan position provided with the dbClarity entry, e.g. monitoring.
     */
    // @Size(min = 3)
    private String planPosition;

    /**
     * spoc stands for single point of contact. masterKey because this is just
     * a reference to a resource made available by the stakeholder service.
     * <p>
     * this is the single point of contact for this project. Maybe there is no
     * such SPOC but a group of contacts or no contact at all. Feel free what to
     * do with this in such a situation.
     */
    private String spocMasterKey;

    @NotNull
    @ManyToOne
    private ProjectStatus projectStatus;

    /**
     * this field is intended to be used in the UI to display identifying information about the project SPOC,
     * since the masterKey is not self explanatory and no one wants to look up keys in the stakeholder datamatrix.
     * Fill it with some convenient values like the name, or the email address, or - maybe - both?
     * Be sure to have it updated to relect information about the actual stakeholder (who might change?) before
     * reading the value in the UI. This value shall not be persisted to persistence, but transferred via REST-ful WS.
     */
    @Transient
    @JsonInclude
    private String label;

}
