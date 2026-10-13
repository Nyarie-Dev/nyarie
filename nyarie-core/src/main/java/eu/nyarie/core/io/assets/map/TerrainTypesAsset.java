package eu.nyarie.core.io.assets.map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.nyarie.core.io.FileDto;

import java.time.Duration;
import java.util.List;

public record TerrainTypesAsset(List<TerrainTypeAsset> data) implements FileDto {

    public record TerrainTypeAsset(
            @JsonProperty(value = "id", required = true) String id,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "movementDuration", required = true) Duration movementDuration
    ) implements FileDto { }
}
