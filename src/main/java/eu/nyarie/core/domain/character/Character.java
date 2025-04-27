package eu.nyarie.core.domain.character;

import eu.nyarie.core.api.data.character.CharacterData;
import eu.nyarie.core.api.data.map.RegionData;
import eu.nyarie.core.api.persistence.CharacterRepository;
import eu.nyarie.core.domain.Identity;
import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.api.commands.character.CharacterCommands;
import lombok.Getter;
import lombok.val;

import java.util.UUID;

@Getter
public class Character extends Identity implements CharacterCommands {

    private String name;
    private String title;

    private float health;
    private String gear;

    private UUID factionId;
    private Region currentRegion;
    private UUID commandedGroupId;

    protected Character(CharacterRepository repository, CharacterData characterData) {
        val existingChars = repository.getCharactersByName(characterData.getName());

        if(existingChars.contains(characterData))
            throw new RuntimeException();

        super(characterData.getId());
        this.name = characterData.getName();
        this.title = characterData.getTitle();
        this.health = characterData.getHealth();
        this.gear = characterData.getGear();
        this.factionId = characterData.getFactionId();
        this.currentRegion = null; //TODO: needs to be set when we have our in-memory region querying
        this.commandedGroupId = characterData.getCommandedGroupId();
    }

    @Override
    public void moveTo(RegionData region) {

    }

    @Override
    public String getCurrentRegionId() {
        return this.currentRegion.getId();
    }
}
