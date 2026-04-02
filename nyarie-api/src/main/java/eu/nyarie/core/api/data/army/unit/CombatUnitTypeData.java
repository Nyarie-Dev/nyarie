package eu.nyarie.core.api.data.army.unit;

/// Defines the type of a [combat unit][CombatUnitData]. The combat unit type is unique, meaning
/// that each type only exists once. This is because the type acts like a "blueprint" while the
/// [combat unit][CombatUnitData] itself is like an "instance" of that type.
///
/// A simple example would be the unit type "Archer": the type only exists once, while
/// there can be an infinite number of [combat units][CombatUnitData] of type "Archer" on the map.
public interface CombatUnitTypeData {
    String getName();
    int getTokenCost();
    int getMaxHealth();
}
