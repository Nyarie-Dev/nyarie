package eu.nyarie.core.io.appdata;

import eu.nyarie.core.io.appdata.exception.AppDataDirectoryException;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import eu.nyarie.core.util.io.FileSystemUtils;
import lombok.val;
import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

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
            @DisplayName("should return default path")
            void shouldReturnDefaultPath() {
                assertThat(result).isEqualTo(expected);
            }

            private static Path expectedPath() {
                val os = System.getProperty("os.name").toLowerCase();
                val userHome = System.getProperty("user.home");

                if(os.contains("windows"))
                    return Path.of(userHome, "AppData", "Roaming", "nyarie");
                if(os.contains("mac"))
                    return Path.of(userHome, "Library", "Application Support", "nyarie");

                return Path.of(userHome, ".local", "share", "nyarie");
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
            @DisplayName("absolute path")
            class AbsolutePath {
                @Nested
                @DisplayName("pointing to directory (root)")
                class PointingToDirectory extends AppDataPathResolverTest.DetermineAppDataDirectoryPathTest.SuccessfulAppDataPathResolverTest {

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
            @DisplayName("absolute path")
            class AbsolutePath {
                
                @Nested
                @DisplayName("pointing to directory (root)")
                class PointingToDirectory extends AppDataPathResolverTest.DetermineAppDataDirectoryPathTest.SuccessfulAppDataPathResolverTest {

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
                @DisplayName("pointing to file")
                class PointingToFile {

                    @Nested
                    @DisplayName("Should throw Exception")
                    class ShouldThrowException {

                        private ThrowableAssertAlternative<Exception> exception;

                        @BeforeEach
                        void setSystemProperty() {
                            val classFilePath = AppDataPathResolver.class.getName().split("\\.");
                            val lastIndex = classFilePath.length - 1;
                            classFilePath[lastIndex] = classFilePath[lastIndex] + ".class";
                            expected = testClassPath.resolve("", classFilePath);
                            Mockito.when(spyConfigReader.getEnvVarValue()).thenReturn(Optional.of(expected.toString()));
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
                            exception.withMessage(AppDataDirectoryException.pathIsNoDirectory(expected.toString()).getMessage());
                        }
                    }
                }
            }
        }
    }
}