package io.github.djhaskin987.methuselah.server.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.djhaskin987.methuselah.server.model.FileObjectStorageService;
import io.github.djhaskin987.methuselah.server.model.ObjectStorageOutcome;
import io.github.djhaskin987.methuselah.server.payload.UploadContentResponse;

/**
 * Controller for adding content to the object store.
 *
 * This class houses the HTTP api that allows the user to upload content to the
 * object store.
 */
@RestController
@RequestMapping("/api/v1")
public final class ContentController {

        /**
         * The storage service obect that stores and retrieves objects. It is
         * used here to store objects as they come in and out of the API.
         */
        @Autowired
        private FileObjectStorageService storageService;

        /**
         * Upload objects endpoint in the API.
         *
         *
         * @param object
         *                       The object given to this endpoint to upload.
         *                       These "files" must be named after the sha256sum
         *                       hex string of the object they are describing.
         *                       This hex string is the content address. If that
         *                       address already exists in the object store, it
         *                       is skipped outright. If the computed sha256sum
         *                       of the object and the name of the object do not
         *                       match, an error for that object is returned.
         *                       Otherwise, a successful upload message is
         *                       returned for the object
         * @return a list of metadata which essentially returns the names of
         *         these objects as recorded in the object store.
         */
        @PostMapping("/objects/upload")
        public UploadContentResponse uploadObject(@RequestBody
        final MultipartFile object) {
                String contentAddress = object.getOriginalFilename();
                InputStream input;
                try {
                        input = object.getInputStream();
                } catch (IOException e) {
                        return new UploadContentResponse(contentAddress,
                                        "storage-error");
                }
                ObjectStorageOutcome outcome = storageService
                                .storeObject(contentAddress, input);
                try {
                        input.close();
                } catch (IOException e) {
                        return new UploadContentResponse(contentAddress,
                                        "storage-error");
                }
                switch (outcome) {
                case ADDRESS_CHECKSUM_MISMATCH:
                        return new UploadContentResponse(contentAddress,
                                        "address-checksum-mimsmatch");
                case ALREADY_EXISTS:
                        return new UploadContentResponse(contentAddress,
                                        "object-already-exists");
                case STORAGE_ERROR:
                        return new UploadContentResponse(contentAddress,
                                        "storage-error");
                case SUCCESSFUL:
                        return new UploadContentResponse(contentAddress,
                                        "successful");
                default:
                        return new UploadContentResponse(contentAddress,
                                        "unknown-error");
                }
        }

        /**
         * Endpoint for checking multiple objects to see if they already exist.
         * This endpoint is called by the client to be able to see whether or
         * not certain objects already exist in the endpoint, and if they do,
         * the client may refrain from re-uploading them.
         *
         * @param requests
         *                         a list of JSON objects describing the objects
         *                         for which to check for their existence.
         * @return a list specifying for each requested object whether it exists
         *         or not.
         */
        @PostMapping("/objects/check-multiple")
        public List<String> checkExistsMultiple(@Validated @RequestBody
        final String[] requests) {
                List<String> presentObjects = new ArrayList<String>();
                for (String request : requests) {
                        if (storageService.objectExists(request)) {
                                presentObjects.add(request);
                        }
                }
                return presentObjects;
        }

        /**
         * API endpoint for downloading an object. This method allows the client
         * to download multiple objects all at once.
         *
         * @param object
         *                       the name of an object.
         * @return the object in question.
         */
        @GetMapping("/objects/download/{object}")
        public ResponseEntity<Resource> downloadObject(@PathVariable
        final String object) {
                return null;
        }

}
