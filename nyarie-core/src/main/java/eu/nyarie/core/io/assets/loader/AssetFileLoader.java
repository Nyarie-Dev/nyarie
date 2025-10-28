package eu.nyarie.core.io.assets.loader;

import com.fasterxml.jackson.databind.DatabindException;
import eu.luktronic.logblock.LogBlock;
import eu.nyarie.core.io.assets.AssetDto;
import eu.nyarie.core.io.assets.AssetFileDto;
import eu.nyarie.core.io.assets.exception.AssetLoadingException;
import eu.nyarie.core.io.assets.exception.AssetNotFoundException;
import eu.nyarie.core.util.serialization.NyarieObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/// Class that loads a single asset file and converts it into the respective
/// class.
@Slf4j
public class AssetFileLoader {

    /// Loads the assets from a combination of the passed `basePath`
    /// as well as the [assetFilePath][AssetFilePath] and returns a list
    /// of the assets cast to their respective [AssetDto] class.
    ///
    /// The final [Path] where the asset file will be searched for is made from `basePath` + `assetFilePath`.<br>
    /// For example, if the `basePath` is set to `/nyarie` and the `assetFilePath` is [AssetPaths#REGIONS], then the
    /// path of the asset file will be:
    /// ```text
    /// /nyarie/assets/map/region.json
    /// ```
    /// @param assetFilePath The path to the asset file. Must be an existing file.
    /// @throws AssetNotFoundException {@inheritDoc}
    public <T extends AssetFileDto<?>> Optional<T> fromFileSystem(Path basePath, AssetFilePath<T> assetFilePath) {
        val path = basePath.resolve(assetFilePath.getPath());
        log.debug("Loading asset file for class '{}': {}", assetFilePath.getAssetClass().getSimpleName(), path);

        if(Files.notExists(path)) {
            log.debug("Asset file '{}' was not found, returning empty optional", path);
            return Optional.empty();
        }

        log.debug("Found asset file '{}'", path);
        val om = new NyarieObjectMapper().getInstance();
        try {
            log.debug("Deserializing asset file '{}'", path);
            val response = om.readValue(path.toFile(), assetFilePath.getAssetClass());
            log.debug("Loaded asset file {}", assetFilePath.getPath());
            return Optional.of(response);
        }
        catch (DatabindException e) {
            log.error("Invalid JSON structure while reading asset file: {}", path);
            throw AssetLoadingException.invalidStructure(path);
        }
        catch (Exception e) {
            val logBlock = LogBlock.withLogger(log);
            logBlock.error("""
                    ERROR WHILE LOADING ASSET FILE:
                    {}
                    
                    An unexpected {} occurred while trying to read the asset file:
                    '{}'
                    """, path, e.getClass().getSimpleName(), e.getMessage());
            throw AssetLoadingException.unexpectedErrorReadingFile(path, e);
        }
    }

    public <T extends AssetFileDto<?>> Optional<T> fromFileSystemWithClasspathFallback(Path basePath, AssetFilePath<T> assetFilePath) {
        val fileSystemAsset = fromFileSystem(basePath, assetFilePath);
        if(fileSystemAsset.isPresent()) {
            log.debug("Asset was loaded using file system - skipping classpath loading");
            return fileSystemAsset;
        }
        else
            log.debug("Asset not present in file system - falling back to classpath loading");

        val path = assetFilePath.getPath();
        log.debug("Loading asset file for class '{}' from classpath resource: {}", assetFilePath.getAssetClass().getSimpleName(), path);

        try (val inputStream = this.getClass().getClassLoader().getResourceAsStream(assetFilePath.getPath().toString())) {
            if(inputStream == null) {
                log.debug("Asset file '{}' was not found, returning empty optional", path);
                return Optional.empty();
            }
            log.debug("Found asset file '{}'", path);

            val om = new NyarieObjectMapper().getInstance();
            log.debug("Deserializing asset file '{}'", path);
            val type = om.getTypeFactory().constructType(assetFilePath.getAssetClass());
            //noinspection unchecked
            val response = (T) om.readValue(inputStream, type);
            log.debug("Loaded asset file {}", assetFilePath.getPath());
            return Optional.of(response);

        } catch (IOException e) {
            val logBlock = LogBlock.withLogger(log);
            logBlock.error("""
                    ERROR WHILE LOADING ASSET FILE:
                    {}
                    
                    An unexpected {} occurred while trying to read the asset file:
                    '{}'
                    """, path, e.getClass().getSimpleName(), e.getMessage());
            throw AssetLoadingException.unexpectedErrorReadingFile(path, e);
        }
    }
}
