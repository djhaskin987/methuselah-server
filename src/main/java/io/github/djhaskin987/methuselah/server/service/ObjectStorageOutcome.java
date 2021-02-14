package io.github.djhaskin987.methuselah.server.service;

/**
 * This enum is the return type for ObjectStorageService's
 * <code>storeObject</code>. It specifies what kinds of errors might happen when
 * attempting to store an object.
 */
public enum ObjectStorageOutcome {
    /**
     * The object storage was successful.
     */
    SUCCESSFUL,
    /**
     * The object attempted to be stored already exists.
     */
    ALREADY_EXISTS,
    /**
     * The object's content sha256sum disagrees with its given content address.
     */
    ADDRESS_CHECKSUM_MISMATCH,
    /**
     * The address given was not a valid address.
     */
    ADDRESS_INVALID,
    /**
     * There was some other error involved in storing the object.
     */
    STORAGE_ERROR
}
