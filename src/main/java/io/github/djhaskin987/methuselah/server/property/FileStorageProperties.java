package io.github.djhaskin987.methuselah.server.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * POJO class that defines file object storage properties used in the File Storage Service.
 */
@ConfigurationProperties(prefix = "objects")
@ConstructorBinding
public class FileStorageProperties {
    /**
     * The location for temporary staging of objects while they are evaluated for accuracy before being moved into their
     * final storage location.
     */
    private final String stagingPath;

    /**
     * The actual location where object files will be permantently stored.
     */
    private final String permanentStoragePath;

    public final String getStagingPath() {
        return stagingPath;
    }

    public final String getPermanentStoragePath() {
        return permanentStoragePath;
    }

    /**
     * Always safe to override this method, obviously.
     */
    @Override
    public String toString() {
        return "FileStorageProperties{stagingPath=" + stagingPath + ",permanentStoragePath=" + permanentStoragePath
                + "}";
    }

    /**
     * Standard constructor.
     *
     * @param stagingPath
     *            the staging path.
     * @param permanentStoragePath
     *            permanent storage path.
     */
    public FileStorageProperties(final String stagingPath, final String permanentStoragePath) {
        this.stagingPath = stagingPath;
        this.permanentStoragePath = permanentStoragePath;
    }

}
