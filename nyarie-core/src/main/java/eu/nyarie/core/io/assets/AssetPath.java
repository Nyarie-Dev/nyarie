package eu.nyarie.core.io.assets;

import lombok.Getter;

import java.nio.file.Path;

@Getter
enum AssetPath {
    ROOT("assets", Path.of("assets")),
    REGIONS("regions.json", ROOT.path.resolve("map", "regions.json"));

    private final String filename;
    private final Path path;

    AssetPath(String filename, Path path) {
        this.filename = filename;
        this.path = path;
    }

}
