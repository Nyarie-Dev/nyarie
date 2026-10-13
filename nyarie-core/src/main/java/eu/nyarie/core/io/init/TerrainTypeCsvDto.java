package eu.nyarie.core.io.init;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import eu.nyarie.core.io.FileDto;

/// A single terrain type entry in the `terrain-types.csv`. Gets its data written
/// to the final `map.json` file after map initialization is done.
public record TerrainTypeCsvDto(
        @JsonProperty(value = "id", required = true) Integer id,
        @JsonProperty(value = "name", required = true) String name,
        @JsonProperty(value = "moveIntoDuration", required = true) Integer moveIntoDuration
) implements FileDto {

    public static CsvSchema getSchema() {
        return CsvSchema.builder()
                .addNumberColumn("id")
                .addColumn("name")
                .addNumberColumn("moveIntoDuration")
                .build()
                .withoutHeader()
                .withAllowComments(true);
    }

}
