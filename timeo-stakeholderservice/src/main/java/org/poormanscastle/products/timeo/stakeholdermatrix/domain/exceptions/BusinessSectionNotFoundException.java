package org.poormanscastle.products.timeo.stakeholdermatrix.domain.exceptions;

/**
 * Created by georg on 30.10.16.
 */
public class BusinessSectionNotFoundException extends StakeholderMatrixBusinessException {

    private final String businessSectionName;

    public BusinessSectionNotFoundException(String businessSectionName, String message) {
        super(message);
        this.businessSectionName = businessSectionName;
    }

    public BusinessSectionNotFoundException(String businessSectionName, String message, Throwable cause) {
        super(message, cause);
        this.businessSectionName = businessSectionName;
    }

    public BusinessSectionNotFoundException(String businessSectionName, Throwable cause) {
        super(cause);
        this.businessSectionName = businessSectionName;
    }

    protected BusinessSectionNotFoundException(String businessSectionName, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.businessSectionName = businessSectionName;
    }

    public String getBusinessSectionName() {
        return businessSectionName;
    }
    
}

