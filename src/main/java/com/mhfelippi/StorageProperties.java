package com.mhfelippi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Application properties containing information of the storage system.
 *
 * <p>The root location can be set via runtime arguments (storage.location). If no argument is provided, location is
 * loaded from HOME env or TMPDIR env variable. If none is set, root location is set to /.</p>
 */
@Component
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Contains the root location of manipulated files.
     */
    private String location;

    public StorageProperties() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("HOME")) {
            this.location = env.get("HOME");
        } else if (env.containsKey("TMPDIR")) {
            this.location = env.get("TMPDIR");
        } else {
            this.location = "/";
        }
    }

    /**
     * Gets the root location of files manipulated by a <code>StorageService</code>
     * @return The root location of files.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the root location of files manipulated by a <code>StorageService</code>
     * @param location The root location of files.
     */
    public void setLocation(String location) {
        this.location = location;
    }

}
