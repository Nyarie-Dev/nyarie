package eu.nyarie.core.io.assets;

/// Parent abstract class defining that a type is an Asset.
///
/// Assets are loaded on startup of the engine and are **immutable** during runtime.
/// They hold data that (usually) stays constant during a running game - such as the map, or the available items.
///
/// However, the assets **can** be updated and reloaded, but doing so might corrupt the game state if
/// not done with caution.
public abstract class Asset {
}
