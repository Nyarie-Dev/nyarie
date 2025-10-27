package eu.nyarie.core.io.assets.map;

import eu.nyarie.core.domain.constant.map.TerrainType;
import eu.nyarie.core.io.assets.AssetDto;
import eu.nyarie.core.io.assets.AssetFileDto;
import lombok.*;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TerrainTypesAsset extends AssetFileDto<TerrainTypesAsset.TerrainTypeAsset> {

    @Setter
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TerrainTypeAsset extends AssetDto<TerrainType> {
        private String id;
        private String name;
        private Duration movementDuration;
    }
}
