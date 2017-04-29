package org.poormanscastle.products.timeo.stakeholdermatrix.domain.exceptions;

/**
 * This exception is raised if a Stakeholder is created from data referencing an Organisation that cannot be found in
 * the system. Remedy might be to double check the organisation and then let the system create a fitting Organisation
 * for the current case. Or skip this stakeholder altogether.
 * Created by georg on 30.10.16.
 */
public class OrganisationNotFoundException extends StakeholderMatrixBusinessException {

    private final String organisationName;

    public OrganisationNotFoundException(String organisationName, String message) {
        super(message);
        this.organisationName = organisationName;
    }

    public OrganisationNotFoundException(String organisationName, String message, Throwable cause) {
        super(message, cause);
        this.organisationName = organisationName;
    }

    public OrganisationNotFoundException(String organisationName, Throwable cause) {
        super(cause);
        this.organisationName = organisationName;
    }

    protected OrganisationNotFoundException(String organisationName, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.organisationName = organisationName;
    }

    public String getOrganisationName() {
        return organisationName;
    }
    
}
