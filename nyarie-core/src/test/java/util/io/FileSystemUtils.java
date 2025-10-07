package util.io;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

/// A class containing utils for file system interactions.
public class FileSystemUtils {

    public static Path jarPath() {
        return Path.of(jarLocation());
    }

    public static Path jarPath(Class<?> clazz) {
        return Path.of(jarLocation(clazz));
    }

    public static URI jarLocation() {
        return jarLocation(FileSystemUtils.class);
    }
    
    public static URI jarLocation(Class<?> clazz) {
        try {
            return clazz.getProtectionDomain().getCodeSource().getLocation().toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
