package eu.nyarie.core.io.init;

import eu.nyarie.core.io.FileLoader;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Path;
import java.util.List;

/// Uses file loaders to load all the data needed for map creation,
@Slf4j
public class MapDataLoader {

    private final FileLoader fileLoader;

    public MapDataLoader(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    public List<TerrainTypeCsvDto> loadTerrainTypes(Path basePath) {
        log.trace("Entered loadTerrainTypes method with basePath: {}", basePath);

        val terrainTypeInitPath = InitFilePaths.TERRAIN_TYPES;
        val path = basePath.resolve(terrainTypeInitPath.getPath());
        log.debug("Loading terrain types from file: {}", path);

        log.trace("Calling FileLoader");

        val entries = fileLoader.csvFromFileSystem(basePath, InitFilePaths.TERRAIN_TYPES, TerrainTypeCsvDto.getSchema());

        log.debug("Successfully initialized {} terrain types:", entries.size());
        entries.forEach(entry -> {
            log.debug("|--- [{}] {} (movement duration: {})", entry.id(), String.format("%-15s", entry.name()), String.format("%2d", entry.moveIntoDuration()));
        });

        return entries;
    }

    public List<RegionsCsvDto> loadRegions(Path basePath) {
        log.trace("Entered loadRegions method with basePath: {}", basePath);

        val regionTypePath = InitFilePaths.REGIONS;
        val path = basePath.resolve(regionTypePath.getPath());
        log.debug("Loading region definitions from file: {}", path);

        log.trace("Calling FileLoader");
        val entries = fileLoader.csvFromFileSystem(basePath, regionTypePath, RegionsCsvDto.getSchema());

        log.debug("Successfully loaded {} region definitions:", entries.size());
        entries.forEach(entry -> {
            log.debug("|--- [{}] [RGB: {},{},{}] [Terrain: {}] \"{}\"", entry.id(), String.format("%3d", entry.red()), String.format("%3d", entry.green()), String.format("%3d", entry.blue()), String.format("%3d", entry.terrainTypeId()), entry.name());
        });

        return entries;
    }
}
