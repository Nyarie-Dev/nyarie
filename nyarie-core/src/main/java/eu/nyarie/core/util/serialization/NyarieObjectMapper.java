package eu.nyarie.core.util.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

/// Class that holds a global singleton Jackson [ObjectMapper] so
///  that it can be reused across the codebase.
public class NyarieObjectMapper {

    private static ObjectMapper instance;

    /// Gets the singleton instance of the global [ObjectMapper].
    /// @return The global [ObjectMapper] singleton.
    public ObjectMapper getInstance() {
        if(instance != null)
            return instance;

        instance = new ObjectMapper();
        return instance;
    }
}
