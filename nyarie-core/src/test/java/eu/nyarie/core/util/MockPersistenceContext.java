package eu.nyarie.core.util;

import eu.nyarie.core.api.engine.EnginePersistenceContext;
import eu.nyarie.core.api.persistence.CharacterRepository;
import eu.nyarie.core.api.persistence.FactionRepository;
import lombok.val;
import org.mockito.Mockito;

/// An {@link EnginePersistenceContext} containing only mocked repositories
public class MockPersistenceContext extends EnginePersistenceContext {
    public MockPersistenceContext() {
        val characterRepository = Mockito.mock(CharacterRepository.class);
        val factionRepository = Mockito.mock(FactionRepository.class);

        super(characterRepository, factionRepository);
    }
}
