package eu.nyarie.core.api.engine;

import eu.nyarie.core.api.service.CharacterService;
import eu.nyarie.core.api.service.FactionService;

public abstract class NyarieEngine {

    protected final EnginePersistenceContext persistenceContext;
    protected FactionService factionService;
    protected CharacterService characterService;

    public NyarieEngine(EnginePersistenceContext persistenceContext) {
        this.persistenceContext = persistenceContext;
    }

    public abstract void start();
    public abstract void stop();

    public FactionService getFactionService() {
        return factionService;
    }

    public CharacterService getCharacterService() {
        return characterService;
    }
}
