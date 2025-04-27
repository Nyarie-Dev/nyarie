package eu.nyarie.core.api.engine;

import eu.nyarie.core.api.persistence.CharacterRepository;
import eu.nyarie.core.api.persistence.FactionRepository;

public interface EnginePersistenceContext {
    
    CharacterRepository characterRepository();
    FactionRepository factionRepository();
}
