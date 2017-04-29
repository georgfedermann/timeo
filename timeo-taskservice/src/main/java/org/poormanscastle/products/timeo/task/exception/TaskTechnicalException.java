package org.poormanscastle.products.timeo.task.exception;

/**
 * Created by georg on 20/02/2017.
 */
public class TaskTechnicalException extends TaskException {
    public TaskTechnicalException() {
    }

    public TaskTechnicalException(String message) {
        super(message);
    }

    public TaskTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskTechnicalException(Throwable cause) {
        super(cause);
    }

    public TaskTechnicalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
