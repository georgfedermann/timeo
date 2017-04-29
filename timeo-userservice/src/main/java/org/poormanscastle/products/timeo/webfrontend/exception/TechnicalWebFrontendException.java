package org.poormanscastle.products.timeo.webfrontend.exception;

/**
 * This exception indicates that some technical exception appears to have occurred.
 * Created by georg on 20/03/2017.
 */
public class TechnicalWebFrontendException extends WebFrontendException {
    public TechnicalWebFrontendException() {
        super();
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

    protected TechnicalWebFrontendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
