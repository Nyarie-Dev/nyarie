package eu.nyarie.core.engine.data;

import java.util.List;

/// Parent class for all classes containing constant data.
///
/// Defines the {@link #load(List)} method, which loads the data into memory.
abstract class ConstData<T> {

    /// Loads the passed items into const memory
    abstract void load(List<T> items);
}
