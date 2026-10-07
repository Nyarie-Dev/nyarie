package eu.nyarie.core.util.serialization;

import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/// Class that holds global singleton Jackson mappers such as [ObjectMapper] or [CsvMapper]
/// so that it can be reused across the codebase.
public class NyarieObjectMappers {

    private static ObjectMapper jsonMapper;
    private static CsvMapper csvMapper;

    /// Gets the singleton instance of the global [ObjectMapper].
    /// @return The global [ObjectMapper] singleton.
    public ObjectMapper getJsonMapperInstance() {
        if(jsonMapper != null)
            return jsonMapper;

        jsonMapper = JsonMapper.builder()
                .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                .build();
        jsonMapper.registerModule(new JavaTimeModule());
        return jsonMapper;
    }

    /// Gets the singleton instance of the global [CsvMapper].
    /// @return The global [CsvMapper] singleton.
    public ObjectMapper getCsvMapperInstance() {
        if(csvMapper != null)
            return csvMapper;

        csvMapper = CsvMapper.builder()
                .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                .build();
        csvMapper.registerModule(new JavaTimeModule());
        return csvMapper;
    }


}
