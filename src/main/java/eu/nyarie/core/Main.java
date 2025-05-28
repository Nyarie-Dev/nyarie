package eu.nyarie.core;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            var uwu = Main.class.getProtectionDomain().getCodeSource().getLocation();
            var path = Paths.get(uwu.toURI());
            System.out.println("Path: " + path);
            System.out.println("Absolute: " + path.toAbsolutePath());
            System.out.println("URL: " + uwu);
            System.out.println("URI: " + uwu.toURI());
            final File file = new File(uwu.toURI().toString());
            System.out.println("File: " + file);
            System.out.println("File abs: " + file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
