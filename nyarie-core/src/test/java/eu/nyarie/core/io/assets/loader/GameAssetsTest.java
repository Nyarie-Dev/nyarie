package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.installation.InstallationDirectory;
import eu.nyarie.core.util.abstraction.AbstractIoTest;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Set;

class GameAssetsTest extends AbstractIoTest {

    @Test
    void runLoad() {
        val installationDirectory = new InstallationDirectory();
        GameAssets.load(installationDirectory, Set.of());
    }

}