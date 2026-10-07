package eu.nyarie.core.io.init;

import eu.nyarie.core.io.CsvFileLoader;
import eu.nyarie.core.io.PngFileLoader;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/// Uses file loaders to load all the data needed for map creation,
@Slf4j
public class MapDataLoader {

    private final CsvFileLoader csvFileLoader;
    private final PngFileLoader pngFileLoader;

    public MapDataLoader(CsvFileLoader csvFileLoader, PngFileLoader pngFileLoader) {
        this.csvFileLoader = csvFileLoader;
        this.pngFileLoader = pngFileLoader;
    }

    public List<TerrainTypeCsvContent> loadTerrainTypes() {

        val terrainTypeInitPath = InitFilePaths.TERRAIN_TYPES;
        val path = terrainTypeInitPath.getPath();
        val dtoClass = terrainTypeInitPath.getDtoClass();

        csvFileLoader.readFile(path, dtoClass, TerrainTypeCsvContent.getSchema(), () -> {
            try {
                return Files.newInputStream(path);
            } catch (IOException e) {
                log.warn("No InputStream could be created for path '{}'", path);
                log.warn("Caused by {}: {}", e.getClass().getSimpleName(), e.getMessage());
                log.warn("Passing null to readAsset helper");
                return null;
            }
        });
        return List.of();
    }
}
