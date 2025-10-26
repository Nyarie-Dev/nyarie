package eu.nyarie.core.io.assets.map;

import eu.nyarie.core.domain.constant.map.Region;
import eu.nyarie.core.io.assets.AssetDto;
import eu.nyarie.core.io.assets.AssetFileDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionsAsset extends AssetFileDto<RegionsAsset.RegionAsset> {

    public RegionsAsset(RegionAsset... data) {
        super(data);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegionAsset extends AssetDto<Region> {
        private String id;
        private String name;
        private String terrainType;
    }
}
