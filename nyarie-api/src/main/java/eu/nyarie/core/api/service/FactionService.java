package eu.nyarie.core.api.service;

import eu.nyarie.core.api.commands.faction.FactionCharacterCommands;
import eu.nyarie.core.api.commands.faction.FactionCommands;
import eu.nyarie.core.api.data.faction.CreateFactionData;
import eu.nyarie.core.api.data.faction.FactionCharacterData;
import eu.nyarie.core.api.data.faction.FactionData;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.persistence.FactionRepository;

public abstract class FactionService implements NyarieService {

    protected final NyarieEngine engine;
    protected final FactionRepository factionRepository;

    public FactionService(NyarieEngine engine, FactionRepository factionRepository) {
        this.engine = engine;
        this.factionRepository = factionRepository;
    }

    @Override
    public NyarieEngine getEngine() {
        return engine;
    }

    public abstract FactionCommands createFaction(CreateFactionData createFactionData);
    public abstract FactionCommands getFaction(FactionData factionData);
    public abstract FactionCharacterCommands getFactionCharacter(FactionCharacterData characterData);
}
