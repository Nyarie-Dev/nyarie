package eu.nyarie.core.domain;

import eu.nyarie.core.api.data.Identifiable;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Identity implements Identifiable {

    private final UUID id;

    public Identity() {
        this.id = UUID.randomUUID();
    }

    public Identity(UUID id) {
        this.id = id;
    }
}
