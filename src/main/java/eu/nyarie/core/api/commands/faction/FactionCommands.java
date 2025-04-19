package eu.nyarie.core.api.commands.faction;

import eu.nyarie.core.api.data.faction.FactionData;

public interface FactionCommands extends FactionData {

    void addToStockpile(int amount);
    void takeFromStockpile(int amount);
}
