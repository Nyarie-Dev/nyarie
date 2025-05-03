package eu.nyarie.core.domain.faction;

import eu.nyarie.core.api.commands.faction.FactionCharacterCommands;
import eu.nyarie.core.api.commands.faction.FactionCommands;
import eu.nyarie.core.api.data.faction.FactionCharacterData;
import eu.nyarie.core.api.data.faction.FactionData;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.persistence.FactionRepository;
import eu.nyarie.core.api.service.FactionService;
import lombok.Getter;
import lombok.val;

@Getter
public class DefaultFactionService implements FactionService {

    private final NyarieEngine engine;
    private final FactionRepository factionRepository;

    public DefaultFactionService(NyarieEngine engine) {
        this.engine = engine;
        this.factionRepository = engine.getFactionRepository();
    }

    @Override
    public FactionCommands getFaction(FactionData factionData) {
        return new Faction(factionData);
    }

    @Override
    public FactionCharacterCommands getFactionCharacter(FactionCharacterData factionCharacterData) {
        val characterService = engine.getCharacterService();
        val characters = factionCharacterData.getCharacters().stream()
                .map(characterService::getCharacter)
                .toList();

        return new FactionCharacter(factionCharacterData, characters);
    }
}
