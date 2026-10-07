package eu.nyarie.core.io.init;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/// Parent abstract class defining that a type is a serialization DTO of a single
/// entry in an init file.
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class InitFileEntryDto {

}