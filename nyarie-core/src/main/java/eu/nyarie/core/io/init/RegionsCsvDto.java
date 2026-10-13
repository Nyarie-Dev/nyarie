package eu.nyarie.core.io.init;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import eu.nyarie.core.io.FileDto;

/// A single terrain type entry in the `terrain-types.csv`. Gets its data written
/// to the final `map.json` file after map initialization is done.
public record RegionsCsvDto(
        @JsonProperty(value = "id", required = true) Integer id,
        @JsonProperty(value = "red", required = true) Integer red,
        @JsonProperty(value = "green", required = true) Integer green,
        @JsonProperty(value = "blue", required = true) Integer blue,
        @JsonProperty(value = "name", required = true) String name,
        @JsonProperty(value = "terrainTypeId", required = true) Integer terrainTypeId
) implements FileDto {

    public static CsvSchema getSchema() {
        return CsvSchema.builder()
                .addNumberColumn("id")
                .addNumberColumn("red")
                .addNumberColumn("green")
                .addNumberColumn("blue")
                .addColumn("name")
                .addNumberColumn("terrainTypeId")
                .build()
                .withoutHeader()
                .withAllowComments(true);
    }

}
