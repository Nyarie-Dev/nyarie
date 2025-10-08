package eu.nyarie.core.io.assets;


import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import eu.nyarie.core.util.io.FileSystemUtils;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

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
                var finalPath = jarPath.resolve(regionAssetFilePath.getDelegate());
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
    }
}