package eu.nyarie.core.api.exception.data;

import eu.nyarie.core.api.exception.NyarieException;

/// The base {@link NyarieException} that shall be thrown when
/// some data could not be found, for example, when using a {@link eu.nyarie.core.api.persistence Repository}.
public class NotFoundException extends NyarieException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
