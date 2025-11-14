package eu.nyarie.core.io.appdata;

/// Helper Class responsible for determining the path of the engine's **app data directory**.
///
/// ### Default path
/// The default path of the app data directory will be in a subdirectory of the user home directory.
/// The subdirectory however depends on the OS that the engine is running on.<br>
/// Assuming defaults user homes with a user called `john`:
/// - Windows: `C:\Users\john\AppData\Roaming`
/// - Linux/UNIX: `/home/john/.local/share`
/// - macOS: `/Users/john/Library/Application Support`
///
/// ### Configuration
/// The default path is customizable and can be overwritten by (higher number means higher priority):
/// 1. Setting the `eu.nyarie.core.appdata.path` Java System property
/// 2. Setting the `NYARIE_CORE_APPDATA_PATH` environment variable
///
/// The values must contain the **absolute path** of the directory that should be used.
final class AppDataPathResolver {
}
