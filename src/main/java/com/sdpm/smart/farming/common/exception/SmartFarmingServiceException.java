package com.sdpm.smart.farming.common.exception;

/**
 * 业务异常
 *
 * @author rukey
 */
public class SmartFarmingServiceException extends RuntimeException {
    private static final long serialVersionUID = 102410141126L;

    public SmartFarmingServiceException(String message) {
        this(message, null);
    }

    public SmartFarmingServiceException(Throwable cause) {
        this(null, cause);
    }

    public SmartFarmingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected SmartFarmingServiceException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
