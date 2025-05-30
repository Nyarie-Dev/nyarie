package eu.nyarie.core.data;

enum ConstDataFileNames {
    REGIONS("regions.json");

    private final String filename;

    ConstDataFileNames(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
