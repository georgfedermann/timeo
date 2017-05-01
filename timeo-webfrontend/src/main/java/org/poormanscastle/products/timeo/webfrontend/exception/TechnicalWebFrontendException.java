package org.poormanscastle.products.timeo.webfrontend.exception;

/**
 * Created by georg on 01/05/2017.
 */
public class TechnicalWebFrontendException extends WebFrontendException {

    public TechnicalWebFrontendException() {
    }

    public TechnicalWebFrontendException(String message) {
        super(message);
    }

    public TechnicalWebFrontendException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalWebFrontendException(Throwable cause) {
        super(cause);
    }

    public TechnicalWebFrontendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
