package eu.nyarie.core.api.actions;

import eu.nyarie.core.api.data.map.RegionData;

public interface Moveable {

    RegionData getCurrentRegion();

    void moveTo(RegionData region);

}
