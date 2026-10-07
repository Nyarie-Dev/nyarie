package eu.nyarie.core.io;


import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
class CsvFileLoaderTest extends AbstractIoTest {

    private static final Path jarPath = FileSystemUtils.jarPath(CsvFileLoader.class);
    private static final CsvFileLoader csvFileLoader = new CsvFileLoader();

    private static final CsvSchema csvSchema = CsvSchema.builder()
            .addNumberColumn("id")
            .addColumn("name")
            .addColumn("someString")
            .build();
    private static final String sourceCsvString = """
            1,Hello,World
            2,Hello2,World2
            """;

    @Nested
    @DisplayName("loadAssetFile")
    class LoadAssetFile {

        @Nested
        @DisplayName("with existing file")
        class WithExistingFile {

            List<CsvDto> result;

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve("CsvFileLoaderTest.csv");
                Files.writeString(finalPath, sourceCsvString);
                result = csvFileLoader.readAsset(finalPath, CsvDto.class, csvSchema, () -> {
                    try {
                        return Files.newInputStream(finalPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Test
            @DisplayName("should return List with correct size")
            void shouldReturnOptionalOfList() {
                assertThat(result).hasSize(2);
            }

            @Test
            @DisplayName("should have correct id")
            void shouldHaveCorrectId() {
                assertThat(result.getFirst().id()).isEqualTo("1");
                assertThat(result.get(1).id()).isEqualTo("2");
            }

            @Test
            @DisplayName("should have correct name")
            void shouldHaveCorrectName() {
                assertThat(result.getFirst().name()).isEqualTo("Hello");
                assertThat(result.get(1).name()).isEqualTo("Hello2");
            }

            @Test
            @DisplayName("should have correct someString")
            void shouldHaveCorrectSomeString() {
                assertThat(result.getFirst().someString()).isEqualTo("World");
                assertThat(result.get(1).someString()).isEqualTo("World2");
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