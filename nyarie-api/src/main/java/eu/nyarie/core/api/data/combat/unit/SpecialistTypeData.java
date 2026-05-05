package eu.nyarie.core.api.data.combat.unit;

/// Defines the type of a [specialist][SpecialistData]. The specialist type is unique, meaning
/// that each type only exists once. This is because the type acts like a "blueprint" while the
/// [specialist][SpecialistData] itself is like an "instance" of that type.
///
/// A simple example would be the specialist type "Builder": the type only exists once, while
/// there can be an infinite number of [specialists][SpecialistData] of type "Builder" on the map.
public interface SpecialistTypeData {
    String getName();
    int getTokenCost();
    int getMaxHealth();
}
