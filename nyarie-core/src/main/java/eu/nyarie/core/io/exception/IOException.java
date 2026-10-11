package eu.nyarie.core.io.exception;

import eu.nyarie.core.api.exception.NyarieException;

/// Super-class for all exceptions regarding Nyarie Core's const data
public class IOException extends NyarieException {
    protected IOException(String message) {
        super(message);
    }

    protected IOException(String message, Throwable cause) {
        super(message, cause);
    }
}
