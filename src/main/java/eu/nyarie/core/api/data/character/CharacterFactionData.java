package eu.nyarie.core.api.data.character;

import eu.nyarie.core.api.data.faction.FactionData;

public interface CharacterFactionData extends CharacterData {

    FactionData getFaction();
}
