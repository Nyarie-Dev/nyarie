package eu.nyarie.core.io.init;


import eu.nyarie.core.io.CsvFileLoader;
import eu.nyarie.core.io.PngFileLoader;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import eu.nyarie.core.util.io.FileSystemUtils;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("ALL")
class MapDataLoaderTest extends AbstractIoTest {

    private static final Path jarPath = FileSystemUtils.jarPath(MapDataLoader.class);
    private static final CsvFileLoader csvFileLoader = new CsvFileLoader();
    private static final PngFileLoader pngFileLoader = new PngFileLoader();
    private static final MapDataLoader mapDataLoader = new MapDataLoader(csvFileLoader, pngFileLoader);

    private static final String sourceCsvString = """
            1,Plains,1
            2,Mountains,10
            3,Hills,3
            4,Desert,1
            """;

    @Nested
    @DisplayName("")
    class LoadAssetFile {

        @Nested
        @DisplayName("with existing file")
        class WithExistingFile {

            TerrainTypeCsvContent result;

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve(InitFilePaths.TERRAIN_TYPES.getPath());
                Files.createDirectories(finalPath.getParent());
                Files.writeString(finalPath, sourceCsvString);
                result = mapDataLoader.loadTerrainTypes(jarPath);
            }

            @Test
            @DisplayName("should return List with correct size")
            void shouldReturnOptionalOfList() {
                assertThat(result.getData()).hasSize(4);
            }

            @Test
            @DisplayName("should have correct id")
            void shouldHaveCorrectId() {
                assertThat(result.getData().getFirst().getId()).isEqualTo(1);
                assertThat(result.getData().get(1).getId()).isEqualTo(2);
                assertThat(result.getData().get(2).getId()).isEqualTo(3);
                assertThat(result.getData().get(3).getId()).isEqualTo(4);
            }

            @Test
            @DisplayName("should have correct name")
            void shouldHaveCorrectName() {
                assertThat(result.getData().getFirst().getName()).isEqualTo("Plains");
                assertThat(result.getData().get(1).getName()).isEqualTo("Mountains");
                assertThat(result.getData().get(2).getName()).isEqualTo("Hills");
                assertThat(result.getData().get(3).getName()).isEqualTo("Desert");
            }

            @Test
            @DisplayName("should have correct someString")
            void shouldHaveCorrectSomeString() {
                assertThat(result.getData().getFirst().getMoveIntoDuration()).isEqualTo(1);
                assertThat(result.getData().get(1).getMoveIntoDuration()).isEqualTo(10);
                assertThat(result.getData().get(2).getMoveIntoDuration()).isEqualTo(3);
                assertThat(result.getData().get(3).getMoveIntoDuration()).isEqualTo(1);
            }
        }

//        @Nested
//        @DisplayName("with non-existing file")
//        class WithNonExistingFile {
//
//            static final AssetFilePath<@NonNull TerrainTypesAsset> terrainTypeAssetFilePath = AssetPaths.TERRAIN_TYPES;
//            Optional<TerrainTypesAsset> result;
//
//            @BeforeEach
//            void setup() throws IOException {
//                val finalPath = jarPath.resolve(terrainTypeAssetFilePath.getPath());
//                Files.deleteIfExists(finalPath);
//                result = assetLoader.fromFileSystem(jarPath, terrainTypeAssetFilePath);
//            }
//
//            @Test
//            @DisplayName("should return empty optional")
//            void shouldReturnOptionalOfList() {
//                assertThat(result).isEmpty();
//            }
//        }
    }

    private static final record CsvDto(
            String id,
            String name,
            String someString
    ) {};
}