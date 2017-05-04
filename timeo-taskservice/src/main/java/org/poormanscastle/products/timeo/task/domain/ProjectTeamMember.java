package org.poormanscastle.products.timeo.task.domain;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This type is an association class mapping resources to projects
 * assigning the project role while at it.
 * Created by georg on 13/02/2017.
 */
@RooJavaBean
@RooToString
@RooSerializable
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"project", "resourceId", "role"})})
@RooJpaActiveRecord(finders = { "findProjectTeamMembersByProject", "findProjectTeamMembersByResourceIdEquals" })
public class ProjectTeamMember {

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
    @ManyToOne
    private TimeoRole role;

    /**
     * this is the masterKey of a stakeholder in the stakeholderMatrix service.
     */
    @NotNull
    private String resourceId;

    @NotNull
    @ManyToOne
    private Project project;

    /**
     * this field is intended to be used in the UI to display identifying information about the team member,
     * since the masterKey is not self explanatory and no one wants to look up names in the stakeholder datamatrix.
     * Fill it with some convenient values like the name or the email address.
     * Be sure to have it updated to reflect information about the actual stakeholder (who might change?) before
     * reading the value in the UI.
     */
    @Transient
    @JsonInclude
    private String label;
}
