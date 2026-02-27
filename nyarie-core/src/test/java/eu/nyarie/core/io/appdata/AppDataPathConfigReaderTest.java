package eu.nyarie.core.io.appdata;

import eu.nyarie.core.util.abstraction.AbstractIoTest;
import lombok.val;
import org.junit.jupiter.api.*;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

class AppDataPathConfigReaderTest extends AbstractIoTest {

    private static final String PROPERTY_NAME = "eu.nyarie.core.appdata.path";
    private static final AppDataPathConfigReader configReader = new AppDataPathConfigReader();

    @Nested
    @DisplayName("when calling getSystemPropertyValue()")
    class GetSystemPropertyValue {

        @Test
        @DisplayName("should return Optional containing value when property is set")
        void shouldReturnOptionalContainingValue() {
            val expectedValue = "value";
            System.setProperty(PROPERTY_NAME, expectedValue);
            assertThat(configReader.getSystemPropertyValue())
                    .contains(expectedValue);
            System.clearProperty(PROPERTY_NAME);
        }

        @Test
        @DisplayName("should return empty Optional when property is not set")
        void shouldReturnEmptyOptional() {
            System.clearProperty(PROPERTY_NAME);
            assertThat(configReader.getSystemPropertyValue())
                    .isEmpty();
        }
    }
}