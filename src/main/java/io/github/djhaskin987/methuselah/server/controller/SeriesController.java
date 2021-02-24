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
import io.github.djhaskin987.methuselah.server.model.Series;
import io.github.djhaskin987.methuselah.server.payload.NamedContainerRequest;
import io.github.djhaskin987.methuselah.server.payload.NewSeriesRequest;
import io.github.djhaskin987.methuselah.server.repository.SeriesRepository;
import io.github.djhaskin987.methuselah.server.repository.HistoryRepository;

/**
 * Controller for adding content to the object store.
 *
 * This class houses the HTTP api that allows the user to upload content to the
 * object store.
 */
@RestController
@RequestMapping("/api/v1")
@Validated
public class SeriesController {

    /**
     * The repository of history objects.
     */
    @Autowired
    private HistoryRepository historyRepository;

    /**
     * The repository of series objects.
     */
    @Autowired
    private SeriesRepository seriesRepository;

    /*
     * Logging provider.
     */
    // private static final Logger logger = LoggerFactory
    // .getLogger(SeriesController.class);

    /**
     * GET endpoint for a list of the series in an endpoint.
     *
     * @param historyId
     *                      the ID of the history for which to list the series.
     * @param pageable
     *                      the Spring pagination object.
     * @return all the Series in the history given.
     */
    @GetMapping("/histories/{history}/series")
    public Page<Series> getSeriesByHistoryId(@PathVariable
    final Long historyId, final Pageable pageable) {
        return seriesRepository.findByHistoryId(historyId, pageable);
    }

    /**
     * Function that allows the creation of new series.
     *
     * @param historyId
     *                          the ID of the history for which to list the
     *                          series.
     * @param seriesRequest
     *                          the new series object to save.
     * @return the new series object as saved.
     */
    // CHECKSTYLE:OFF
    @PostMapping("/histories/{historyId}/series")
    public Series createSeries(@PathVariable
    final Long historyId, @Valid @RequestBody
    NewSeriesRequest seriesRequest) {
        // CHECKSTYLE:ON
        return historyRepository.findById(historyId)
                .map(history -> {
                    Series series = new Series();
                    series.setName(seriesRequest.getName());
                    series.setDescription(seriesRequest.getDescription());
                    series.setStartSequenceNumber(
                            seriesRequest.getStartSequenceNumber());
                    series.setLastAssigned(
                            seriesRequest.getStartSequenceNumber());
                    series.setHistory(history);
                    return seriesRepository.save(series);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "History not found with id: " + historyId));
    }

    /**
     * Function that allows to update an series in-place.
     *
     * @param seriesId
     *                          The ID of the series that needs updating.
     * @param seriesRequest
     *                          the name and description of the existing series
     *                          object.
     * @return The updated Series object.
     */
    @PutMapping("/series/{seriesId}")
    public Series updateSeries(@PathVariable
    final Long seriesId,
            @Valid @RequestBody
            final NamedContainerRequest seriesRequest) {
        return seriesRepository.findById(seriesId)
                .map(series -> {
                    series.setName(seriesRequest.getName());
                    series.setDescription(
                            seriesRequest.getDescription());
                    return seriesRepository.save(series);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "Series not found with id " + seriesId));
    }

    /**
     * Function facilitating the deletion of an series.
     *
     * @param seriesId
     *                     the ID of the series to be deleted.
     * @return a Response to the caller.
     */
    @DeleteMapping("/series/{seriesId}")
    public ResponseEntity<?> deleteSeries(@PathVariable
    final Long seriesId) {
        return seriesRepository.findById(seriesId)
                .map(series -> {
                    seriesRepository.delete(series);
                    return ResponseEntity.ok().build();
                }).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Series not found with id "
                                        + seriesId));
    }
}
