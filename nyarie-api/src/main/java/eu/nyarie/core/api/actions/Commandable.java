package eu.nyarie.core.api.actions;

import eu.nyarie.core.api.data.character.CharacterData;
import eu.nyarie.core.api.data.army.ArmyData;

import java.util.UUID;

/// Defines entities that can be commanded by another entity.
///
/// The most straight forward example would be an [army][ArmyData] being commanded by a [character][CharacterData].
public interface Commandable {
    UUID getCommanderId();
    void setCommanderId(UUID commanderId);
}
