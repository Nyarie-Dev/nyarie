package eu.nyarie.core.io.init;

import eu.nyarie.core.io.FileLoader;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Path;

/// Uses file loaders to load all the data needed for map creation,
@Slf4j
public class MapDataLoader {

    private final FileLoader fileLoader;

    public MapDataLoader(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    public TerrainTypeCsvContent loadTerrainTypes(Path basePath) {
        log.trace("Entered loadTerrainTypes method with basePath: {}", basePath);

        val terrainTypeInitPath = InitFilePaths.TERRAIN_TYPES;
        val path = basePath.resolve(terrainTypeInitPath.getPath());
        log.debug("Loading terrain types from file: {}", path);

        log.trace("Calling FileLoader");
        val entries = fileLoader.csvFromFileSystem(basePath, InitFilePaths.TERRAIN_TYPES, TerrainTypeCsvContent.getSchema());

        log.debug("Successfully initialized {} terrain types:", entries.size());
        entries.forEach(entry -> {
            log.debug("|--- [{}] {} (movement duration: {})", entry.getId(), entry.getName(), entry.getMoveIntoDuration());
        });

        return new TerrainTypeCsvContent(entries);
    }
}
