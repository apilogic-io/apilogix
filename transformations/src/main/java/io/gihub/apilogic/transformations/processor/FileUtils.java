package io.gihub.apilogic.transformations.processor;

import lombok.SneakyThrows;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;

public class FileUtils {
    @SneakyThrows
    public static Path getFilePath(String path) {
        var uri = Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(path)).toURI();
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            return fileSystem.getPath("/" + path);
        } else {
            return Paths.get(uri);
        }
    }
}
