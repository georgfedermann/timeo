package org.poormanscastle.products.timeo.stakeholdermatrix.domain;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.exceptions.BusinessSectionNotFoundException;
import org.poormanscastle.products.timeo.stakeholdermatrix.domain.exceptions.OrganisationNotFoundException;
import org.poormanscastle.products.timeo.stakeholdermatrix.exceptions.InvalidStakeholderInputDataException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import com.fasterxml.jackson.annotation.JsonIgnore;

@RooJavaBean
@RooToString
@RooSerializable
@RooJpaActiveRecord(finders = {"findStakeholdersByMasterKey", "findStakeholdersByEmail"})
public class Stakeholder {

    private static final Logger logger = Logger.getLogger(Stakeholder.class);

    /**
     * using UUID as the primary key and entity id shall make the use case feasible to create
     * Stakeholder instances offline on client apps like on ios or android and later sync
     * data with servers.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;


    /**
     * The organisation this stakeholder belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Organisation organisation;

    /**
     * The business section this stakeholder belongs to. E.g. CIO, COO, etc.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private BusinessSection businessSection;

    /**
     * a synthetic key to establish a line management hierarchy
     */
    @NotNull
    @Column(unique = true)
    private String masterKey;

    /**
     * a synthetic key to establish line management hierarchy
     */
    private String reportsTo;

    /**
     * document whether I deal directly with this stakeholder
     */
    private int directContact;

    /**
     * The name of this stakeholder
     */
    @NotNull
    @Size(min = 1)
    private String name;

    /**
     * This stakeholder's function within the given organisation
     */
    private String functionWithinOrganisation;

    /**
     * Things to remember about this stakeholder
     */
    private String notes;

    /**
     * This stakeholder's email address
     */
    @Column(unique = true)
    private String email;

    /**
     * This stakeholder's telephone number
     */
    @Column(unique = true)
    private String phone;

    /**
     * This stakeholder's business address
     */
    private String businessAddress;

    /**
     * this method facilitates the parsing from string business data to Stakeholder instances.
     *
     * @param inputString    the string containing the business data
     * @param separatorRegex the character used to separate values, e.g. comma, tab, colon, etc.
     * @return a Stakeholder object created from the given business data.
     */
    public Stakeholder parseStakeholderFromCsvString(String inputString, String separatorRegex) {
        String[] stakeholderData = inputString.split(separatorRegex);
        checkArgument(stakeholderData.length == 11, StringUtils.join("A data set must contain 11 data elements but this one contains ", stakeholderData.length, ": ", Arrays.toString(stakeholderData)));
        if ("-".equals(stakeholderData[0])) {
            setOrganisation(null);
        } else {
            List<Organisation> organisations = Organisation.findOrganisationsByNameEquals(stakeholderData[0]).getResultList();
            if (organisations.size() == 0) {
                String errorMessage = StringUtils.join("Error while parsing stakeholder data '", inputString, "': Organisation '", stakeholderData[0], "' not found.");
                logger.error(errorMessage);
                throw new OrganisationNotFoundException(stakeholderData[0], errorMessage);
            }
            setOrganisation(organisations.get(0));
        }
        // 2017-02-09 stakeholder key was changed from long value to UUID String. Now, the key can be any String as long
        // as it's not empty, consisting of white space only, or null.
        if (StringUtils.isBlank(stakeholderData[1])) {
            String errorMessage = StringUtils.join("Error while parsing stakeholder data '", inputString, "': Key is not a valid value: '", stakeholderData[1], "'.");
            logger.error(errorMessage);
            throw new InvalidStakeholderInputDataException(errorMessage);
        }
        setMasterKey(stakeholderData[1]);
        // 2017-02-09 reportsTo has been changed to a UUID String value. if the input is "-" then reportsTo is 
        // considered to be unknown. The "-" is accepted and interpreted as "unknown".
        if ("-".equals(stakeholderData[2])) {
            setReportsTo(stakeholderData[2]);
        } else {
            if (StringUtils.isBlank(stakeholderData[2])) {
                String errorMessage = StringUtils.join("Error while parsing stakeholder data '", inputString, "': reportsTo is not a valid long value: '", stakeholderData[2], "'.");
                logger.error(errorMessage);
                throw new InvalidStakeholderInputDataException(errorMessage);
            }
            // TODO a check could be added if reportsTo actually points to an existing stakeholder in persistence.
            setReportsTo(stakeholderData[2]);
        }
        // Business section. Expected is something like CTO, COO, etc. Run the bank, change the bank, etc. Feel free.
        // If the business section is "-" then it is regarded to be unknown.
        if ("-".equals(stakeholderData[3])) {
            setBusinessSection(null);
        } else {
            List<BusinessSection> sections = BusinessSection.findBusinessSectionsByNameEquals(stakeholderData[3]).getResultList();
            if (sections.size() != 1) {
                String errorMessage = StringUtils.join("Error while parsing stakeholder data '", inputString, "', business section '", stakeholderData[3], "' not found.");
                logger.error(errorMessage);
                throw new BusinessSectionNotFoundException(stakeholderData[3], errorMessage);
            }
            setBusinessSection(sections.get(0));
        }
        if (!StringUtils.isNumeric(stakeholderData[4]) || Integer.parseInt(stakeholderData[4]) != 0 && Integer.parseInt(stakeholderData[4]) != 1) {
            String errorMessage = StringUtils.join("Error while parsing stakeholder data '", inputString, "', directContact '", stakeholderData[4], "' is not 0 or 1.");
            logger.error(errorMessage);
            throw new InvalidStakeholderInputDataException(errorMessage);
        }
        setDirectContact(Integer.parseInt(stakeholderData[4]));
        setName(stakeholderData[5]);
        setFunctionWithinOrganisation(stakeholderData[6]);
        setNotes(stakeholderData[7]);
        setEmail(stakeholderData[8]);
        setPhone(stakeholderData[9]);
        setBusinessAddress(stakeholderData[10]);
        return this;
    }
}
