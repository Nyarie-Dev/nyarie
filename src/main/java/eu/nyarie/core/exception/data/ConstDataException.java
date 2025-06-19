package eu.nyarie.core.exception.data;

import eu.nyarie.core.api.exception.NyarieException;

/// Super-class for all exceptions regarding Nyarie Core's const data
public class ConstDataException extends NyarieException {
    protected ConstDataException(String message) {
        super(message);
    }

    protected ConstDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
