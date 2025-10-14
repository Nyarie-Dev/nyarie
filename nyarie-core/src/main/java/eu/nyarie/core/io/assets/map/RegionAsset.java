package eu.nyarie.core.io.assets.map;

import eu.nyarie.core.io.assets.Asset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegionAsset extends Asset {

    private String id;
    private String name;
    private String terrainType;

}
