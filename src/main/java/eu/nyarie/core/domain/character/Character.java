package eu.nyarie.core.domain.character;

import eu.nyarie.core.api.data.character.CharacterData;
import eu.nyarie.core.api.data.character.CreateCharacterData;
import eu.nyarie.core.api.data.map.RegionData;
import eu.nyarie.core.api.exception.data.FactionNotFoundException;
import eu.nyarie.core.api.persistence.FactionRepository;
import eu.nyarie.core.domain.Identity;
import eu.nyarie.core.domain.constant.Region;
import eu.nyarie.core.api.commands.character.CharacterCommands;
import lombok.Getter;

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

    protected Character(CharacterData characterData) {
        super(characterData.getId());
        this.name = characterData.getName();
        this.title = characterData.getTitle();
        this.health = characterData.getHealth();
        this.gear = characterData.getGear();
        this.factionId = characterData.getFactionId();
        this.currentRegion = null; //TODO: needs to be set when we have our in-memory region querying
        this.commandedGroupId = characterData.getCommandedGroupId();
    }

    public Character(FactionRepository factionRepository, CreateCharacterData data) {
        if(data.getFactionId() != null) {
            factionRepository.findById(data.getFactionId())
                    .orElseThrow(() -> FactionNotFoundException.factionWithIdNotFound(data.getFactionId()));
        }

        super(UUID.randomUUID());
        this.name = data.getName();
        this.title = data.getTitle();
        this.gear = data.getGear();
        this.factionId = data.getFactionId();

        this.health = 100f;
        this.currentRegion = null; //TODO Needs to be set to home region
        this.commandedGroupId = null;
    }

    @Override
    public void moveTo(RegionData region) {

    }

    @Override
    public String getCurrentRegionId() {
        //TODO: fix this when we have regions
        return "this.currentRegion.getId()";
    }
}
