package io.github.djhaskin987.methuselah.server.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Files;

/**
 * Class representing an object storage service that operates by storing to the
 * file system.
 */
public final class FileObjectStorageService implements ObjectStorageService {

    /**
     * The length of the hex string used to make sub directories.
     */
    private static final int PART_LENGTH = 2;
    /**
     * The index start of the hex string used to create directories to house the
     * objects.
     */
    private static final int PART_START = 0;

    /**
     * Permanent storage location for objects when they are copied from the
     * staging location.
     */
    private final Path objectStorageLocation;
    /**
     * Objects are first copied to a staging location to verify their hash, and
     * for transactional purposes.
     */
    private final Path stagingStorageLocation;

    /**
     * Consume file storage properties given in the application properties file
     * as part of the constructor.
     *
     * @param fileStorageProps
     *                             the file storage properties given in the
     *                             properties file.
     * @throws IOException
     */
    @Autowired
    public FileObjectStorageService(
            final FileStorageProperties fileStorageProps) throws IOException {
        this.objectStorageLocation = Paths
                .get(fileStorageProps.getPermanentStoragePath());
        this.stagingStorageLocation = Paths
                .get(fileStorageProps.getStagingPath());
        Files.createDirectories(objectStorageLocation);
        Files.createDirectories(stagingStorageLocation);

    }

    private Path objectPath(final String contentAddress) {
        String firstPart = contentAddress.substring(PART_START, PART_LENGTH);
        String secondPart = contentAddress.substring(PART_LENGTH,
                PART_LENGTH + PART_LENGTH);
        return objectStorageLocation
                .resolve(Paths.get(firstPart, secondPart, contentAddress));
    }

    @Override
    public ObjectStorageOutcome storeObject(final String contentAddress,
            final InputStream content) {
        if (this.objectExists(contentAddress)) {
            return ObjectStorageOutcome.ALREADY_EXISTS;
        }
        Path initialLocation = stagingStorageLocation.resolve(contentAddress);
        String comparisonAddress;
        try {
            Files.copy(content, initialLocation);
            InputStream sha256Stream = Files.newInputStream(initialLocation);
            comparisonAddress = DigestUtils.sha256Hex(sha256Stream);
            sha256Stream.close();
        } catch (IOException e) {
            return ObjectStorageOutcome.STORAGE_ERROR;
        }
        if (comparisonAddress != contentAddress) {
            return ObjectStorageOutcome.ADDRESS_CHECKSUM_MISMATCH;
        }
        try {
            String firstPart = contentAddress.substring(PART_START,
                    PART_LENGTH);
            String secondPart = contentAddress.substring(PART_LENGTH,
                    PART_LENGTH + PART_LENGTH);
            Path destDir = objectStorageLocation
                    .resolve(Paths.get(firstPart, secondPart));
            Files.createDirectories(destDir);
            Path destination = destDir.resolve(contentAddress);
            Files.move(initialLocation, destination);
            return ObjectStorageOutcome.SUCCESSFUL;
        } catch (IOException e) {
            return ObjectStorageOutcome.STORAGE_ERROR;
        }

    }

    @Override
    public InputStream getObject(final String contentAddress) {
        try {
            return Files.newInputStream(objectPath(contentAddress));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean objectExists(final String contentAddress) {
        return Files.exists(objectPath(contentAddress));
    }
}
