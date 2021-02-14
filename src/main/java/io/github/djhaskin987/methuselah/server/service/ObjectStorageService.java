package io.github.djhaskin987.methuselah.server.service;

import java.io.InputStream;

import org.springframework.core.io.Resource;

/**
 * This interface provides a storage service for content objects, serving them
 * up and storing them as well.
 */
public interface ObjectStorageService {

    /**
     * Attempt to Store object in object store, return whether or not it worked.
     *
     * @param contentAddress
     *                           the actual content address. @param content the
     *                           input stream to access the content. Storage
     *                           will fail if the sha256 sum of the content does
     *                           not match contentAddress. storeObject fully
     *                           consumes and closes the InputStream. @throws
     * @param content
     *                           the input stream to the content. After
     *                           invocation, this input stream will be at the
     *                           end of stream.
     * @return whether or not the storage action worked, and if it didn't, why
     *         not.
     */
    ObjectStorageOutcome storeObject(String contentAddress,
            InputStream content);

    /**
     * Retrieve an InputStream to the stored object in the object store.
     *
     * @param contentAddress
     *                           the actual content address, which consists of
     *                           the sha256sum hex string of the contents of the
     *                           object.
     * @return A spring Resource describing/pointing to the resource.
     */
    Resource getObject(String contentAddress);

    /**
     * Return whether or not an object already exists in the object store.
     *
     * @param contentAddress
     *                           the content address (sha256sum hex string of
     *                           the content in the object).
     * @return whether or not the content already exists in the store.
     */
    boolean objectExists(String contentAddress);
}
