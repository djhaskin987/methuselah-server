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
import io.github.djhaskin987.methuselah.server.model.History;
import io.github.djhaskin987.methuselah.server.repository.HistoryRepository;
import io.github.djhaskin987.methuselah.server.repository.OrganizationRepository;

/**
 * Controller for adding content to the object store.
 *
 * This class houses the HTTP api that allows the user to upload content to the
 * object store.
 */
@RestController
@RequestMapping("/api/v1")
@Validated
public class HistoryController {

    /**
     * The repository of organization objects.
     */
    @Autowired
    private OrganizationRepository organizationRepository;

    /**
     * The repository of history objects.
     */
    @Autowired
    private HistoryRepository historyRepository;

    /*
     * Logging provider.
     */
    // private static final Logger logger = LoggerFactory
    // .getLogger(HistoryController.class);

    /**
     * GET endpoint for a list of the histories in an endpoint.
     *
     * @param organizationId
     *                           the ID of the organization for which to list
     *                           the histories.
     * @param pageable
     *                           Spring pagination object.
     * @return all the Histories in the organization given.
     */
    @GetMapping("/organizations/{organization}/histories")
    public Page<History> getHistoriesByOrganizationId(@PathVariable
    final Long organizationId, final Pageable pageable) {
        return historyRepository.findByOrganizationId(organizationId, pageable);
    }

    /**
     * Function that allows the creation of new histories.
     *
     * @param organizationId
     *                           the ID of the organization for which to list
     *                           the histories.
     * @param history
     *                           the new history object to save.
     * @return the new history object as saved.
     */
    // CHECKSTYLE:OFF
    @PostMapping("/organizations/{organization}/histories")
    public History createHistory(@PathVariable
    final Long organizationId, @Valid @RequestBody
    History history) {
        // CHECKSTYLE:ON
        return organizationRepository.findById(organizationId)
                .map(organization -> {
                    history.setOrganization(organization);
                    return historyRepository.save(history);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found with id: " + organizationId));
    }

    /**
     * Function that allows to update an history in-place.
     *
     * @param historyId
     *                           The ID of the history that needs updating.
     * @param historyRequest
     *                           the new history object representing updatess to
     *                           the history.
     * @return The updated History object.
     */
    @PutMapping("/histories/{historyId}")
    public History updateHistory(@PathVariable
    final Long historyId,
            @Valid @RequestBody
            final History historyRequest) {
        return historyRepository.findById(historyId)
                .map(history -> {
                    history.setName(historyRequest.getName());
                    history.setDescription(
                            historyRequest.getDescription());
                    return historyRepository.save(history);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "History not found with id " + historyId));
    }

    /**
     * Function facilitating the deletion of an history.
     *
     * @param historyId
     *                      the ID of the history to be deleted.
     * @return a Response to the caller.
     */
    @DeleteMapping("/histories/{historyId}")
    public ResponseEntity<?> deleteHistory(@PathVariable
    final Long historyId) {
        return historyRepository.findById(historyId)
                .map(history -> {
                    historyRepository.delete(history);
                    return ResponseEntity.ok().build();
                }).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "History not found with id "
                                        + historyId));
    }
}
