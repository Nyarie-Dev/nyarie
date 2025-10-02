package eu.nyarie.core.util;

import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

@NullMarked
public class PathDecorator implements Path {

    private final Path delegate;

    public PathDecorator(Path path) {
        this.delegate = path;
    }

    @Override
    public FileSystem getFileSystem() {
        return delegate.getFileSystem();
    }

    @Override
    public boolean isAbsolute() {
        return delegate.isAbsolute();
    }

    @Override
    public Path getRoot() {
        return delegate.getRoot();
    }

    @Override
    public Path getFileName() {
        return delegate.getFileName();
    }

    @Override
    public Path getParent() {
        return delegate.getParent();
    }

    @Override
    public int getNameCount() {
        return delegate.getNameCount();
    }

    @Override
    public Path getName(int index) {
        return delegate.getName(index);
    }

    @Override
    public Path subpath(int beginIndex, int endIndex) {
        return delegate.subpath(beginIndex, endIndex);
    }

    @Override
    public boolean startsWith(Path other) {
        return delegate.startsWith(other);
    }

    @Override
    public boolean endsWith(Path other) {
        return delegate.endsWith(other);
    }

    @Override
    public Path normalize() {
        return delegate.normalize();
    }

    @Override
    public Path resolve(Path other) {
        return delegate.resolve(other);
    }

    @Override
    public Path relativize(Path other) {
        return delegate.relativize(other);
    }

    @Override
    public URI toUri() {
        return delegate.toUri();
    }

    @Override
    public Path toAbsolutePath() {
        return delegate.toAbsolutePath();
    }

    @Override
    public Path toRealPath(LinkOption... options) throws IOException {
        return delegate.toRealPath(options);
    }

    @Override
    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers) throws IOException {
        return delegate.register(watcher, events, modifiers);
    }

    @Override
    public int compareTo(Path other) {
        return delegate.compareTo(other);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
