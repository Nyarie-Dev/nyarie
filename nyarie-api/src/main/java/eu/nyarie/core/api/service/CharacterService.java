package eu.nyarie.core.api.service;

import eu.nyarie.core.api.commands.character.CharacterCommands;
import eu.nyarie.core.api.data.character.CharacterData;
import eu.nyarie.core.api.data.character.CreateCharacterData;
import eu.nyarie.core.api.engine.NyarieEngine;
import eu.nyarie.core.api.persistence.CharacterRepository;

public abstract class CharacterService implements NyarieService {

    protected final NyarieEngine engine;
    protected final CharacterRepository characterRepository;

    public CharacterService(NyarieEngine engine, CharacterRepository characterRepository) {
        this.engine = engine;
        this.characterRepository = characterRepository;
    }

    @Override
    public NyarieEngine getEngine() {
        return engine;
    }

    public abstract CharacterCommands createCharacter(CreateCharacterData createCharacterData);
    public abstract CharacterCommands getCharacter(CharacterData characterData);
}
