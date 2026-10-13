package eu.nyarie.core.io.assets.map;

import eu.nyarie.core.io.FileDto;

import java.util.List;

public record RegionsAsset(List<RegionAsset> data) implements FileDto {

}
