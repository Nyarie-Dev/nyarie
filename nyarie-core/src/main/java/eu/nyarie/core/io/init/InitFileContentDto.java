package eu.nyarie.core.io.init;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/// Abstract parent class for all types that are representations of an
/// entire init file.
///
/// Each init file has a `data` attribute, which is a list containing the
/// actual entries.
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class InitFileContentDto<T extends InitFileEntryDto> {

    private List<T> data;

    @SafeVarargs
    public InitFileContentDto(T... data) {
        this.data = Arrays.asList(data);
    }

    public InitFileContentDto(List<T> data) {
        this.data = new ArrayList<>(data);
    }
}