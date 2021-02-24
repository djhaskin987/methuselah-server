package io.github.djhaskin987.methuselah.server.repository;

import io.github.djhaskin987.methuselah.server.model.Series;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Series object.
 */

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    /**
     * Return paginated results.
     *
     * @param historyId
     *                      the history ID.
     * @param pageable
     *                      the Spring pagination object.
     * @return pagination of Series objects.
     */
    Page<Series> findByHistoryId(Long historyId, Pageable pageable);
}
