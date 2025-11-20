package eu.nyarie.core.io.appdata;

import eu.nyarie.core.util.abstraction.AbstractIoTest;
import lombok.val;
import org.junit.jupiter.api.Test;

class AppDataPathResolverTest extends AbstractIoTest {

    @Test
    void run() {
        val resolver = new AppDataPathResolver();
        resolver.determineAppDataDirectoryPath();
    }
}