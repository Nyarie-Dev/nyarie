package eu.nyarie.core.io.appdata;

import eu.nyarie.core.util.abstraction.AbstractIoTest;
import eu.nyarie.core.util.io.FileSystemUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Path;

class AppDataDirectoryTest extends AbstractIoTest {

    @Nested
    @DisplayName("Constructor")
    class Constructor {

        private static AppDataDirectory appDataDirectory;
        private static final String propertyName = "eu.nyarie.core.appdata.path";
        private static final Path testPath = FileSystemUtils.jarPath().resolve("appdata-test");

        @BeforeAll
        static void setUp() {
            System.setProperty(propertyName, testPath.toString());
            appDataDirectory = new AppDataDirectory();
        }

        @AfterAll
        static void removeProperty() throws IOException {
            System.clearProperty(propertyName);
            FileSystemUtils.deleteRecursively(testPath);
        }

        @Test
        @DisplayName("should create root folder")
        void shouldCreateRootFolder() {
            assertThat(appDataDirectory.getRootDirectory()).exists();
        }

        @Test
        @DisplayName("should create mods folder")
        void shouldCreateModsFolder() {
            assertThat(appDataDirectory.getModsDirectory()).exists();
        }

    }
}