package eu.nyarie.core.util.serialization;

import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/// Class that holds a global singleton Jackson [ObjectMapper] so
///  that it can be reused across the codebase.
public class NyarieObjectMapper {

    private static ObjectMapper instance;

    /// Gets the singleton instance of the global [ObjectMapper].
    /// @return The global [ObjectMapper] singleton.
    public ObjectMapper getInstance() {
        if(instance != null)
            return instance;

        instance = JsonMapper.builder()
                .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                .build();
        instance.registerModule(new JavaTimeModule());
        return instance;
    }
}
