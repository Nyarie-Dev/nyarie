package eu.nyarie.core.api.data.army.unit;

import eu.nyarie.core.api.actions.Commandable;
import eu.nyarie.core.api.data.Identifiable;

public interface CombatUnitData extends Identifiable {

    CombatUnitTypeData getType();
    int getHealth();
    Commandable getCommandedBy();
}
