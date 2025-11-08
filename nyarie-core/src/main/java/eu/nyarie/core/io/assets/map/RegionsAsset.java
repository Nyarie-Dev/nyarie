package eu.nyarie.core.io.assets.map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public static class RegionAsset extends AssetDto<Region> {
        private final String id;
        private final String name;
        private final String terrainType;

        @JsonCreator
        public RegionAsset(
                @JsonProperty(value = "id", required = true) String id,
                @JsonProperty(value = "name", required = true) String name,
                @JsonProperty(value = "terrainType", required = true) String terrainType) {
            this.id = id;
            this.name = name;
            this.terrainType = terrainType;
        }
    }
}
