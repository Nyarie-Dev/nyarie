package eu.nyarie.core.io.assets.map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegionAsset(
            @JsonProperty(value = "id", required = true) String id,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "terrainType", required = true) String terrainType) {
}
