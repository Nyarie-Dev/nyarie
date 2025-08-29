package eu.nyarie.core.api.exception;

/// Parent exception for all Nyarie exceptions
public class NyarieException extends RuntimeException {
    public NyarieException(String message) {
        super(message);
    }

    public NyarieException(String message, Throwable cause) {
      super(message, cause);
    }
}
