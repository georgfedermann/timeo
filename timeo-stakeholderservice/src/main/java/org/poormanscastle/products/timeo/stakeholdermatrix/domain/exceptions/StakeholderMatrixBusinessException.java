package org.poormanscastle.products.timeo.stakeholdermatrix.domain.exceptions;

import org.poormanscastle.products.timeo.stakeholdermatrix.exceptions.StakeholderException;

/**
 * Created by georg on 03.11.16.
 */
public class StakeholderMatrixBusinessException extends StakeholderException {
    public StakeholderMatrixBusinessException() {
        super();
    }

    public StakeholderMatrixBusinessException(String message) {
        super(message);
    }

    public StakeholderMatrixBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public StakeholderMatrixBusinessException(Throwable cause) {
        super(cause);
    }

    protected StakeholderMatrixBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
