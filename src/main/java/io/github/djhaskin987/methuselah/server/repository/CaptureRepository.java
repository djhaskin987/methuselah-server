package io.github.djhaskin987.methuselah.server.repository;

import io.github.djhaskin987.methuselah.server.model.Capture;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Capture object.
 */

@Repository
public interface CaptureRepository extends JpaRepository<Capture, Long> {
    /**
     * Grab all the captures by revision ID.
     *
     * @param revisionId
     *                       the ID of the revision for which to grab the
     *                       captures.
     * @param pageable
     *                       the Spring pagination object.
     * @return A paginated list of Captures for that revision.
     */
    Page<Capture> findByRevisionId(Long revisionId, Pageable pageable);
}
