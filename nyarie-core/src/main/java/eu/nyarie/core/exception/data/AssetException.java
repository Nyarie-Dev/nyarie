package eu.nyarie.core.exception.data;

import eu.nyarie.core.api.exception.NyarieException;

/// Super-class for all exceptions regarding Nyarie Core's const data
public class AssetException extends NyarieException {
    protected AssetException(String message) {
        super(message);
    }

    protected AssetException(String message, Throwable cause) {
        super(message, cause);
    }
}
