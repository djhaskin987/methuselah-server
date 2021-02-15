package io.github.djhaskin987.methuselah.server.controller;

import java.net.URI;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import io.github.djhaskin987.methuselah.server.service.ObjectStorageService;
import io.github.djhaskin987.methuselah.server.service.ObjectStorageOutcome;
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
     * HTTP Status code used if there was a problem storing an object in the
     * object store, or some other server-size error occurred.
     */
    public static final int SERVER_ERROR = 500;
    /**
     * The storage service obect that stores and retrieves objects. It is used
     * here to store objects as they come in and out of the API.
     */
    @Autowired
    private ObjectStorageService storageService;

    /**
     * Upload objects endpoint in the API.
     *
     * @param contentAddress
     *                           The content address of the given object.
     * @param object
     *                           The object given to this endpoint to upload.
     *                           These "files" must be named after the sha256sum
     *                           hex string of the object they are describing.
     *                           This hex string is the content address. If that
     *                           address already exists in the object store, it
     *                           is skipped outright. If the computed sha256sum
     *                           of the object and the name of the object do not
     *                           match, an error for that object is returned.
     *                           Otherwise, a successful upload message is
     *                           returned for the object
     * @return a list of metadata which essentially returns the names of these
     *         objects as recorded in the object store.
     */
    @PutMapping("/objects/{contentAddress:.+}")
    public ResponseEntity<UploadContentResponse> uploadObject(
            @PathVariable @NotBlank @Pattern(regexp = "^[0-9a-f]{64}$")
            final String contentAddress, @RequestParam("content")
            final MultipartFile object) {
        ObjectStorageOutcome outcome = storageService
                .storeObject(contentAddress, object);
        switch (outcome) {
        case ADDRESS_INVALID:
            return ResponseEntity.badRequest().body(new UploadContentResponse(
                    contentAddress, "address-invalid"));
        case ADDRESS_CHECKSUM_MISMATCH:
            return ResponseEntity.badRequest().body(new UploadContentResponse(
                    contentAddress, "address-checksum-mimsmatch"));
        case ALREADY_EXISTS:
            return ResponseEntity.ok().body(new UploadContentResponse(
                    contentAddress, "object-already-exists"));
        case STORAGE_ERROR:
            return ResponseEntity.status(SERVER_ERROR).body(
                    new UploadContentResponse(contentAddress, "storage-error"));
        case SUCCESSFUL:
            return ResponseEntity
                    .created(URI.create(
                            String.format("/objects/%s", contentAddress)))
                    .body(new UploadContentResponse(contentAddress,
                            "successful"));
        default:
            return ResponseEntity.status(SERVER_ERROR).body(
                    new UploadContentResponse(contentAddress, "unknown-error"));
        }
    }

    /**
     * Endpoint for checking if an object exists in the store.
     *
     * @param contentAddress
     *                           the content address to check.
     * @return a list specifying for each requested object whether it exists or
     *         not.
     */
    @RequestMapping(path = "/objects/{contentAddress:.+}",
            method = RequestMethod.HEAD)
    public ResponseEntity<Void> objectExists(
            @PathVariable @NotBlank @Pattern(regexp = "^[0-9a-f]{64}$")
            final String contentAddress) {
        if (!storageService.objectExists(contentAddress)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    /**
     * API endpoint for downloading an object. This method allows the client to
     * download an object from the object store.
     *
     * @param contentAddress
     *                           the address of an object.
     * @return the object in question.
     */
    @GetMapping("/objects/{object:.+}")
    public ResponseEntity<Resource> downloadObject(
            @PathVariable @NotBlank @Pattern(regexp = "^[0-9a-f]{64}$")
            final String contentAddress) {
        Resource resource = storageService.getObject(contentAddress);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + contentAddress + "\"")
                    .body(resource);
        }
    }
}
