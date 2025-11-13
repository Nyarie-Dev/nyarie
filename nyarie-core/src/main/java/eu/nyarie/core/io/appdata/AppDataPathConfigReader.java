package eu.nyarie.core.io.appdata;

import lombok.val;

import java.util.Optional;

/// Class responsible for reading the configurable values for the [AppDataPaths].
///
/// For more information on which configs exist and how to configure the [AppDataPaths],
/// see [AppDataPathResolver].
final class AppDataPathConfigReader {

    /// Returns the value set for the `eu.nyarie.core.appdata.path` system property.
    /// @return An [Optional] containing the value of the system property. [Optional#EMPTY] if property is not set.
    public Optional<String> getSystemPropertyValue() {
        val SYSTEM_PROPERTY_NAME = "eu.nyarie.core.appdata.path";
        return Optional.ofNullable(System.getProperty(SYSTEM_PROPERTY_NAME));
    }

    /// Returns the value of the `NYARIE_CORE_APPDATA_PATH` environment variable,
    /// @return An [Optional] containing the value of the env var. [Optional#EMPTY] if env var is not set.
    public Optional<String> getEnvVarValue() {
        val ENV_NAME = "NYARIE_CORE_APPDATA_PATH";
        return Optional.ofNullable(System.getenv(ENV_NAME));
    }
}
