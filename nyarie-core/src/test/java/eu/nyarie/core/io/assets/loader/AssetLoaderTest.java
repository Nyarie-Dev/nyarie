package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.assets.LoadedAssetDirectory;
import eu.nyarie.core.io.installation.InstallationDirectory;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class AssetLoaderTest extends AbstractIoTest {

    private final AssetDirectoryLoader mockAssetDirectoryLoader = Mockito.mock(AssetDirectoryLoader.class);
    private final InstallationDirectory mockInstallationDirectory = Mockito.mock(InstallationDirectory.class);
    private final AssetLoader.ModDirectory mockModDirectory1 = Mockito.mock(AssetLoader.ModDirectory.class);
    private final AssetLoader.ModDirectory mockModDirectory2 = Mockito.mock(AssetLoader.ModDirectory.class);
    private final Set<AssetLoader.ModDirectory> mockModDirectories = Set.of(mockModDirectory1, mockModDirectory2);

    private final AssetLoader assetLoader = new AssetLoader(mockAssetDirectoryLoader, mockInstallationDirectory, mockModDirectories);

    @Nested
    @DisplayName("loadAssets")
    class LoadAssets {

        private LoadedAssetDirectory mockLoadedInstallationDirectory;
        private List<LoadedAssetDirectory> mockLoadedModDirectories = new ArrayList<>(mockModDirectories.size());
        private AssetContext result;

        @BeforeEach
        void setupStubs() {
            mockLoadedInstallationDirectory = Mockito.mock(LoadedAssetDirectory.class);
            Mockito.when(mockInstallationDirectory.getRootDirectory()).thenReturn(Path.of("installation"));
            Mockito.when(mockAssetDirectoryLoader.fromFileSystemWithClasspathFallback(mockInstallationDirectory.getRootDirectory()))
                    .thenReturn(mockLoadedInstallationDirectory);

            int index = 0;
            for (AssetLoader.ModDirectory directory : mockModDirectories) {
                Mockito.when(directory.getRootDirectory()).thenReturn(Path.of("mod" + index));
                val mockLoadedDirectory = Mockito.mock(LoadedAssetDirectory.class);
                mockLoadedModDirectories.add(index, mockLoadedDirectory);
                Mockito.when(mockAssetDirectoryLoader.fromFileSystem(directory.getRootDirectory())).thenReturn(mockLoadedDirectory);
                index++;
            }
            result = assetLoader.loadAssets();
        }

        @Test
        @DisplayName("should call AssetDirectoryLoader for installation directory")
        void shouldCallAssetDirectoryLoaderForInstallation() {
            Mockito.verify(mockAssetDirectoryLoader, Mockito.times(1))
                    .fromFileSystemWithClasspathFallback(mockInstallationDirectory.getRootDirectory());
        }

        @Test
        @DisplayName("should call AssetDirectoryLoader for each mod directory")
        void shouldCallAssetDirectoryLoaderForEachMod() {
            mockModDirectories.forEach(directory -> {
                Mockito.verify(mockAssetDirectoryLoader, Mockito.times(1))
                        .fromFileSystem(directory.getRootDirectory());
            });
        }

        @Test
        @DisplayName("should have expected loaded installation assets in returned AssetContext")
        void shouldHaveExpectedLoadedInstallationAssets() {
            assertThat(result.installationAssets()).isEqualTo(mockLoadedInstallationDirectory);
        }

        @Test
        @DisplayName("should have expected mod assets in returned AssetContext")
        void shouldHaveExpectedLoadedModAssets() {
            assertThatList(result.modAssets()).allSatisfy(modDirectory ->
                    assertThatList(mockLoadedModDirectories).containsOnlyOnce(modDirectory));
        }
    }

}