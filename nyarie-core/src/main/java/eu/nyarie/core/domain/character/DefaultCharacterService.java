package eu.nyarie.core.domain.character;

import eu.nyarie.core.api.commands.character.CharacterCommands;
import eu.nyarie.core.api.data.character.CharacterData;
import eu.nyarie.core.api.data.character.CreateCharacterData;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.persistence.CharacterRepository;
import eu.nyarie.core.api.service.CharacterService;
import lombok.Getter;

@Getter
public class DefaultCharacterService extends CharacterService {


    public DefaultCharacterService(NyarieEngine engine) {
        super(engine, engine.getCharacterRepository());
    }

    @Override
    public CharacterCommands createCharacter(CreateCharacterData createCharacterData) {
        return new Character(engine.getFactionRepository(), createCharacterData);
    }

    @Override
    public CharacterCommands getCharacter(CharacterData characterData) {
        return new Character(characterData);
    }
}
