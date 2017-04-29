package org.poormanscastle.products.timeo.stakeholdermatrix.exceptions;

/**
 * The parent exception for all exceptions raised in the Stakeholder system component.
 * Use subtypes of this exception that best suite your needs or extend this type if no existing exception fits.
 * Created by georg on 30.10.16.
 */
public class StakeholderException extends RuntimeException {

    public StakeholderException() {
        super();
    }

    public StakeholderException(String message) {
        super(message);
    }

    public StakeholderException(String message, Throwable cause) {
        super(message, cause);
    }

    public StakeholderException(Throwable cause) {
        super(cause);
    }

    protected StakeholderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
