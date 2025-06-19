package eu.nyarie.core.exception.data;

import eu.nyarie.core.api.exception.data.NotFoundException;

import java.io.FileNotFoundException;

public class ConstDataNotFoundException extends NotFoundException {

    private static final String CONFIGURED_DIRECTORY_NOT_FOUND = "The configured directory for const data could not be found: %s";

    protected ConstDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ConstDataNotFoundException constDataDirectoryNotFound(String directory, FileNotFoundException cause) {
        return new ConstDataNotFoundException(CONFIGURED_DIRECTORY_NOT_FOUND.formatted(directory), cause);
    }
}
