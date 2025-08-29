package eu.nyarie.core;

import eu.nyarie.core.data.ConstDataLoader;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        ConstDataLoader.loadDataFromJson();
    }
}
