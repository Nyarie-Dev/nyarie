package eu.nyarie.core.io.assets.exception;

public class AssetLoadingException extends AssetException {

    private static final String PATH_IS_NO_DIRECTORY = "The configured path is no directory: %s";

    protected AssetLoadingException(String message) {
        super(message);
    }

    public static AssetLoadingException pathIsNoDirectory(String path) {
        return new AssetLoadingException(PATH_IS_NO_DIRECTORY.formatted(path));
    }
}
