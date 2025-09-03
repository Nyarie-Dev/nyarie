package eu.nyarie.core.io.assets.exception;

import java.io.FileNotFoundException;

public class AssetNotFoundException extends AssetException {

    private static final String CONFIGURED_DIRECTORY_NOT_FOUND = "The configured asset directory could not be found: %s";

    protected AssetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AssetNotFoundException assetDirectoryNotFound(String directory, FileNotFoundException cause) {
        return new AssetNotFoundException(CONFIGURED_DIRECTORY_NOT_FOUND.formatted(directory), cause);
    }
}
