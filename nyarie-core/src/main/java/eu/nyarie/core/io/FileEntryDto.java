package eu.nyarie.core.io;

import eu.nyarie.core.domain.constant.Asset;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/// Parent abstract class defining that a type is a serialization DTO of a single entry in a file.
///
/// These are the classes used for de-/serializing a file.
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class FileEntryDto {

}