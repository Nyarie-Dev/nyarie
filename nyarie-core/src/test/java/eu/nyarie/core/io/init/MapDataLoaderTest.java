package eu.nyarie.core.io.init;


import eu.nyarie.core.io.FileLoader;
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
import java.util.List;

@SuppressWarnings("ALL")
class MapDataLoaderTest extends AbstractIoTest {

    private static final Path jarPath = FileSystemUtils.jarPath(MapDataLoader.class);
    private static final FileLoader fileLoader = new FileLoader();
    private static final MapDataLoader mapDataLoader = new MapDataLoader(fileLoader);

    private static final String sourceCsvString = """
            1,Plains,1
            2,Mountains,10
            3,Hills,3
            4,Desert,1
            """;

    @Nested
    @DisplayName("loadTerrainTypes")
    class LoadTerrainTypes {

        @Nested
        @DisplayName("with existing file")
        class WithExistingFile {

            List<TerrainTypeCsvDto> result;

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
                assertThat(result).hasSize(4);
            }

            @Test
            @DisplayName("should have correct id")
            void shouldHaveCorrectId() {
                assertThat(result.getFirst().id()).isEqualTo(1);
                assertThat(result.get(1).id()).isEqualTo(2);
                assertThat(result.get(2).id()).isEqualTo(3);
                assertThat(result.get(3).id()).isEqualTo(4);
            }

            @Test
            @DisplayName("should have correct name")
            void shouldHaveCorrectName() {
                assertThat(result.getFirst().name()).isEqualTo("Plains");
                assertThat(result.get(1).name()).isEqualTo("Mountains");
                assertThat(result.get(2).name()).isEqualTo("Hills");
                assertThat(result.get(3).name()).isEqualTo("Desert");
            }

            @Test
            @DisplayName("should have correct someString")
            void shouldHaveCorrectSomeString() {
                assertThat(result.getFirst().moveIntoDuration()).isEqualTo(1);
                assertThat(result.get(1).moveIntoDuration()).isEqualTo(10);
                assertThat(result.get(2).moveIntoDuration()).isEqualTo(3);
                assertThat(result.get(3).moveIntoDuration()).isEqualTo(1);
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