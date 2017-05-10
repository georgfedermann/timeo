package org.poormanscastle.products.timeo.task.exception;

/**
 * Created by georg on 10/05/2017.
 */
public class InvalidDateStringException extends TaskTechnicalException {

    public InvalidDateStringException() {
    }

    public InvalidDateStringException(String message) {
        super(message);
    }

    public InvalidDateStringException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateStringException(Throwable cause) {
        super(cause);
    }

    public InvalidDateStringException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
