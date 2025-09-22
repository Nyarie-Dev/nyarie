package eu.nyarie.core.io.assets.exception;

public class AssetNotFoundException extends AssetException {

    protected AssetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AssetNotFoundException(String message) {
        super(message);
    }
}
