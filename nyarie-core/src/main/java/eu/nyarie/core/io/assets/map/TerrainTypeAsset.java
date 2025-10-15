package eu.nyarie.core.io.assets.map;

import eu.nyarie.core.domain.constant.map.TerrainType;
import eu.nyarie.core.io.assets.AssetDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TerrainTypeAsset extends AssetDto<TerrainType> {

    private String id;
    private String name;
    private Duration getMovementDuration;
}
