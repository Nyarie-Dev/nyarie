package eu.nyarie.core;

import eu.nyarie.core.io.assets.loader.InstallationAssets;

public class Main {

    public static void main(String[] args) {
        new InstallationAssets().loadDataFromJson();
    }
}
