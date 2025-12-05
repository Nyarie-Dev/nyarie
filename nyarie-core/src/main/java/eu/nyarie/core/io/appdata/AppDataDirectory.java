package eu.nyarie.core.io.appdata;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

/// Class representing the Engine's Application Data Directory.
///
/// By default, the AppData directory is created on the following path:
/// - Windows: `C:\Users\\<username>\AppData\Roaming\nyarie`
/// - Linux/UNIX: `/home/<username>/.local/share/nyarie`
/// - macOS: `/home/<username>/Library/Application Support/nyarie`
///
/// The location of the AppData directory can be configured by (higher number means higher priority):
/// 1. Setting the `eu.nyarie.core.appdata.path` Java System property
/// 2. Setting a `NYARIE_CORE_APPDATA_PATH` environment variable
@Slf4j
public final class AppDataDirectory {

    /// Gets the [Path] of the app data directory. See [AppDataDirectory] for the default values.
    /// @return The [Path] of the app data directory.
    public Path getRootDirectory() {
        return AppDataPaths.ROOT;
    }

    /// Gets the [Path] of the `/mods` directory inside the app data directory.
    ///
    /// This is equal to the [mods path][AppDataPaths#MODS] appended to the [app data directory path][#getRootDirectory()]
    /// @return The [Path] of the `/mods` directory inside the app data directory.
    public Path getModsDirectory() {
        return AppDataPaths.MODS;
    }
}
