package eu.nyarie.core.io.appdata;

import eu.nyarie.core.util.abstraction.AbstractIoTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

class AppDataDirectoryLoaderTest extends AbstractIoTest {

    @Nested
    @DisplayName("Constructor")
    class Constructor {

        @Test
        @DisplayName("should call AppDataDirectory constructor")
        void shouldCallAppDataDirectoryConstructor() {
            try(MockedConstruction<AppDataDirectory> mockedConstruction = Mockito.mockConstruction(AppDataDirectory.class)) {

                new AppDataDirectoryLoader();
                assertThat(mockedConstruction.constructed().size()).isEqualTo(1);
            }
        }
    }
}