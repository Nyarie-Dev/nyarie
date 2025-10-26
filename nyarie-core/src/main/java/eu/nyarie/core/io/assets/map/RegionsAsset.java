package eu.nyarie.core.io.assets.map;

import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.io.assets.AssetDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegionsAsset extends AssetDto<Region> {

    private String id;
    private String name;
    private String terrainType;

}
