package eu.nyarie.core.io.assets.exception;

import java.nio.file.Path;

public class AssetNotFoundException extends AssetException {

    private static final String REQUIRED_ASSET_FILE_NOT_FOUND = "Required asset file could not be found: %s";

    protected AssetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AssetNotFoundException(String message) {
        super(message);
    }
    
    public static AssetNotFoundException requiredAssetNotFound(Path path) {
        return new AssetNotFoundException(REQUIRED_ASSET_FILE_NOT_FOUND.formatted(path));
    }
}
