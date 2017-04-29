package org.poormanscastle.products.timeo.stakeholdermatrix.exceptions;

import org.poormanscastle.products.timeo.stakeholdermatrix.exceptions.StakeholderException;

/**
 * Created by georg on 30.10.16.
 */
public class InvalidStakeholderInputDataException extends StakeholderException {

    public InvalidStakeholderInputDataException() {
        super();
    }

    public InvalidStakeholderInputDataException(String message) {
        super(message);
    }

    public InvalidStakeholderInputDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStakeholderInputDataException(Throwable cause) {
        super(cause);
    }

    protected InvalidStakeholderInputDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
