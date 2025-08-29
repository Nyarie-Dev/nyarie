package eu.nyarie.core.domain.faction;

import eu.nyarie.core.api.data.faction.CreateFactionData;
import eu.nyarie.core.api.data.faction.FactionData;
import eu.nyarie.core.domain.Identity;
import eu.nyarie.core.api.commands.faction.FactionCommands;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Faction extends Identity implements FactionCommands {

    protected String name;
    protected String description;
    protected String motto;

    protected UUID leaderCharacterId;

    protected final UUID originFactionId;

    protected Faction(CreateFactionData factionData) {
        super(UUID.randomUUID());
        this.name = factionData.getName();
        this.description = factionData.getDescription();
        this.motto = factionData.getMotto();
        this.leaderCharacterId = factionData.getLeaderCharacterId();
        this.originFactionId = factionData.getOriginFactionId();
    }

    @Override
    public void addToStockpile(int amount) {

    }

    @Override
    public void takeFromStockpile(int amount) {

    }
}
