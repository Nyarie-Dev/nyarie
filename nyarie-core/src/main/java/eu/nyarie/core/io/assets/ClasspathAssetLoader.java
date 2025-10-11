package eu.nyarie.core.io.assets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.luktronic.logblock.LogBlock;
import eu.nyarie.core.domain.constant.Asset;
import eu.nyarie.core.io.assets.exception.AssetLoadingException;
import eu.nyarie.core.io.assets.exception.AssetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

/// Implementation for [AssetLoader] that loads asset files from the
/// JVM's classpath.
@Slf4j
public class ClasspathAssetLoader implements AssetLoader {

    /// @throws AssetNotFoundException {@inheritDoc}
    @Override
    public <T extends Asset> Optional<List<T>> loadAssetFile(AssetFilePath<T> assetFilePath) {
        val path = assetFilePath.getPath();
        log.debug("Loading asset file for class '{}' from classpath resource: {}", assetFilePath.getAssetClass().getSimpleName(), path);

        try (val inputStream = this.getClass().getClassLoader().getResourceAsStream("")) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(Files.notExists(path)) {
            log.debug("Asset file '{}' was not found, returning empty optional", path);
            return Optional.empty();
        }

        log.debug("Found asset file '{}'", path);
        val om = new ObjectMapper();
        try {
            log.debug("Deserializing asset file '{}'", path);
            val response = om.readValue(path.toFile(), new TypeReference<List<T>>() { });
            log.debug("Loaded {} instances of class {}", response.size(), assetFilePath.getAssetClass().getSimpleName());
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
}
