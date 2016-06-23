package org.lskk.lumen.helpdesk;

/**
 * Created by ceefour on 27/10/2015.
 */
public class HelpdeskException extends RuntimeException {

    public HelpdeskException() {
    }

    public HelpdeskException(String message) {
        super(message);
    }

    public HelpdeskException(String message, Throwable cause) {
        super(message, cause);
    }

    public HelpdeskException(Throwable cause) {
        super(cause);
    }

    public HelpdeskException(Throwable cause, String format, Object... params) {
        super(String.format(format, params), cause);
    }

    public HelpdeskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
