package org.poormanscastle.products.timeo.stakeholdermatrix.domain;

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
@RooSerializable
@RooJpaActiveRecord(finders = {"findBusinessSectionsByNameEquals"})
public class BusinessSection {

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

    public BusinessSection(String name) {
        this.name = name;
    }

    /**
     * The name of the business section, like CIO, COO, external
     */
    @NotNull
    @Column(unique = true)
    @Size(min = 1)
    private String name;

}
