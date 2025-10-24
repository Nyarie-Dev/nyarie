package eu.nyarie.core;

import eu.nyarie.core.util.DurationUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import eu.nyarie.core.util.MockPersistenceContext;

import java.io.File;
import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class CoreClassTest {

    private static final MockPersistenceContext persistenceContext = new MockPersistenceContext();

    @Test
    void shouldCorrectlyAccessApiClasses() {
        val coreClass = new CoreClass();
        val returnedValue =  coreClass.accessApi(persistenceContext);

        assertThat(returnedValue).isEqualTo("Bye World!");

        val file = new File(".");
        log.error(file.getAbsolutePath());
    }

    @Test
    void runDurationUtils() {
        val duration = Duration.ofMillis(0);

        log.error(DurationUtils.toReadableString(duration));
    }
}
