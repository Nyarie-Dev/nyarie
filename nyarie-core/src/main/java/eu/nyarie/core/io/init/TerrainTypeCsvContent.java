package eu.nyarie.core.io.init;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Getter;

/// A serialization DTO representing the content of the `terrain-types.csv`. Gets its data written
/// to the final `map.json` file after map initialization is done.
public class TerrainTypeCsvContent extends InitFileContentDto<TerrainTypeCsvContent.TerrainTypeCsvEntry> {

    public TerrainTypeCsvContent(TerrainTypeCsvEntry... data) {
        super(data);
    }

    public static CsvSchema getSchema() {
        return CsvSchema.builder()
                .addNumberColumn("id")
                .addColumn("name")
                .addNumberColumn("moveIntoDuration")
                .build()
                .withoutHeader()
                .withAllowComments(true);
    }

    /// A single terrain type entry in the `terrain-types.csv`. Gets its data written
    /// to the final `map.json` file after map initialization is done.
    @Getter
    public static class TerrainTypeCsvEntry extends InitFileEntryDto {
        private final Integer id;
        private final String name;
        private final Integer moveIntoDuration;

        @JsonCreator
        public TerrainTypeCsvEntry(
                @JsonProperty(value = "id", required = true) Integer id,
                @JsonProperty(value = "id", required = true) String name,
                @JsonProperty(value = "id", required = true) Integer moveIntoDuration) {
            this.id = id;
            this.name = name;
            this.moveIntoDuration = moveIntoDuration;
        }
    }
}
