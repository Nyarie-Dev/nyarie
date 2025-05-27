package eu.nyarie.core;

import eu.nyarie.core.api.engine.EnginePersistenceContext;
import eu.nyarie.core.api.persistence.CharacterRepository;
import eu.nyarie.core.api.persistence.FactionRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoreClassTest {

    private final FactionRepository factionRepository = Mockito.mock(FactionRepository.class);
    private final CharacterRepository characterRepository = Mockito.mock(CharacterRepository.class);
    private final EnginePersistenceContext persistenceContext = EnginePersistenceContext.from(characterRepository, factionRepository);

    @Test
    void shouldCorrectlyAccessApiClasses() {
        val coreClass = new CoreClass();
        val returnedValue =  coreClass.accessApi(persistenceContext);

        assertThat(returnedValue).isEqualTo("Bye World!");
    }
}
