package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.map.RegionsAsset;
import eu.nyarie.core.io.assets.map.TerrainTypesAsset;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.util.Optional;

@SuppressWarnings("ALL")
class AssetDirectoryLoaderTest extends AbstractIoTest {

    private final AssetFileLoader mockAssetFileLoader = Mockito.mock(AssetFileLoader.class);
    private final AssetDirectoryLoader assetDirectoryLoader = new AssetDirectoryLoader(mockAssetFileLoader);

    private final Path path = Path.of("some-path");
    private final Optional<RegionsAsset> expectedRegions = Optional.of(new RegionsAsset());
    private final Optional<TerrainTypesAsset> expectedTerrainTypes = Optional.of(new TerrainTypesAsset());
    private final LoadedAssetDirectory expectedResult = new LoadedAssetDirectory(expectedRegions, expectedTerrainTypes);

    @Nested
    @DisplayName("fromFileSystem()")
    class FromFileSystem {

        LoadedAssetDirectory result;

        @BeforeEach
        void act() {
            Mockito.when(mockAssetFileLoader.fromFileSystem(path, AssetPaths.REGIONS)).thenReturn(expectedRegions);
            Mockito.when(mockAssetFileLoader.fromFileSystem(path, AssetPaths.TERRAIN_TYPES)).thenReturn(expectedTerrainTypes);

            result = assetDirectoryLoader.fromFileSystem(path);
        }

        @Test
        @DisplayName("should call AssetFileLoader exactly expected times")
        void shouldCallAssetFileLoaderExactlyExpectedTimes() {
            val totalInvocations = Mockito.mockingDetails(mockAssetFileLoader).getInvocations().size();
            assertThat(totalInvocations).isEqualTo(2);
        }

        @Test
        @DisplayName("should call AssetFileLoader for regions")
        void shouldCallAssetFileLoaderForRegions() {
            Mockito.verify(mockAssetFileLoader, Mockito.times(1)).fromFileSystem(path, AssetPaths.REGIONS);
        }

        @Test
        @DisplayName("should call AssetFileLoader for terrain types")
        void shouldCallAssetFileLoaderForTerrainTypes() {
            Mockito.verify(mockAssetFileLoader, Mockito.times(1)).fromFileSystem(path, AssetPaths.TERRAIN_TYPES);
        }

        @Test
        @DisplayName("should return expected result")
        void shouldReturnExpectedResult() {
            assertThat(result).isEqualTo(expectedResult);
        }
    }

    @Nested
    @DisplayName("fromFileSystemWithClasspathFallback()")
    class FromFileSystemWithClasspathFallback {

        LoadedAssetDirectory result;

        @BeforeEach
        void act() {
            Mockito.when(mockAssetFileLoader.fromFileSystemWithClasspathFallback(path, AssetPaths.REGIONS)).thenReturn(expectedRegions);
            Mockito.when(mockAssetFileLoader.fromFileSystemWithClasspathFallback(path, AssetPaths.TERRAIN_TYPES)).thenReturn(expectedTerrainTypes);

            result = assetDirectoryLoader.fromFileSystemWithClasspathFallback(path);
        }

        @Test
        @DisplayName("should call AssetFileLoader exactly expected times")
        void shouldCallAssetFileLoaderExactlyExpectedTimes() {
            val totalInvocations = Mockito.mockingDetails(mockAssetFileLoader).getInvocations().size();
            assertThat(totalInvocations).isEqualTo(2);
        }

        @Test
        @DisplayName("should call AssetFileLoader for regions")
        void shouldCallAssetFileLoaderForRegions() {
            Mockito.verify(mockAssetFileLoader, Mockito.times(1)).fromFileSystemWithClasspathFallback(path, AssetPaths.REGIONS);
        }

        @Test
        @DisplayName("should call AssetFileLoader for terrain types")
        void shouldCallAssetFileLoaderForTerrainTypes() {
            Mockito.verify(mockAssetFileLoader, Mockito.times(1)).fromFileSystemWithClasspathFallback(path, AssetPaths.TERRAIN_TYPES);
        }

        @Test
        @DisplayName("should return expected result")
        void shouldReturnExpectedResult() {
            assertThat(result).isEqualTo(expectedResult);
        }
    }

}