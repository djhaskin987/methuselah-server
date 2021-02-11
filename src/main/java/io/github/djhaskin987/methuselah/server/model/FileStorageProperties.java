package io.github.djhaskin987.methuselah.server.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * POJO class that defines file object storage properties used in the File
 * Storage Service.
 */
@ConfigurationProperties(prefix = "object-store.file-system")
public final class FileStorageProperties {
    /**
     * The location for temporary staging of objects while they are evaluated
     * for accuracy before being moved into their final storage location.
     */
    private String stagingPath;

    /**
     * The actual location where object files will be permantently stored.
     */
    private String permanentStoragePath;

    public String getStagingPath() {
        return stagingPath;
    }

    public void setStagingPath(final String stagingPath) {
        this.stagingPath = stagingPath;
    }

    public String getPermanentStoragePath() {
        return permanentStoragePath;
    }

    public void setPermanentStoragePath(final String permanentStoragePath) {
        this.permanentStoragePath = permanentStoragePath;
    }

}
