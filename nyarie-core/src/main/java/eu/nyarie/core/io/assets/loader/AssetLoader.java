package eu.nyarie.core.io.assets.loader;

import eu.nyarie.core.io.installation.InstallationDirectory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Path;
import java.util.Set;

/// Class responsible for loading all assets (installation and mods) and returning
/// them in a single [AssetContext]
@Slf4j
@RequiredArgsConstructor
class AssetLoader {

    private final AssetDirectoryLoader assetDirectoryLoader;
    private final InstallationDirectory installationDirectory;
    private final Set<ModDirectory> modDirectories;

    AssetContext loadAssets() {
        log.info("Loading all assets of installation directory and {} selected mods", modDirectories.size());
        logDirectories();

        log.debug("Loading installation assets from installation directory: {}", installationDirectory.getRootDirectory());
        val installationAssets = assetDirectoryLoader.fromFileSystemWithClasspathFallback(installationDirectory.getRootDirectory());
        log.debug("Starting to load mod assets");
        val modAssets = modDirectories.stream().map(modDirectory -> {
            log.trace("Loading assets for mod: {}", modDirectory);
            return assetDirectoryLoader.fromFileSystem(modDirectory.getRootDirectory());
        }).toList();

        log.trace("Instantiating AssetContext from loaded directories");
        val assetContext = new AssetContext(installationAssets, modAssets);
        log.info("Finished loading {} assets across installation directory and {} mods", assetContext.totalAssetCount(), modDirectories.size());
        return assetContext;
    }

    private void logDirectories() {
        log.trace("Selected installation directory:");
        log.trace("|-- {}", installationDirectory.getRootDirectory());
        log.trace("Selected mod directories:");
        modDirectories.forEach(modDir -> log.trace("|-- {}", modDir.getRootDirectory()));
    }

    //TODO create actual classes for these
    
    class ModDirectory {
        public String getName() {return "Mod";}
        public Path getRootDirectory() {return null;}

        @Override
        public String toString() {
            return getName();
        }
    }
}
