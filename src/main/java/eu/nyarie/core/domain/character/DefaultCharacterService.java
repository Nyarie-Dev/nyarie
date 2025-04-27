package eu.nyarie.core.domain.character;

import eu.nyarie.core.api.commands.character.CharacterCommands;
import eu.nyarie.core.api.data.character.CharacterData;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.persistence.CharacterRepository;
import eu.nyarie.core.api.service.CharacterService;
import lombok.Getter;

@Getter
public class DefaultCharacterService extends CharacterService {


    public DefaultCharacterService(NyarieEngine engine, CharacterRepository characterRepository) {
        super(engine, characterRepository);
    }

    @Override
    public CharacterCommands createCharacter(CharacterData characterData) {
        return new Character(this.characterRepository, characterData);
    }
}
