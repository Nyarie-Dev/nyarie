package eu.nyarie.core.io.installation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import util.abstraction.AbstractIoTest;

import java.nio.file.Path;

@Slf4j
class InstallationPathResolverTest extends AbstractIoTest {

    private static final InstallationPathResolver installationPathResolver = new InstallationPathResolver();

    @Nested
    @DisplayName("when calling determineInstallationDirectoryPath()")
    class DetermineInstallationDirectoryPathTest {

        final Path result = installationPathResolver.determineInstallationDirectoryPath();
        final Path testClassPath = Path.of(InstallationPathResolver.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        abstract class SuccessfulInstallationPathResolverTest {

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

            @BeforeEach
            void setSystemProperty() {
                expected = testClassPath.getRoot();
                System.setProperty("eu.nyarie.core.installation.path", expected.toString());
            }

            @AfterAll
            static void unsetProperty() {
                System.clearProperty("eu.nyarie.core.installation.path");
            }

            @Nested
            @DisplayName("existing absolute path (root)")
            class ExistingAbsolutePath extends SuccessfulInstallationPathResolverTest {

                @Test
                @DisplayName("should return configured path (root)")
                void shouldReturnConfiguredPath() {
                    log.info("Result: {}", result);
                    assertThat(result).isEqualTo(expected);
                }
            }

        }
    }
}