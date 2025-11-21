package eu.nyarie.core.io.appdata;

import eu.nyarie.core.io.appdata.exception.AppDataDirectoryException;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import eu.nyarie.core.util.io.FileSystemUtils;
import lombok.val;
import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Optional;

class AppDataPathResolverTest extends AbstractIoTest {


    private static final AppDataPathConfigReader spyConfigReader = Mockito.spy(new AppDataPathConfigReader());
    private static final AppDataPathResolver appDataPathResolver = new AppDataPathResolver(spyConfigReader);

    @Nested
    @DisplayName("when calling determineAppDataDirectoryPath()")
    class DetermineAppDataDirectoryPathTest {

        final Path testClassPath = FileSystemUtils.jarPath(AppDataPathResolver.class);

        DetermineAppDataDirectoryPathTest() throws URISyntaxException {
        }

        abstract static class SuccessfulAppDataPathResolverTest {

            Path result;

            @BeforeEach
            void getResult() {
                result = appDataPathResolver.determineAppDataDirectoryPath();
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
        class WithNoAdditionalSettings extends AppDataPathResolverTest.DetermineAppDataDirectoryPathTest.SuccessfulAppDataPathResolverTest {
            private final Path expected = expectedPath();

            @Test
            @DisplayName("should return path of jar")
            void shouldReturnPathOfJar() {
                assertThat(result).isEqualTo(expected);
            }

            private static Path expectedPath() {
                val os = System.getProperty("os.name").toLowerCase();
                val userHome = System.getProperty("user.home");

                if(os.contains("windows"))
                    return Path.of(userHome, "AppData", "Roaming");
                if(os.contains("mac"))
                    return Path.of(userHome, "Library", "Application Support");

                return Path.of(userHome, ".local", "share");
            }
        }

        @Nested
        @DisplayName("with system property set to")
        class WithSystemPropertySetTo {

            Path expected;

            @AfterEach
            void unsetProperty() {
                System.clearProperty("eu.nyarie.core.appdata.path");
            }

            @Nested
            @DisplayName("existing absolute path (root)")
            class ExistingAbsolutePath extends AppDataPathResolverTest.DetermineAppDataDirectoryPathTest.SuccessfulAppDataPathResolverTest {

                @BeforeEach
                void setSystemProperty() {
                    expected = testClassPath.getRoot();
                    System.setProperty("eu.nyarie.core.appdata.path", expected.toString());
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
                    expected = Path.of(FileSystemUtils.getRoot(), "this", "hopefully", "does", "not", "exist");
                    System.setProperty("eu.nyarie.core.appdata.path", expected.toString());
                }

                @Nested
                @DisplayName("Should throw Exception")
                class ShouldThrowException {

                    private ThrowableAssertAlternative<Exception> exception;

                    @BeforeEach
                    void setSystemProperty() {
                        exception = assertThatException().isThrownBy(appDataPathResolver::determineAppDataDirectoryPath);
                    }


                    @Test
                    @DisplayName("with type AppDataDirectoryException")
                    void withTypeAppDataDirectoryException() {
                        exception.isExactlyInstanceOf(AppDataDirectoryException.class);
                    }

                    @Test
                    @DisplayName("with correct message")
                    void withCorrectMessage() {
                        exception.withMessage(AppDataDirectoryException.directoryNotFound(expected.toString(), new FileNotFoundException()).getMessage());
                    }

                }
            }

            @Nested
            @DisplayName("relative path")
            class RelativePath {

                @BeforeEach
                void setSystemProperty() {
                    expected = Path.of("..", "..");
                    System.setProperty("eu.nyarie.core.appdata.path", expected.toString());
                }

                @Nested
                @DisplayName("Should throw Exception")
                class ShouldThrowException {

                    private ThrowableAssertAlternative<Exception> exception;

                    @BeforeEach
                    void setSystemProperty() {
                        exception = assertThatException().isThrownBy(appDataPathResolver::determineAppDataDirectoryPath);
                    }


                    @Test
                    @DisplayName("with type AppDataDirectoryException")
                    void withTypeAppDataDirectoryException() {
                        exception.isExactlyInstanceOf(AppDataDirectoryException.class);
                    }

                    @Test
                    @DisplayName("with correct message")
                    void withCorrectMessage() {
                        exception.withMessage(AppDataDirectoryException.configuredPathNotAbsolute(expected.toString()).getMessage());
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
            class ExistingAbsolutePath extends AppDataPathResolverTest.DetermineAppDataDirectoryPathTest.SuccessfulAppDataPathResolverTest {

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
                    expected = Path.of(FileSystemUtils.getRoot(), "this", "hopefully", "does", "not", "exist");
                    Mockito.when(spyConfigReader.getEnvVarValue()).thenReturn(Optional.of(expected.toString()));
                }

                @Nested
                @DisplayName("Should throw Exception")
                class ShouldThrowException {

                    private ThrowableAssertAlternative<Exception> exception;

                    @BeforeEach
                    void setSystemProperty() {
                        exception = assertThatException().isThrownBy(appDataPathResolver::determineAppDataDirectoryPath);
                    }


                    @Test
                    @DisplayName("with type AppDataDirectoryException")
                    void withTypeAppDataDirectoryException() {
                        exception.isExactlyInstanceOf(AppDataDirectoryException.class);
                    }

                    @Test
                    @DisplayName("with correct message")
                    void withCorrectMessage() {
                        exception.withMessage(AppDataDirectoryException.directoryNotFound(expected.toString(), new FileNotFoundException()).getMessage());
                    }

                }
            }

        }
    }
}