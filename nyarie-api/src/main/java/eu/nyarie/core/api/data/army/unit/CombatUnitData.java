package eu.nyarie.core.api.data.army.unit;

import eu.nyarie.core.api.actions.Commandable;
import eu.nyarie.core.api.actions.Moveable;
import eu.nyarie.core.api.data.Identifiable;

/// Defines a unit capable of engaging in combat. A combat unit can be of a certain [type][CombatUnitTypeData],
/// which defines its traits.
public interface CombatUnitData extends Identifiable, Moveable, Commandable {

    CombatUnitTypeData getType();
    int getHealth();
}
