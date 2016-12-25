package org.lskk.lumen.helpdesk.core;

/**
 * Created by ceefour on 25/12/2016.
 */
public class LumenException extends RuntimeException {

    public LumenException() {
    }

    public LumenException(String message) {
        super(message);
    }

    public LumenException(String message, Throwable cause) {
        super(message, cause);
    }

    public LumenException(Throwable cause) {
        super(cause);
    }

    public LumenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public LumenException(Throwable cause, String format, Object... args) {
        super(String.format(format, args), cause);
    }

}
