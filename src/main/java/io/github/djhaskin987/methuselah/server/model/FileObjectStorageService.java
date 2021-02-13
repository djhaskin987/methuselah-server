package io.github.djhaskin987.methuselah.server.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
     * The offset in the address marking the end of the second "part" used to
     * organize the objects.
     */
    private static final int PART_TERMINATOR = 4;
    /**
     * The end of the first part of the address used to organize the objects,
     * and the start of the second.
     */
    private static final int PART_SEPARATOR = 2;
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

        String firstPart = contentAddress.substring(PART_START, PART_SEPARATOR);
        String secondPart = contentAddress.substring(PART_SEPARATOR,
                PART_TERMINATOR);
        return objectStorageLocation
                .resolve(Paths.get(firstPart, secondPart, contentAddress));
    }

    @Override
    public ObjectStorageOutcome storeObject(final String contentAddress,
            final InputStream content) {
        if (contentAddress.length() <= PART_TERMINATOR) {
            return ObjectStorageOutcome.ADDRESS_INVALID;
        }
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
                    PART_SEPARATOR);
            String secondPart = contentAddress.substring(PART_SEPARATOR,
                    PART_TERMINATOR);
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
    public Resource getObject(final String contentAddress) {
        if (contentAddress.length() <= PART_TERMINATOR) {
            return null;
        }
        return new FileSystemResource(objectPath(contentAddress));
    }

    @Override
    public boolean objectExists(final String contentAddress) {
        return Files.exists(objectPath(contentAddress));
    }
}
