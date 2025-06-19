package eu.nyarie.core.exception.data;

public class ConstDataLoadingException extends ConstDataException {

    private static final String PATH_IS_NO_DIRECTORY = "The configured path is no directory: %s";

    protected ConstDataLoadingException(String message) {
        super(message);
    }

    public static ConstDataLoadingException pathIsNoDirectory(String path) {
        return new ConstDataLoadingException(PATH_IS_NO_DIRECTORY.formatted(path));
    }
}
