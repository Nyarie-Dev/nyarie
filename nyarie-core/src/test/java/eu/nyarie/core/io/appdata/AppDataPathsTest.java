package eu.nyarie.core.io.appdata;

import eu.nyarie.core.util.abstraction.AbstractIoTest;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Set;

class AppDataPathsTest extends AbstractIoTest {

    @Test
    @DisplayName("ROOT should be equal to AppDataPathResolver#determineAppDataDirectoryPath()")
    void rootShouldBeEqual() {
        val resolver = new AppDataPathResolver();
        assertThat(AppDataPaths.ROOT).isEqualTo(resolver.determineAppDataDirectoryPath());
    }

    @Test
    @DisplayName("MODS should be equal to ROOT + '/mods'")
    void modsShouldBeEqual() {
        val root = AppDataPaths.ROOT;
        assertThat(AppDataPaths.MODS).isEqualTo(root.resolve("mods"));
    }

    @Nested
    @DisplayName("getSubpaths() method")
    class GetSubpaths {

        final Set<Path> result = AppDataPaths.getSubpaths();

        @Test
        @DisplayName("result should have size 1")
        void shouldReturnListWithSize1() {
            assertThat(result).hasSize(1);
        }

        @Test
        @DisplayName("result should contain correct content")
        void shouldContainCorrectContent() {
            assertThat(result).containsExactly(
                    AppDataPaths.MODS
            );
        }
    }
}