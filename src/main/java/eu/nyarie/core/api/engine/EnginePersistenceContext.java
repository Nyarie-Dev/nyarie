package eu.nyarie.core.api.engine;

import eu.nyarie.core.api.persistence.CharacterRepository;
import eu.nyarie.core.api.persistence.FactionRepository;

public class EnginePersistenceContext {

    private final CharacterRepository characterRepository;
    private final FactionRepository factionRepository;

    protected EnginePersistenceContext(CharacterRepository characterRepository, FactionRepository factionRepository) {
        this.characterRepository = characterRepository;
        this.factionRepository = factionRepository;
    }

    public static EnginePersistenceContext from(
            CharacterRepository characterRepository,
            FactionRepository factionRepository
    ) {
        return new EnginePersistenceContext(characterRepository, factionRepository);
    }

    public CharacterRepository getCharacterRepository() {
        return characterRepository;
    }

    public FactionRepository getFactionRepository() {
        return factionRepository;
    }
}
