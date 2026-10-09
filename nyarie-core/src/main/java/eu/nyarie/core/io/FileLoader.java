package eu.nyarie.core.io;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/// Exposes methods to load different kinds of files
/// from both the filesystem and the classpath.
@Slf4j
public class FileLoader {

    private final JsonFileLoader jsonFileLoader;
    private final CsvFileLoader csvFileLoader;
    private final PngFileLoader pngFileLoader;

    public FileLoader(JsonFileLoader jsonFileLoader, CsvFileLoader csvFileLoader, PngFileLoader pngFileLoader) {
        this.jsonFileLoader = jsonFileLoader;
        this.csvFileLoader = csvFileLoader;
        this.pngFileLoader = pngFileLoader;
    }

    /// Loads a JSON file from the file system and maps it to the class [T] that the [FilePath] links to.
    ///
    /// The final [Path] where the file will be searched under is made from `parentDir` + `filePath`.<br>
    /// For example, if the `parentDir` is set to `/nyarie` and the `filePath` contains `assets/map/map.json`, then the
    /// path of the file will be:
    /// ```text
    /// /nyarie/assets/map/map.json
    /// ```
    /// @param parentDir The path where the file should be searched in the file system.
    /// @param filePath The [FilePath] of the file that should be loaded.
    /// @return [Optional] containing the data mapped to class [T], or [Optional#empty()] if the file does not exist.
    public <T extends FileContentDto<?>> Optional<T> jsonFromFileSystem(Path parentDir, FilePath<T> filePath) {
        val path = parentDir.resolve(filePath.getPath());
        log.debug("Loading JSON file for class '{}': {}", filePath.getDtoClass().getSimpleName(), path);

        if(Files.notExists(path)) {
            log.debug("File '{}' was not found, returning empty optional", path);
            return Optional.empty();
        }

        return jsonFileLoader.readFile(path, filePath.getDtoClass(), defaultInputStreamSupplier(path));
    }

    /// Loads a JSON file from the file system by calling [#jsonFromFileSystem(Path, FilePath)].
    ///
    /// If the file does not exist on the file system, the classpath is searched under the
    /// specified `filePath`.
    ///
    /// For example, if the `parentDir` is `/home/john/nyarie` and the `filePath` contains `assets/map/map.json`,
    /// then the file will first be searched on the file system under:
    /// ```text
    /// /home/john/nyarie/assets/map/map.json
    /// ```
    /// If this file does not exist, then the classpath is searched under:
    /// ```text
    /// assets/map/map.json
    /// ```
    /// @param parentDir The path where the file should be searched in the file system.
    /// @param filePath The [FilePath] of the file that should be loaded.
    /// @return [Optional] containing the loaded [FileContentDto], or [Optional#empty()] if the file does not exist.
    public <T extends FileContentDto<?>> Optional<T> jsonFromFileSystemWithClasspathFallback(Path parentDir, FilePath<T> filePath) {
        val fileSystemAsset = jsonFromFileSystem(parentDir, filePath);
        if(fileSystemAsset.isPresent()) {
            log.debug("JSON file was loaded using file system - skipping classpath loading");
            return fileSystemAsset;
        }
        else
            log.debug("JSON file not present in file system - falling back to classpath loading");

        val path = filePath.getPath();
        log.debug("Loading JSON file for class '{}' from classpath resource: {}", filePath.getDtoClass().getSimpleName(), path);

        return jsonFileLoader.readFile(path, filePath.getDtoClass(), () -> this.getClass().getClassLoader().getResourceAsStream(filePath.getPath().toString()));
    }

    /// Loads a CSV file from the file system and maps it to the class [T] that the [FilePath] links to.
    ///
    /// The final [Path] where the file will be searched under is made from `parentDir` + `filePath`.<br>
    /// For example, if the `parentDir` is set to `/nyarie` and the `filePath` contains `init/map/terrain-types.csv`, then the
    /// path of the file will be:
    /// ```text
    /// /nyarie/init/map/terrain-types.csv
    /// ```
    /// @param parentDir The path where the file should be searched in the file system.
    /// @param filePath The [FilePath] of the file that should be loaded.
    /// @param csvSchema The [CsvSchema] that should be used to map the CSV columns to the target class.
    /// @return [List] containing the loaded [FileEntryDto], or empty [List] if the file does not exist.
    public <T extends FileEntryDto> List<T> csvFromFileSystem(Path parentDir, FilePath<T> filePath, CsvSchema csvSchema) {
        val path = parentDir.resolve(filePath.getPath());
        log.debug("Loading CSV file for class '{}': {}", filePath.getDtoClass().getSimpleName(), path);

        if(Files.notExists(path)) {
            log.debug("CSV file '{}' was not found, returning empty list", path);
            return List.of();
        }

        return csvFileLoader.readFile(path, filePath.getDtoClass(), csvSchema, defaultInputStreamSupplier(path));
    }

    /// Loads a CSV file from the file system by calling [#csvFromFileSystem(Path, FilePath, CsvSchema)].
    ///
    /// If the file does not exist on the file system, the classpath is searched under the
    /// specified `filePath`.
    ///
    /// For example, if the `parentDir` is `/home/john/nyarie` and the `filePath` contains `init/map/terrain-types.csv`,
    /// then the file will first be searched on the file system under:
    /// ```text
    /// /home/john/nyarie/init/map/terrain-types.csv
    /// ```
    /// If this file does not exist, then the classpath is searched under:
    /// ```text
    /// init/map/terrain-types.csv
    /// ```
    /// @param parentDir The path where the file should be searched in the file system.
    /// @param filePath The [FilePath] of the file that should be loaded.
    /// @param csvSchema The [CsvSchema] that should be used to map the CSV columns to the target class.
    /// @return [List] containing the loaded [FileEntryDto], or empty [List] if the file does not exist.
    public <T extends FileEntryDto> List<T> csvFromFileSystemWithClasspathFallback(Path parentDir, FilePath<T> filePath, CsvSchema csvSchema) {
        val fileSystemAsset = csvFromFileSystem(parentDir, filePath, csvSchema);
        if(!fileSystemAsset.isEmpty()) {
            log.debug("CSV file was loaded using file system - skipping classpath loading");
            return fileSystemAsset;
        }
        else
            log.debug("CSV file not present in file system - falling back to classpath loading");

        val path = filePath.getPath();
        log.debug("Loading CSV file for class '{}' from classpath resource: {}", filePath.getDtoClass().getSimpleName(), path);

        return csvFileLoader.readFile(path, filePath.getDtoClass(), csvSchema, () -> this.getClass().getClassLoader().getResourceAsStream(filePath.getPath().toString()));
    }

    private Supplier<InputStream> defaultInputStreamSupplier(Path path) {
        return () -> {
            try {
                return Files.newInputStream(path);
            } catch (IOException e) {
                log.warn("No InputStream could be created for path '{}'", path);
                log.warn("Caused by {}: {}", e.getClass().getSimpleName(), e.getMessage());
                log.warn("Passing null to readFile method");
                return null;
            }
        };
    }
}
