package eu.nyarie.core.io.assets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nyarie.core.domain.constant.Asset;
import eu.nyarie.core.io.assets.exception.AssetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/// Implementation for [AssetLoader] that loads asset files from the
/// file system.
@Slf4j
public class FileSystemAssetLoader implements AssetLoader {

    private final Path basePath;

    /// Creates an instance with a given `basePath`. The
    /// assets will be searched in the `/asset` subdirectory of the
    /// `basePath`.
    ///
    /// For example, if the `basePath` is set to `/nyarie`, the assets will be searched
    /// in `/nyarie/assets`
    /// @param basePath The [Path] in which the assets will be searched in.
    public FileSystemAssetLoader(Path basePath) {
        this.basePath = basePath;
    }

    /// @throws AssetNotFoundException {@inheritDoc}
    @Override
    public <T extends Asset> Optional<List<T>> loadAssetFile(AssetFilePath<T> assetFilePath) {
        val path = basePath.resolve(assetFilePath);
        log.debug("Loading asset file for class '{}': {}", assetFilePath.getAssetClass().getSimpleName(), path);

        if(Files.notExists(path)) {
            log.debug("Asset file '{}' was not found, returning empty optional", path);
            return Optional.empty();
        }

        val om = new ObjectMapper();
        try {
            val response = om.readValue(path.toFile(), new TypeReference<List<T>>() { });
            return Optional.of(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
