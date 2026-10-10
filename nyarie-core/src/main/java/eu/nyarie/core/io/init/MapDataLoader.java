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

        val terrainTypeInitPath = InitFilePaths.TERRAIN_TYPES;
        val path = basePath.resolve(terrainTypeInitPath.getPath());
        log.info("Path is: {}", path);

        val entries = fileLoader.csvFromFileSystem(basePath, InitFilePaths.TERRAIN_TYPES, TerrainTypeCsvContent.getSchema());

        return new TerrainTypeCsvContent(entries);
    }
}
