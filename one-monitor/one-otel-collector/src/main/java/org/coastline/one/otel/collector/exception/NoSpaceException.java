package org.coastline.one.otel.collector.exception;

/**
 * @author Jay.H.Zou
 * @date 2021/7/22
 */
public class NoSpaceException extends RuntimeException {
    public NoSpaceException() {
        super();
    }

    public NoSpaceException(String message) {
        super(message);
    }

    public NoSpaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
