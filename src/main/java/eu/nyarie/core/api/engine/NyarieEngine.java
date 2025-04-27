package eu.nyarie.core.api.engine;

import eu.nyarie.core.api.service.CharacterService;
import eu.nyarie.core.api.service.FactionService;

public interface NyarieEngine {

    void start();
    void stop();

    FactionService getFactionService();
    CharacterService getCharacterService();
}
