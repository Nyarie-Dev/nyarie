package eu.nyarie.core.io.assets.loader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import eu.luktronic.logblock.LogBlock;
import eu.nyarie.core.io.assets.AssetDto;
import eu.nyarie.core.io.assets.AssetFileDto;
import eu.nyarie.core.io.assets.exception.AssetLoadingException;
import eu.nyarie.core.io.assets.exception.AssetNotFoundException;
import eu.nyarie.core.util.serialization.NyarieObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/// Class that loads a single asset file and converts it into the respective
/// class.
@Slf4j
public class AssetFileLoader {

    /// Loads the assets from a combination of the passed `basePath`
    /// as well as the [assetFilePath][AssetFilePath] and returns a list
    /// of the assets cast to their respective [AssetDto] class.
    ///
    /// The final [Path] where the asset file will be searched for is made from `basePath` + `assetFilePath`.<br>
    /// For example, if the `basePath` is set to `/nyarie` and the `assetFilePath` is [AssetPaths#REGIONS], then the
    /// path of the asset file will be:
    /// ```text
    /// /nyarie/assets/map/region.json
    /// ```
    /// @param assetFilePath The path to the asset file. Must be an existing file.
    /// @throws AssetNotFoundException {@inheritDoc}x
    public <T extends AssetFileDto<?>> Optional<T> fromFileSystem(Path basePath, AssetFilePath<T> assetFilePath) {
        val path = basePath.resolve(assetFilePath.getPath());
        log.debug("Loading asset file for class '{}': {}", assetFilePath.getAssetClass().getSimpleName(), path);

        if(Files.notExists(path)) {
            log.debug("Asset file '{}' was not found, returning empty optional", path);
            return Optional.empty();
        }

        log.debug("Found asset file '{}'", path);
        val om = new NyarieObjectMapper().getInstance();
        try {
            log.debug("Deserializing asset file '{}'", path);
            val response = om.readValue(path.toFile(), assetFilePath.getAssetClass());
            log.debug("Loaded asset file {}", assetFilePath.getPath());
            return Optional.of(response);
        }
        catch (JsonMappingException e) {
            logJsonError(path, e);
            throw AssetLoadingException.invalidStructure(path, e);
        }
        catch (Exception e) {
            val logBlock = LogBlock.withLogger(log);
            logBlock.error("""
                    ERROR WHILE LOADING ASSET FILE:
                    {}
                    
                    An unexpected {} occurred while trying to read the asset file:
                    '{}'
                    """, path, e.getClass().getSimpleName(), e.getMessage());
            throw AssetLoadingException.unexpectedErrorReadingFile(path, e);
        }
    }

    public <T extends AssetFileDto<?>> Optional<T> fromFileSystemWithClasspathFallback(Path basePath, AssetFilePath<T> assetFilePath) {
        val fileSystemAsset = fromFileSystem(basePath, assetFilePath);
        if(fileSystemAsset.isPresent()) {
            log.debug("Asset was loaded using file system - skipping classpath loading");
            return fileSystemAsset;
        }
        else
            log.debug("Asset not present in file system - falling back to classpath loading");

        val path = assetFilePath.getPath();
        log.debug("Loading asset file for class '{}' from classpath resource: {}", assetFilePath.getAssetClass().getSimpleName(), path);

        try (val inputStream = this.getClass().getClassLoader().getResourceAsStream(assetFilePath.getPath().toString())) {
            if (inputStream == null) {
                log.debug("Asset file '{}' was not found, returning empty optional", path);
                return Optional.empty();
            }
            log.debug("Found asset file '{}'", path);

            val om = new NyarieObjectMapper().getInstance();
            log.debug("Deserializing asset file '{}'", path);
            val response = om.readValue(inputStream, assetFilePath.getAssetClass());
            log.debug("Loaded asset file {}", assetFilePath.getPath());
            return Optional.of(response);

        } catch (IOException e) {
            val logBlock = LogBlock.withLogger(log);
            logBlock.error("""
                    ERROR WHILE LOADING ASSET FILE:
                    {}
                    
                    An unexpected {} occurred while trying to read the asset file:
                    '{}'
                    """, path, e.getClass().getSimpleName(), e.getMessage());
            throw AssetLoadingException.unexpectedErrorReadingFile(path, e);
        }
    }

    private void logJsonError(Path path, JsonProcessingException e) {
        val location = e.getLocation();
        val sb = new StringBuilder();

        sb.append(String.format("ERROR PARSING ASSET FILE:\n%s:\n", path.toString()));
        sb.append(e.getOriginalMessage()).append("\n\n");

        sb.append(String.format("...near Line %d, Column %d:\n",
                location.getLineNr(),
                location.getColumnNr()));
        try {
            val lines = Files.readAllLines(path);
            // Find the problematic line

            val errorLineNumber = location.getLineNr(); // 1-based

            // Define the context window (2 lines before, 2 lines after)
            val startLineNum = Math.max(1, errorLineNumber - 2);
            val endLineNum = Math.min(lines.size(), errorLineNumber + 2);

            // Get the width for padding line numbers (e.g., "115" needs 3 chars)
            int maxLineNumWidth = String.valueOf(endLineNum).length();
            String lineFormat = " %" + maxLineNumWidth + "d | %s\n"; // e.g., " 115 | text"

            for (int i = startLineNum - 1; i < endLineNum; i++) {
                int currentLineNum = i + 1;
                String line = lines.get(i);

                // 4. Print the line with its number
                sb.append(String.format(lineFormat, currentLineNum, line));

                // 5. Add the caret (^) pointer if this is the error line
                if (currentLineNum == errorLineNumber) {
                    // Calculate padding for the caret
                    // " " (leading space) + maxLineNumWidth + " | " (3 chars)
                    int prefixWidth = 1 + maxLineNumWidth + 3;
                    // (location.getColumnNr() is 1-based)
                    String pointer = " ".repeat(prefixWidth + location.getColumnNr() - 1) + "^";
                    sb.append(pointer).append("\n");
                }
            }

            LogBlock.withLogger(log).error(sb.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
