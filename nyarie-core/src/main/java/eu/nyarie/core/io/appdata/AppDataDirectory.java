package eu.nyarie.core.io.appdata;

import lombok.extern.slf4j.Slf4j;

/// Class representing the Engine's Application Data Directory.
///
/// By default, the AppData directory is created on the following path:
/// - Windows: `C:\Users\\<username>\AppData\Roaming`
/// - Linux/UNIX: `/home/<username>/.local/share`
/// - macOS: `/home/<username>/Library/Application Support`
///
/// The location of the AppData directory can be configured by (higher number means higher priority):
/// 1. Setting the `eu.nyarie.core.appdata.path` Java System property
/// 2. Setting a `NYARIE_CORE_APPDATA_PATH` environment variable
@Slf4j
public final class AppDataDirectory {
}
