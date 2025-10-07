package eu.nyarie.core;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import eu.nyarie.core.util.MockPersistenceContext;

import java.io.File;

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
}
