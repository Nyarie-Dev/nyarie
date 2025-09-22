package eu.nyarie.core.io.installation.exception;

import eu.nyarie.core.api.exception.NyarieException;

import java.io.FileNotFoundException;

public class InstallationDirectoryException extends NyarieException {

    private static final String CONFIGURED_PATH_NOT_ABSOLUTE = "The configured asset directory must be absolute - was: %s";
    private static final String CONFIGURED_DIRECTORY_NOT_FOUND = "The configured installation directory could not be found: %s";

    protected InstallationDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    protected InstallationDirectoryException(String message) {
        super(message);
    }

    public static InstallationDirectoryException configuredPathNotAbsolute(String path) {
        return new InstallationDirectoryException(CONFIGURED_PATH_NOT_ABSOLUTE.formatted(path));
    }

    public static InstallationDirectoryException installationDirectoryNotFound(String directory, FileNotFoundException cause) {
        return new InstallationDirectoryException(CONFIGURED_DIRECTORY_NOT_FOUND.formatted(directory), cause);
    }
}
