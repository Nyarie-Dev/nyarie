package eu.nyarie.core.io.assets.loader;

import eu.luktronic.logblock.LogBlock;
import eu.nyarie.core.io.assets.AssetDto;
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

    private final Path basePath;

    /// Creates an instance with a given `basePath`. The
    /// assets will be searched in the `/asset` subdirectory of the
    /// `basePath`.
    ///
    /// For example, if the `basePath` is set to `/nyarie`, the assets will be searched
    /// in `/nyarie/assets`
    /// @param basePath The [Path] in which the assets will be searched in.
    public AssetFileLoader(Path basePath) {
        this.basePath = basePath;
    }

    /// @throws AssetNotFoundException {@inheritDoc}
    public <T extends AssetDto<?>> Optional<T> fromFileSystem(AssetFilePath<T> assetFilePath) {
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
        } catch (Exception e) {
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

    public <T extends AssetDto<?>> Optional<T> fromFileSystemWithClasspathFallback(AssetFilePath<T> assetFilePath) {
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
