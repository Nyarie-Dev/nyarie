package eu.nyarie.core.io.appdata;

import lombok.val;

/// Class that provides functionality to initialize and load an [AppDataDirectory].
public class AppDataDirectoryLoader {

    private final AppDataDirectory directory;

    AppDataDirectoryLoader() {
        directory = new AppDataDirectory();
    }

    /// Initializes and creates all non-existing paths of the [AppDataDirectory].
    ///
    /// The path of the [AppDataDirectory] is determined on call of this method, meaning
    /// that it is not guaranteed that two instances of [AppDataDirectory] have the same underlying
    /// paths. This could for example happen if the default path is overwritten using the
    /// `eu.nyarie.core.appdata.path` system property between the creation of the two instances.
    /// @return A newly initialized [AppDataDirectory].
    public static AppDataDirectory initAppDataDirectory() {
        val loader = new AppDataDirectoryLoader();
        return loader.directory;
    }
}
