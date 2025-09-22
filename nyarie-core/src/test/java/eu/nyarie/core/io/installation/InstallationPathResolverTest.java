package eu.nyarie.core.io.installation;

import eu.nyarie.core.io.installation.exception.InstallationDirectoryException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import util.abstraction.AbstractIoTest;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
class InstallationPathResolverTest extends AbstractIoTest {

    private static final InstallationPathConfigReader spyConfigReader = Mockito.spy(new InstallationPathConfigReader());
    private static final InstallationPathResolver installationPathResolver = new InstallationPathResolver(spyConfigReader);

    @Nested
    @DisplayName("when calling determineInstallationDirectoryPath()")
    class DetermineInstallationDirectoryPathTest {

        final Path testClassPath = Path.of(InstallationPathResolver.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        DetermineInstallationDirectoryPathTest() throws URISyntaxException {
        }

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
                    expected = Path.of("/", "this", "hopefully", "does", "not", "exist");
                    System.setProperty("eu.nyarie.core.installation.path", expected.toString());
                }

                @Nested
                @DisplayName("Should throw Exception")
                class ShouldThrowException {

                    private ThrowableAssertAlternative<Exception> exception;

                    @BeforeEach
                    void setSystemProperty() {
                        exception = assertThatException().isThrownBy(installationPathResolver::determineInstallationDirectoryPath);
                    }


                    @Test
                    @DisplayName("with type InstallationDirectoryException")
                    void withTypeInstallationDirectoryException() {
                        exception.isExactlyInstanceOf(InstallationDirectoryException.class);
                    }

                    @Test
                    @DisplayName("with correct message")
                    void withCorrectMessage() {
                        exception.withMessage(InstallationDirectoryException.installationDirectoryNotFound(expected.toString(), new FileNotFoundException()).getMessage());
                    }

                }
            }

            @Nested
            @DisplayName("relative path")
            class RelativePath {

                @BeforeEach
                void setSystemProperty() {
                    expected = Path.of("..", "..");
                    System.setProperty("eu.nyarie.core.installation.path", expected.toString());
                }

                @Nested
                @DisplayName("Should throw Exception")
                class ShouldThrowException {

                    private ThrowableAssertAlternative<Exception> exception;

                    @BeforeEach
                    void setSystemProperty() {
                        exception = assertThatException().isThrownBy(installationPathResolver::determineInstallationDirectoryPath);
                    }


                    @Test
                    @DisplayName("with type InstallationDirectoryException")
                    void withTypeInstallationDirectoryException() {
                        exception.isExactlyInstanceOf(InstallationDirectoryException.class);
                    }

                    @Test
                    @DisplayName("with correct message")
                    void withCorrectMessage() {
                        exception.withMessage(InstallationDirectoryException.configuredPathNotAbsolute(expected.toString()).getMessage());
                    }

                }
            }

        }

        @Nested
        @DisplayName("with env var set to")
        class WithEnvVarSetTo {

            Path expected;

            @AfterEach
            void unsetEnv() {
                Mockito.when(spyConfigReader.getEnvVarValue()).thenCallRealMethod();
            }

            @Nested
            @DisplayName("existing absolute path (root)")
            class ExistingAbsolutePath extends SuccessfulInstallationPathResolverTest {

                @BeforeEach
                void setSystemProperty() {
                    expected = testClassPath.getRoot();
                    Mockito.when(spyConfigReader.getEnvVarValue()).thenReturn(Optional.of(expected.toString()));
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
                    expected = Path.of("/", "this", "hopefully", "does", "not", "exist");
                    Mockito.when(spyConfigReader.getEnvVarValue()).thenReturn(Optional.of(expected.toString()));
                }

                @Nested
                @DisplayName("Should throw Exception")
                class ShouldThrowException {

                    private ThrowableAssertAlternative<Exception> exception;

                    @BeforeEach
                    void setSystemProperty() {
                        exception = assertThatException().isThrownBy(installationPathResolver::determineInstallationDirectoryPath);
                    }


                    @Test
                    @DisplayName("with type InstallationDirectoryException")
                    void withTypeInstallationDirectoryException() {
                        exception.isExactlyInstanceOf(InstallationDirectoryException.class);
                    }

                    @Test
                    @DisplayName("with correct message")
                    void withCorrectMessage() {
                        exception.withMessage(InstallationDirectoryException.installationDirectoryNotFound(expected.toString(), new FileNotFoundException()).getMessage());
                    }

                }
            }

        }
    }
}