package eu.nyarie.core.util.io;

import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/// A class containing utils for file system interactions.
public class FileSystemUtils {

    /// Gets the first root path for the current system's
    /// file system.
    ///
    /// Usually this is equal to:
    /// - Unix: `/`
    /// - Windows: `C:\\`
    public static String getRoot() {
        return File.listRoots()[0].getPath();
    }

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

    /// Deletes a directory recursively, meaning that the directory and all its content
    /// will be deleted.
    ///
    /// If the passed [Path] is a regular file and not a directory, the File will simply get deleted.
    ///
    /// If the passed [Path] does not exist, nothing happens.
    public static void deleteRecursively(Path path) throws IOException {
        if(!Files.exists(path))
            return;

        if(!Files.isDirectory(path)) {
            Files.delete(path);
            return;
        }

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public @NonNull FileVisitResult postVisitDirectory(@NonNull Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public @NonNull FileVisitResult visitFile(@NonNull Path file, @NonNull BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
