package org.poormanscastle.products.timeo.webfrontend.exception;

/**
 * Created by georg on 01/05/2017.
 */
public class WebFrontendException extends RuntimeException {

    public WebFrontendException() {
    }

    public WebFrontendException(String message) {
        super(message);
    }

    public WebFrontendException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebFrontendException(Throwable cause) {
        super(cause);
    }

    public WebFrontendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
