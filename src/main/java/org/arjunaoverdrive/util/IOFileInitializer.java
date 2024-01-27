package org.arjunaoverdrive.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

@Component
public class IOFileInitializer {

    private final ResourceLoader resourceLoader;

    public IOFileInitializer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public File getFile(String path) throws IOException {
        File file;

        Resource resource = resourceLoader.getResource(path);

        if (!resource.exists()) {
            throw new RuntimeException(MessageFormat.format("File {0} doesn't exist.", path));
        }
        file = resource.getFile();

        return file;
    }
}
