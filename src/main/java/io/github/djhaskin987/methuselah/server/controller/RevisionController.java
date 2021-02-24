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
import io.github.djhaskin987.methuselah.server.model.Revision;
import io.github.djhaskin987.methuselah.server.repository.RevisionRepository;
import io.github.djhaskin987.methuselah.server.repository.SeriesRepository;

/**
 * Controller for adding content to the object store.
 *
 * This class houses the HTTP api that allows the user to upload content to the
 * object store.
 */
@RestController
@RequestMapping("/api/v1")
@Validated
public class RevisionController {

    /**
     * The repository of series objects.
     */
    @Autowired
    private SeriesRepository seriesRepository;

    /**
     * The repository of revision objects.
     */
    @Autowired
    private RevisionRepository revisionRepository;

    /*
     * Logging provider.
     */
    // private static final Logger logger = LoggerFactory
    // .getLogger(RevisionController.class);

    /**
     * GET endpoint for a list of the revisions in an endpoint.
     *
     * @param seriesId
     *                     the ID of the series for which to list the revisions.
     * @param pageable
     *                     the Spring pagination object.
     * @return all the Revision in the series given.
     */
    @GetMapping("/series/{series}/revisions")
    public Page<Revision> getRevisionBySeriesId(@PathVariable
    final Long seriesId, final Pageable pageable) {
        return revisionRepository.findBySeriesId(seriesId, pageable);
    }

    /**
     * Function that allows the creation of new revision.
     *
     * @param seriesId
     *                            the ID of the series for which to list the
     *                            revision.
     * @param revisionRequest
     *                            the new revision object to save.
     * @return the new revision object as saved.
     */
    // CHECKSTYLE:OFF
    @PostMapping("/series/{seriesId}/revisions")
    public Revision createRevision(@PathVariable
    final Long seriesId, @Valid @RequestBody
    Revision revisionRequest) {
        // CHECKSTYLE:ON
        return seriesRepository.findById(seriesId)
                .map(series -> {
                    Revision revision = new Revision();
                    revision.setDescription(revisionRequest.getDescription());
                    revision.setSequenceNumber(
                            revisionRequest.getSequenceNumber());
                    revision.setSeries(series);
                    return revisionRepository.save(revision);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "Series not found with id: " + seriesId));
    }

    /**
     * Function that allows to update an revision in-place.
     *
     * @param revisionId
     *                            The ID of the revision that needs updating.
     * @param revisionRequest
     *                            the name and description of the existing
     *                            revision object.
     * @return The updated Revision object.
     */
    @PutMapping("/revisions/{revisionId}")
    public Revision updateRevision(@PathVariable
    final Long revisionId,
            @Valid @RequestBody
            final Revision revisionRequest) {
        return revisionRepository.findById(revisionId)
                .map(revision -> {
                    revision.setSequenceNumber(
                            revisionRequest.getSequenceNumber());
                    revision.setDescription(
                            revisionRequest.getDescription());
                    return revisionRepository.save(revision);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "Revision not found with id " + revisionId));
    }

    /**
     * Function facilitating the deletion of an revision.
     *
     * @param revisionId
     *                       the ID of the revision to be deleted.
     * @return a Response to the caller.
     */
    @DeleteMapping("/revisions/{revisionId}")
    public ResponseEntity<?> deleteRevision(@PathVariable
    final Long revisionId) {
        return revisionRepository.findById(revisionId)
                .map(revision -> {
                    revisionRepository.delete(revision);
                    return ResponseEntity.ok().build();
                }).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Revision not found with id "
                                        + revisionId));
    }
}
