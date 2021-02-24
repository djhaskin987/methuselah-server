package io.github.djhaskin987.methuselah.server.repository;

import io.github.djhaskin987.methuselah.server.model.Revision;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Revision object.
 */

@Repository
public interface RevisionRepository extends JpaRepository<Revision, Long> {
    /**
     * Find by series ID.
     *
     * @param seriesId
     *                     the ID of the series.
     * @param pageable
     *                     the paging object from Spring.
     * @return page object of revision objects.
     */
    Page<Revision> findBySeriesId(Long seriesId, Pageable pageable);
}
