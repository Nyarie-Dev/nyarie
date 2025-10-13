package eu.nyarie.core.io.assets.loader;


import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.domain.constant.map.TerrainType;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import eu.nyarie.core.util.io.FileSystemUtils;
import lombok.val;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
class FileSystemAssetLoaderTest extends AbstractIoTest {

    private static final Path jarPath = FileSystemUtils.jarPath(FileSystemAssetLoader.class);
    private static final FileSystemAssetLoader assetLoader = new FileSystemAssetLoader(jarPath);

    @Nested
    @DisplayName("loadAssetFile")
    class LoadAssetFile {

        @Nested
        @DisplayName("with existing asset file")
        class WithExistingAssetFile {

            static final AssetFilePath<@NonNull Region> regionAssetFilePath = AssetPaths.REGIONS;
            Optional<List<Region>> result;

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve(regionAssetFilePath.getPath());
                Files.createDirectories(finalPath.getParent());
                Files.writeString(finalPath, "[\n\n]");
                result = assetLoader.loadAssetFile(regionAssetFilePath);
            }

            @Test
            @DisplayName("should return optional of list")
            void shouldReturnOptionalOfList() {
                assertThat(result).isPresent();
            }

            @Test
            @DisplayName("should have correct entries in list")
            void shouldHaveCorrectEntriesInList() {
                assertThat(result.orElseThrow()).isEmpty();
            }
        }

        @Nested
        @DisplayName("with non-existing asset file")
        class WithNonExistingAssetFile {

            static final AssetFilePath<@NonNull TerrainType> terrainTypeAssetFilePath = AssetPaths.TERRAIN_TYPES;
            Optional<List<TerrainType>> result;

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve(terrainTypeAssetFilePath.getPath());
                Files.deleteIfExists(finalPath);
                result = assetLoader.loadAssetFile(terrainTypeAssetFilePath);
            }

            @Test
            @DisplayName("should return empty optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isEmpty();
            }
        }
    }
}