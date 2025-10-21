package eu.nyarie.core.io.assets.loader;


import eu.nyarie.core.io.assets.map.RegionAsset;
import eu.nyarie.core.io.assets.map.TerrainTypeAsset;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import eu.nyarie.core.util.io.FileSystemUtils;
import eu.nyarie.core.util.serialization.NyarieObjectMapper;
import lombok.val;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@SuppressWarnings("ALL")
class AssetFileLoaderTest extends AbstractIoTest {

    private static final Path jarPath = FileSystemUtils.jarPath(AssetFileLoader.class);
    private static final AssetFileLoader assetLoader = new AssetFileLoader(jarPath);

    @Nested
    @DisplayName("loadAssetFile")
    class LoadAssetFile {

        @Nested
        @DisplayName("with existing asset file")
        class WithExistingAssetFile {

            static final AssetFilePath<@NonNull RegionAsset> regionAssetFilePath = AssetPaths.REGIONS;
            Optional<RegionAsset> result;

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve(regionAssetFilePath.getPath());
                Files.createDirectories(finalPath.getParent());
                val jsonString = new NyarieObjectMapper().getInstance().writeValueAsString(new RegionAsset("1", "Gondor", "1"));
                Files.writeString(finalPath, jsonString);
                result = assetLoader.loadAssetFile(regionAssetFilePath);
            }

            @Test
            @DisplayName("should return optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isPresent();
            }

            @Test
            @DisplayName("should have correct id")
            void shouldHaveCorrectEntriesInList() {
                assertThat(result.orElseThrow().getId()).isEqualTo("1");
            }
        }

        @Nested
        @DisplayName("with non-existing asset file")
        class WithNonExistingAssetFile {

            static final AssetFilePath<@NonNull TerrainTypeAsset> terrainTypeAssetFilePath = AssetPaths.TERRAIN_TYPES;
            Optional<TerrainTypeAsset> result;

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

    @Nested
    @DisplayName("fromFileSystenWithClassPathFallback")
    class FromFileSystenWithClassPathFallback {

        @Nested
        @DisplayName("with existing asset file")
        class WithExistingAssetFile {

            static final AssetFilePath<@NonNull RegionAsset> regionAssetFilePath = AssetPaths.REGIONS;
            Optional<RegionAsset> result;

            @BeforeEach
            void setup() throws IOException {
                result = assetLoader.fromFileSystemWithClasspathFallback(regionAssetFilePath);
            }

            @Test
            @DisplayName("should return optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isPresent();
            }

            @Test
            @DisplayName("should have correct id")
            void shouldHaveCorrectEntriesInList() {
                assertThat(result.orElseThrow().getId()).isEqualTo("1");
            }
        }

        @Nested
        @DisplayName("with non-existing asset file")
        class WithNonExistingAssetFile {

            static final AssetFilePath<@NonNull TerrainTypeAsset> terrainTypeAssetFilePath = AssetPaths.TERRAIN_TYPES;
            Optional<TerrainTypeAsset> result;

            @BeforeEach
            void setup() throws IOException {
                result = assetLoader.fromFileSystemWithClasspathFallback(terrainTypeAssetFilePath);
            }

            @Test
            @DisplayName("should return empty optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isEmpty();
            }
        }
    }
}