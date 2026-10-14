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


    //TODO add logs and Javadoc
    public BufferedImage readFile(Path path, Supplier<InputStream> inputStreamSupplier) {
        try(val inputStream = inputStreamSupplier.get() ) {
            val image = ImageIO.read(inputStream);

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
