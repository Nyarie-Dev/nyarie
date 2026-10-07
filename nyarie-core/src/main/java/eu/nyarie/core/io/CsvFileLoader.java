package eu.nyarie.core.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import eu.luktronic.logblock.LogBlock;
import eu.nyarie.core.io.assets.exception.AssetLoadingException;
import eu.nyarie.core.util.serialization.NyarieObjectMappers;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

/// Loads a CSV file and maps it to the desired object
@Slf4j
public class CsvFileLoader {

    public <T> List<T> readFile(Path path, Class<T> mapToClass, CsvSchema schema, Supplier<InputStream> inputStreamSupplier) {
        try(val inputStream = inputStreamSupplier.get() ) {
            if (inputStream == null) {
                log.debug("CSV file '{}' was not found, returning empty optional", path);
                return List.of();
            }
            log.debug("Found CSV file '{}'", path);

            log.debug("Deserializing CSV file '{}'", path);
            val om = new NyarieObjectMappers().getCsvMapperInstance();
            val iterator = om.readerFor(mapToClass)
                    .with(schema)
                    .<T>readValues(inputStream);
            val response = iterator.readAll();
            log.debug("Loaded CSV file {}", path);
            return response;
        }
        catch (JsonMappingException e) {
            log.trace("Encountered {} while reading CSV file - creating pretty log block", e.getClass().getSimpleName());
            logJsonError(path, e, inputStreamSupplier);
            throw AssetLoadingException.invalidStructure(path, e);
        }
        catch (Exception e) {
            val logBlock = LogBlock.withLogger(log);
            logBlock.error("""
                    ERROR WHILE LOADING CSV FILE:
                    {}
                    
                    An unexpected {} occurred while trying to read the CSV file:
                    '{}'
                    """, path, e.getClass().getSimpleName(), e.getMessage());
            throw AssetLoadingException.unexpectedErrorReadingFile(path, e);
        }
    }

    private void logJsonError(Path path, JsonProcessingException e, Supplier<InputStream> inputStreamSupplier) {
        val location = e.getLocation();
        val sb = new StringBuilder();

        sb.append(String.format("ERROR PARSING CSV FILE:\n%s:\n", path.toString()));
        sb.append(e.getOriginalMessage()).append("\n\n");

        sb.append(String.format("...near Line %d, Column %d:\n",
                location.getLineNr(),
                location.getColumnNr()));

        try (var inputStream = inputStreamSupplier.get();
             var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            // Read all lines from the stream into a list
            val lines = reader.lines().toList();

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
            throw AssetLoadingException.unexpectedErrorWhileGettingJsonErrorLocation(ex);
        }
    }
}
