package org.poormanscastle.products.timeo.webfrontend.exception;

/**
 * This exception indicates that a resource could not be loaded from the stakeholder micro service.
 * Created by georg on 20/03/2017.
 */
public class ResourceNotFoundException extends TechnicalWebFrontendException {

    public ResourceNotFoundException() {
        super();
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

    protected ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
