package eu.nyarie.core.io.assets.map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.nyarie.core.domain.constant.map.TerrainType;
import eu.nyarie.core.io.assets.AssetDto;
import eu.nyarie.core.io.assets.AssetFileDto;
import lombok.*;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TerrainTypesAsset extends AssetFileDto<TerrainTypesAsset.TerrainTypeAsset> {

    public TerrainTypesAsset(TerrainTypeAsset... data) {
        super(data);
    }

    @Setter
    @Getter
    public static class TerrainTypeAsset extends AssetDto<TerrainType> {
        private final String id;
        private final String name;
        private final Duration movementDuration;
        private String hello;

        @JsonCreator
        public TerrainTypeAsset(
                @JsonProperty(value = "id", required = true) String id,
                @JsonProperty(value = "name", required = true) String name,
                @JsonProperty(value = "movementDuration", required = true) Duration movementDuration) {
            this.id = id;
            this.name = name;
            this.movementDuration = movementDuration;
        }
    }
}
