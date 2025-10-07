package util.io;

import java.net.URI;
import java.net.URISyntaxException;

/// A class containing utils for file system interactions.
public class FileSystemUtils {

    public URI jarLocation() {
        return jarLocation(FileSystemUtils.class);
    }
    
    public URI jarLocation(Class<?> clazz) {
        try {
            return clazz.getProtectionDomain().getCodeSource().getLocation().toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
