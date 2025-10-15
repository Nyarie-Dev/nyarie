package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.map.RegionAsset;
import eu.nyarie.core.io.assets.map.TerrainTypeAsset;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
class ClasspathAssetLoaderTest extends AbstractIoTest {

    private static final ClasspathAssetLoader assetLoader = new ClasspathAssetLoader();

    @Nested
    @DisplayName("loadAssetFile")
    class LoadAssetFile {

        @Nested
        @DisplayName("with existing asset file")
        class WithExistingAssetFile {

            static final AssetFilePath<@NonNull RegionAsset> regionAssetFilePath = AssetPaths.REGIONS;
            Optional<List<RegionAsset>> result;

            @BeforeEach
            void setup() throws IOException {
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

            static final AssetFilePath<@NonNull TerrainTypeAsset> terrainTypeAssetFilePath = AssetPaths.TERRAIN_TYPES;
            Optional<List<TerrainTypeAsset>> result;

            @BeforeEach
            void setup() throws IOException {
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