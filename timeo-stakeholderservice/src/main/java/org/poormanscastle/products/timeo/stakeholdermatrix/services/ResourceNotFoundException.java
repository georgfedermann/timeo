package org.poormanscastle.products.timeo.stakeholdermatrix.services;

import org.poormanscastle.products.timeo.stakeholdermatrix.exceptions.StakeholderException;

/**
 * This exception indicates that resources could not be loaded from the stakeholder micro service.
 * Created by georg on 20/02/2017.
 */
public class ResourceNotFoundException extends StakeholderException {
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
