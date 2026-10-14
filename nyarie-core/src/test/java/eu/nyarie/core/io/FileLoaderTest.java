package eu.nyarie.core.io;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import eu.nyarie.core.io.exception.FileLoadingException;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import eu.nyarie.core.util.io.FileSystemUtils;
import eu.nyarie.core.util.serialization.NyarieObjectMappers;
import lombok.*;
import org.assertj.core.api.ThrowableAssertAlternative;
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
class FileLoaderTest extends AbstractIoTest {

    private static final Path jarPath = FileSystemUtils.jarPath(FileLoader.class);
    private static final Path testPath = Path.of("test", "io");
    private static final Path nonexistingPath = Path.of("some", "nonexisting", "path");
    private static final JsonFileLoader jsonFileLoader = new JsonFileLoader();
    private static final CsvFileLoader csvFileLoader = new CsvFileLoader();
    private static final PngFileLoader pngFileLoader = new PngFileLoader();
    private static final FileLoader fileLoader = new FileLoader(jsonFileLoader, csvFileLoader, pngFileLoader);

    @Nested
    @DisplayName("fromFileSystem")
    class FromFileSystem {

        @Nested
        @DisplayName("with existing asset file")
        class WithExistingAssetFile {

            static final FilePath testFilePath = new FilePath<>(testPath.resolve("FileLoaderTest.json"), MyFileContentDto.class);
            Optional<MyFileContentDto> result;

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve(testFilePath.getPath());
                Files.createDirectories(finalPath.getParent());
                val jsonString = new NyarieObjectMappers().getJsonMapperInstance()
                        .writeValueAsString(new MyFileContentDto(List.of(new MyFileEntryDto(1, "Belegorn", "Arnorion"))));
                Files.writeString(finalPath, jsonString);
                result = fileLoader.jsonFromFileSystem(jarPath, testFilePath);
            }

            @Test
            @DisplayName("should return optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isPresent();
            }

            @Test
            @DisplayName("should have correct id")
            void shouldHaveCorrectEntriesInList() {
                assertThat(result.orElseThrow().data().getFirst().getId()).isEqualTo(1);
            }
        }

        @Nested
        @DisplayName("with non-existing asset file")
        class WithNonExistingAssetFile {

            static final FilePath testFilePath = new FilePath<>(nonexistingPath.resolve("SomeNonExistingFile.json"), MyFileContentDto.class);
            Optional<MyFileContentDto> result;

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve(testFilePath.getPath());
                Files.deleteIfExists(finalPath);
                result = fileLoader.jsonFromFileSystem(jarPath, testFilePath);
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

            static final FilePath testFilePath = new FilePath<>(testPath.resolve("FileLoaderTest.json"), MyFileContentDto.class);
            Optional<MyFileContentDto> result;

            @BeforeEach
            void setup() throws IOException {
                result = fileLoader.jsonFromFileSystemWithClasspathFallback(jarPath, testFilePath);
            }

            @Test
            @DisplayName("should return optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isPresent();
            }

            @Test
            @DisplayName("should have correct id")
            void shouldHaveCorrectEntriesInList() {
                assertThat(result.orElseThrow().data().getFirst().getId()).isEqualTo(1);
            }
        }

        @Nested
        @DisplayName("with non-existing asset file")
        class WithNonExistingAssetFile {

            static final FilePath testFilePath = new FilePath<>(nonexistingPath.resolve("SomeNonExistingFile.json"), MyFileContentDto.class);
            Optional<MyFileContentDto> result;

            @BeforeEach
            void setup() throws IOException {
                result = fileLoader.jsonFromFileSystemWithClasspathFallback(jarPath, testFilePath);
            }

            @Test
            @DisplayName("should return empty optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("csvFromFileSystem")
    class CsvFromFileSystem {
        List<MyFileEntryDto> result;


        @Nested
        @DisplayName("with existing file")
        class WithExistingFile {

            @Nested
            @DisplayName("and correct schema")
            class AndCorrectSchema {
                private static final String sourceCsvString = """
                    1,Plains,LastName
                    2,Mountains,LastName
                    3,Hills,LastName
                    4,Desert,LastName
                    """;

                @BeforeEach
                void setup() throws IOException {
                    val testFilePath = new FilePath<>(testPath.resolve("FileLoaderTest.csv"), MyFileEntryDto.class);
                    val finalPath = jarPath.resolve(testFilePath.getPath());
                    Files.createDirectories(finalPath.getParent());
                    Files.writeString(finalPath, sourceCsvString);
                    result = fileLoader.csvFromFileSystem(jarPath, testFilePath, MyFileEntryDto.getSchema());
                }

                @Test
                @DisplayName("should return List with correct size")
                void shouldReturnOptionalOfList() {
                    assertThat(result).hasSize(4);
                }

                @Test
                @DisplayName("should have correct id")
                void shouldHaveCorrectId() {
                    assertThat(result.getFirst().getId()).isEqualTo(1);
                    assertThat(result.get(1).getId()).isEqualTo(2);
                    assertThat(result.get(2).getId()).isEqualTo(3);
                    assertThat(result.get(3).getId()).isEqualTo(4);
                }

                @Test
                @DisplayName("should have correct name")
                void shouldHaveCorrectName() {
                    assertThat(result.getFirst().getName()).isEqualTo("Plains");
                    assertThat(result.get(1).getName()).isEqualTo("Mountains");
                    assertThat(result.get(2).getName()).isEqualTo("Hills");
                    assertThat(result.get(3).getName()).isEqualTo("Desert");
                }

                @Test
                @DisplayName("should have correct lastname")
                void shouldHaveCorrectLastname() {
                    assertThat(result.getFirst().getLastname()).isEqualTo("LastName");
                    assertThat(result.get(1).getLastname()).isEqualTo("LastName");
                    assertThat(result.get(2).getLastname()).isEqualTo("LastName");
                    assertThat(result.get(3).getLastname()).isEqualTo("LastName");
                }
            }

            @Nested
            @DisplayName("and incorrect schema")
            class AndIncorrectSchema {
                ThrowableAssertAlternative<Exception> result;

                private static final String sourceCsvString = """
                    HelloWorld,1902391
                    """;

                Path finalPath;

                @BeforeEach
                void setup() throws IOException {
                    val testFilePath = new FilePath<>(testPath.resolve("FileLoaderTest.csv"), MyFileEntryDto.class);
                    finalPath = jarPath.resolve(testFilePath.getPath());
                    Files.createDirectories(finalPath.getParent());
                    Files.writeString(finalPath, sourceCsvString);
                    result = assertThatException().isThrownBy(() -> fileLoader.csvFromFileSystem(jarPath, testFilePath, MyFileEntryDto.getSchema()));
                }

                @Test
                @DisplayName("should have correct type")
                void shouldHaveCorrectType() {
                    assertThat(result.isExactlyInstanceOf(FileLoadingException.class));
                }

                @Test
                @DisplayName("should have correct message")
                void shouldHaveCorrectMessage() {
                    assertThat(result.withMessage(FileLoadingException.invalidStructure(finalPath, null).getMessage()));
                }
            }
        }

        @Nested
        @DisplayName("with non-existing file")
        class WithNonExistingFile {

            static final FilePath testFilePath = new FilePath(nonexistingPath.resolve("SomeNonExistingFile.json"), MyFileEntryDto.class);

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve(testFilePath.getPath());
                Files.deleteIfExists(finalPath);
                result = fileLoader.csvFromFileSystem(finalPath, testFilePath, MyFileEntryDto.getSchema());
            }

            @Test
            @DisplayName("should return empty list")
            void shouldReturnEmptyList() {
                assertThat(result).isEmpty();
            }
        }
    }

    @Getter
    @Setter
    private static class MyFileEntryDto implements FileDto {
        private final Integer id;
        private final String name;
        private final String lastname;

        @JsonCreator
        public MyFileEntryDto(
                @JsonProperty("id") Integer id,
                @JsonProperty("name") String name,
                @JsonProperty("lastname") String lastname) {
            this.id = id;
            this.name = name;
            this.lastname = lastname;
        }

        public static CsvSchema getSchema() {
            return CsvSchema.builder()
                    .addNumberColumn("id")
                    .addColumn("name")
                    .addColumn("lastname")
                    .build()
                    .withComments()
                    .withoutHeader();
        }
    }

    private static record MyFileContentDto(List<MyFileEntryDto> data) implements FileDto {
    }
}