package eu.nyarie.core.io;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/// Abstract parent class for all types that are representations of an
/// entire file.
///
/// Each [FileContentDto] has a `data` attribute, which is a list containing the
/// actual entries.
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class FileContentDto<T extends FileEntryDto> {

    private List<T> data;

    @SafeVarargs
    public FileContentDto(T... data) {
        this.data = Arrays.asList(data);
    }
}