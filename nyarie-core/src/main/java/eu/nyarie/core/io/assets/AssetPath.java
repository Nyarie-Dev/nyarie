package eu.nyarie.core.io.assets;

import lombok.Getter;

import java.nio.file.Path;

@Getter
enum AssetPath {
    ROOT(Path.of("assets")),
    REGIONS(ROOT.path.resolve("map", "regions.json"));

    private final Path path;

    AssetPath(Path path) {
        this.path = path;
    }

}
