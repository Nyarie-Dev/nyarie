package eu.nyarie.core.io.installation;

import eu.nyarie.core.io.assets.exception.AssetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.jupiter.api.*;
import util.abstraction.AbstractIoTest;

import java.io.FileNotFoundException;
import java.nio.file.Path;

@Slf4j
class InstallationPathResolverTest extends AbstractIoTest {

    private static final InstallationPathResolver installationPathResolver = new InstallationPathResolver();

    @Nested
    @DisplayName("when calling determineInstallationDirectoryPath()")
    class DetermineInstallationDirectoryPathTest {

        final Path testClassPath = Path.of(InstallationPathResolver.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        abstract static class SuccessfulInstallationPathResolverTest {

            Path result;

            @BeforeEach
            void getResult() {
                result = installationPathResolver.determineInstallationDirectoryPath();
            }

            @Test
            @DisplayName("should return a directory path")
            void shouldReturnDirectory() {
                assertThat(result).isDirectory();
            }

            @Test
            @DisplayName("should return existing path")
            void shouldReturnExistingPath() {
                assertThat(result).isDirectory();
            }

            @Test
            @DisplayName("should return absolute path")
            void shouldReturnAbsolutePath() {
                assertThat(result).isAbsolute();
            }
        }

        @Nested
        @DisplayName("with no additional settings")
        class WithNoAdditionalSettings extends SuccessfulInstallationPathResolverTest {
            private final Path expected = testClassPath;

            @Test
            @DisplayName("should return path of jar")
            void shouldReturnPathOfJar() {
                assertThat(result).isEqualTo(expected);
            }
        }

        @Nested
        @DisplayName("with system property set to")
        class WithSystemPropertySetTo {

            Path expected;

            @AfterEach
            void unsetProperty() {
                System.clearProperty("eu.nyarie.core.installation.path");
            }

            @Nested
            @DisplayName("existing absolute path (root)")
            class ExistingAbsolutePath extends SuccessfulInstallationPathResolverTest {

                @BeforeEach
                void setSystemProperty() {
                    expected = testClassPath.getRoot();
                    System.setProperty("eu.nyarie.core.installation.path", expected.toString());
                    getResult();
                }

                @Test
                @DisplayName("should return configured path (root)")
                void shouldReturnConfiguredPath() {
                    assertThat(result).isEqualTo(expected);
                }
            }

            @Nested
            @DisplayName("non-existing absolute path")
            class NonExistingAbsolutePath {

                @BeforeEach
                void setSystemProperty() {
                    expected = Path.of("this", "hopefully", "does", "not", "exist");
                    System.setProperty("eu.nyarie.core.installation.path", expected.toString());
                }

                @Nested
                @DisplayName("Should throw Exception")
                class ShouldThrowException {

                    private ThrowableAssertAlternative<Exception> exception;

                    @BeforeEach
                    void setSystemProperty() {
                        expected = Path.of("this", "hopefully", "does", "not", "exist");
                        System.setProperty("eu.nyarie.core.installation.path", expected.toString());
                        exception = assertThatException().isThrownBy(installationPathResolver::determineInstallationDirectoryPath);
                    }


                    @Test
                    @DisplayName("with type ConstDataNotFoundException")
                    void withTypeConstDataNotFoundException() {
                        exception.isExactlyInstanceOf(AssetNotFoundException.class);
                    }

                    @Test
                    @DisplayName("with correct message")
                    void withCorrectMessage() {
                        exception.withMessage(AssetNotFoundException.assetDirectoryNotFound(expected.toString(), new FileNotFoundException()).getMessage());
                    }

                }
            }

        }
    }
}