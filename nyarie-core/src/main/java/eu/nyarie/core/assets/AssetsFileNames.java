package eu.nyarie.core.assets;

enum AssetsFileNames {
    REGIONS("regions.json");

    private final String filename;

    AssetsFileNames(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
