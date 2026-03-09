package eu.nyarie.core.io.installation.exception;

import eu.nyarie.core.api.exception.NyarieException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class InstallationDirectoryException extends NyarieException {

    private static final String PATH_IS_NO_DIRECTORY = "The configured path is no directory: %s";
    private static final String CONFIGURED_PATH_NOT_ABSOLUTE = "The configured asset directory must be absolute - was: %s";
    private static final String CONFIGURED_DIRECTORY_NOT_FOUND = "The configured installation directory could not be found: %s";
    private static final String COULD_NOT_CREATE_SUBDIRECOTRY = "Could not create subdirectory of installation directory: %s";

    protected InstallationDirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    protected InstallationDirectoryException(String message) {
        super(message);
    }

    public static InstallationDirectoryException pathIsNoDirectory(String path) {
        return new InstallationDirectoryException(PATH_IS_NO_DIRECTORY.formatted(path));
    }

    public static InstallationDirectoryException configuredPathNotAbsolute(String path) {
        return new InstallationDirectoryException(CONFIGURED_PATH_NOT_ABSOLUTE.formatted(path));
    }

    public static InstallationDirectoryException installationDirectoryNotFound(String directory, FileNotFoundException cause) {
        return new InstallationDirectoryException(CONFIGURED_DIRECTORY_NOT_FOUND.formatted(directory), cause);
    }

    public static InstallationDirectoryException couldNotCreateSubdirectory(String subdirectoryPath, IOException cause)  {
        return new InstallationDirectoryException(COULD_NOT_CREATE_SUBDIRECOTRY.formatted(subdirectoryPath), cause);
    }
}
