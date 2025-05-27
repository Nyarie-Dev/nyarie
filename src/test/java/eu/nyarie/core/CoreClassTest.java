package eu.nyarie.core;

import lombok.val;
import org.junit.jupiter.api.Test;
import util.MockPersistenceContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoreClassTest {

    private static final MockPersistenceContext persistenceContext = new MockPersistenceContext();

    @Test
    void shouldCorrectlyAccessApiClasses() {
        val coreClass = new CoreClass();
        val returnedValue =  coreClass.accessApi(persistenceContext);

        assertThat(returnedValue).isEqualTo("Bye World!");
    }
}
