package eu.nyarie.core.engine.data;

import java.util.List;

abstract class ConstData<T> {

    /// Loads the passed items into const memory
    abstract void load(List<T> items);
}
