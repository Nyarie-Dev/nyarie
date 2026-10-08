package eu.nyarie.core.io;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import eu.nyarie.core.util.io.FileSystemUtils;
import eu.nyarie.core.util.serialization.NyarieObjectMappers;
import lombok.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@SuppressWarnings("ALL")
class FileLoaderTest extends AbstractIoTest {

    private static final Path jarPath = FileSystemUtils.jarPath(FileLoader.class);
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

            static final FilePath testFilePath = new FilePath<>(Path.of("test", "io", "FileLoaderTest.json"), MyFileContentDto.class);
            Optional<MyFileContentDto> result;

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve(testFilePath.getPath());
                Files.createDirectories(finalPath.getParent());
                val jsonString = new NyarieObjectMappers().getJsonMapperInstance()
                        .writeValueAsString(new MyFileContentDto(new MyFileEntryDto(1, "Belegorn", "Arnorion")));
                Files.writeString(finalPath, jsonString);
                result = fileLoader.fromFileSystem(jarPath, testFilePath);
            }

            @Test
            @DisplayName("should return optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isPresent();
            }

            @Test
            @DisplayName("should have correct id")
            void shouldHaveCorrectEntriesInList() {
                assertThat(result.orElseThrow().getData().getFirst().getId()).isEqualTo(1);
            }
        }

        @Nested
        @DisplayName("with non-existing asset file")
        class WithNonExistingAssetFile {

            static final FilePath testFilePath = new FilePath<>(Path.of("some", "nonexisting", "path", "SomeNonExistingFile.json"), MyFileContentDto.class);
            Optional<MyFileContentDto> result;

            @BeforeEach
            void setup() throws IOException {
                val finalPath = jarPath.resolve(testFilePath.getPath());
                Files.deleteIfExists(finalPath);
                result = fileLoader.fromFileSystem(jarPath, testFilePath);
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

            static final FilePath testFilePath = new FilePath<>(Path.of("test", "io", "FileLoaderTest.json"), MyFileContentDto.class);
            Optional<MyFileContentDto> result;

            @BeforeEach
            void setup() throws IOException {
                result = fileLoader.fromFileSystemWithClasspathFallback(jarPath, testFilePath);
            }

            @Test
            @DisplayName("should return optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isPresent();
            }

            @Test
            @DisplayName("should have correct id")
            void shouldHaveCorrectEntriesInList() {
                assertThat(result.orElseThrow().getData().getFirst().getId()).isEqualTo(1);
            }
        }

        @Nested
        @DisplayName("with non-existing asset file")
        class WithNonExistingAssetFile {

            static final FilePath testFilePath = new FilePath<>(Path.of("some", "nonexisting", "path", "SomeNonExistingFile.json"), MyFileContentDto.class);
            Optional<MyFileContentDto> result;

            @BeforeEach
            void setup() throws IOException {
                result = fileLoader.fromFileSystemWithClasspathFallback(jarPath, testFilePath);
            }

            @Test
            @DisplayName("should return empty optional")
            void shouldReturnOptionalOfList() {
                assertThat(result).isEmpty();
            }
        }
    }

    @Getter
    private static class MyFileEntryDto extends FileEntryDto {
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
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class MyFileContentDto extends FileContentDto<MyFileEntryDto> {
        public MyFileContentDto(MyFileEntryDto... data) {
            super(data);
        }
    }
}