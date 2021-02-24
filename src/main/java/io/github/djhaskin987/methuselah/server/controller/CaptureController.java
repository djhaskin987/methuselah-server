package io.github.djhaskin987.methuselah.server.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.djhaskin987.methuselah.server.exception.ResourceNotFoundException;
import io.github.djhaskin987.methuselah.server.model.Capture;
import io.github.djhaskin987.methuselah.server.repository.CaptureRepository;
import io.github.djhaskin987.methuselah.server.repository.RevisionRepository;

/**
 * Controller for adding content to the object store.
 *
 * This class houses the HTTP api that allows the user to upload content to the
 * object store.
 */
@RestController
@RequestMapping("/api/v1")
@Validated
public class CaptureController {

    /**
     * The repository of revision objects.
     */
    @Autowired
    private RevisionRepository revisionRepository;

    /**
     * The repository of capture objects.
     */
    @Autowired
    private CaptureRepository captureRepository;

    /*
     * Logging provider.
     */
    // private static final Logger logger = LoggerFactory
    // .getLogger(CaptureController.class);

    /**
     * GET endpoint for a list of the capture in an endpoint.
     *
     * @param revisionId
     *                       the ID of the revision for which to list the
     *                       capture.
     * @param pageable
     *                       the Spring pagination object.
     * @return all the Captures in the revision given.
     */
    @GetMapping("/revisions/{revision}/captures")
    public Page<Capture> getCaptureByRevisionId(@PathVariable
    final Long revisionId, final Pageable pageable) {
        return captureRepository.findByRevisionId(revisionId, pageable);
    }

    /**
     * Function that allows the creation of new captures.
     *
     * @param revisionId
     *                           the ID of the revision for which to list the
     *                           captures.
     * @param captureRequest
     *                           the new capture object to save.
     * @return the new capture object as saved.
     */
    // CHECKSTYLE:OFF
    @PostMapping("/revisions/{revisionId}/captures")
    public Capture createCapture(@PathVariable
    final Long revisionId, @Valid @RequestBody
    Capture captureRequest) {
        // CHECKSTYLE:ON
        return revisionRepository.findById(revisionId)
                .map(revision -> {
                    Capture capture = new Capture();
                    capture.setDescription(captureRequest.getDescription());
                    capture.setSequenceNumber(
                            captureRequest.getSequenceNumber());
                    capture.setRevision(revision);
                    return captureRepository.save(capture);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "Revision not found with id: " + revisionId));
    }

    /**
     * Function that allows to update an capture in-place.
     *
     * @param captureId
     *                           The ID of the capture that needs updating.
     * @param captureRequest
     *                           the name and description of the existing
     *                           capture object.
     * @return The updated Capture object.
     */
    @PutMapping("/captures/{captureId}")
    public Capture updateCapture(@PathVariable
    final Long captureId,
            @Valid @RequestBody
            final Capture captureRequest) {
        return captureRepository.findById(captureId)
                .map(capture -> {
                    capture.setSequenceNumber(
                            captureRequest.getSequenceNumber());
                    capture.setDescription(
                            captureRequest.getDescription());
                    return captureRepository.save(capture);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "Capture not found with id " + captureId));
    }

    /**
     * Function facilitating the deletion of an capture.
     *
     * @param captureId
     *                      the ID of the capture to be deleted.
     * @return a Response to the caller.
     */
    @DeleteMapping("/captures/{captureId}")
    public ResponseEntity<?> deleteCapture(@PathVariable
    final Long captureId) {
        return captureRepository.findById(captureId)
                .map(capture -> {
                    captureRepository.delete(capture);
                    return ResponseEntity.ok().build();
                }).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Capture not found with id "
                                        + captureId));
    }
}
