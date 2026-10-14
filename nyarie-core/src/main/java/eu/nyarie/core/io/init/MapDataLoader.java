package eu.nyarie.core.io.init;

import eu.nyarie.core.io.FileLoader;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/// Uses file loaders to load all the data needed for map creation,
@Slf4j
public class MapDataLoader {

    private final FileLoader fileLoader;

    public MapDataLoader(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    /// Loads the content of the `init/map/terrain-types.csv` file (as defined in [InitFilePaths#TERRAIN_TYPES])
    /// inside the specified `basePath`.
    ///
    /// For example, if the `basePath` is set to `/nyarie`, then the path of the file will be:
    /// ```text
    /// /nyarie/init/map/terrain-types.csv
    /// ```
    /// ### CSV content
    /// This file contains the definition of all existing terrain types, as well as their:
    /// - ID
    /// - name
    /// - movement duration
    ///
    /// It is important that the CSV file content matches exactly the schema as it is returned in [TerrainTypeCsvDto#getSchema()].
    ///
    /// @param basePath The directory in which the `init/map/terrain-type.csv` file will be searched
    /// @return A [List] containing the Terrain Types. Empty [List] if the file was not found or empty.
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

    /// Loads the content of the `init/map/regions.csv` file (as defined in [InitFilePaths#REGIONS])
    /// inside the specified `basePath`.
    ///
    /// For example, if the `basePath` is set to `/nyarie`, then the path of the file will be:
    /// ```text
    /// /nyarie/init/map/regions.csv
    /// ```
    /// ### CSV content
    /// This file contains the definition of all existing regions, as well as their:
    /// - ID
    /// - name
    /// - terrain type
    /// - RGB values for the `regions.png` file
    ///
    /// It is important that the CSV file content matches exactly the schema as it is returned in [RegionsCsvDto#getSchema()].
    ///
    /// @param basePath The directory in which the `init/map/regions.csv` file will be searched
    /// @return A [List] containing the Region definitions. Empty [List] if the file was not found or empty.
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
