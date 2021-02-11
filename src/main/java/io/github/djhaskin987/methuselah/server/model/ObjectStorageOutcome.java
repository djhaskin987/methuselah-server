package io.github.djhaskin987.methuselah.server.model;

/**
 * This enum is the return type for ObjectStorageService's
 * <code>storeObject</code>. It specifies what kinds of errors might happen when
 * attempting to store an object.
 */
public enum ObjectStorageOutcome {
    /**
     * <ul>
     * <li><code>SUCCESSFUL</code>: The object storage was successful.</li>
     * <li><code>ALREADY_EXISTS</code>: The object already exists in the object
     * store.</li>
     * <li><code>ADDRESS_CHECKSUM_MISMATCH</code>: The object's checksum does
     * not match the given content address.</li>
     * <li><code>STORAGE_ERROR</code>: Some other storage error occurred, such
     * as disk or network issues.</li>
     * </ul>
     */
    SUCCESSFUL, ALREADY_EXISTS, ADDRESS_CHECKSUM_MISMATCH, STORAGE_ERROR
}
