package eu.nyarie.core.io;

import eu.luktronic.logblock.LogBlock;
import eu.nyarie.core.io.exception.FileLoadingException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.Supplier;

/// Loads a PNG file
@Slf4j
public class PngFileLoader {


    /// Loads a PNG image file by creating an [InputStream] from the
    /// passed [Supplier] and loading its data into a [BufferedImage].
    ///
    /// @param path The path where the image is located. Mainly used for logging.
    /// @param inputStreamSupplier [Supplier] that gets executed to create the [InputStream] to the PNG file.
    /// @return A [BufferedImage] containing PNG data.
    /// @throws FileLoadingException When encountering an error while reading PNG file.
    public BufferedImage readFile(Path path, Supplier<InputStream> inputStreamSupplier) {
        log.debug("Loading PNG: {}", path);
        try(val inputStream = inputStreamSupplier.get()) {
            log.trace("Loading file into BufferedImage");
            val image = ImageIO.read(inputStream);

            log.debug("Successfully loaded PNG: {}", path);
            return image;
        }
        catch (Exception e) {
            val logBlock = LogBlock.withLogger(log);
            logBlock.error("""
                    ERROR WHILE LOADING PNG FILE:
                    {}
                    
                    An unexpected {} occurred while trying to read the PNG file:
                    '{}'
                    """, path, e.getClass().getSimpleName(), e.getMessage());
            throw FileLoadingException.unexpectedErrorReadingFile(path, e);
        }
    }
}
