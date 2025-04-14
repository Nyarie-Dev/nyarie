package eu.nyarie.core;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoreClassTest {

    @Test
    void shouldCorrectlyAccessApiClasses() {
        val coreClass = new CoreClass();
        val returnedValue =  coreClass.accessApi();

        assertThat(returnedValue).isEqualTo("Bye World!");
    }
}
