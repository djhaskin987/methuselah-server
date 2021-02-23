package io.github.djhaskin987.methuselah.server.repository;

import io.github.djhaskin987.methuselah.server.model.Series;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Series object.
 */

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findByHistoryId(Long historyId);
}
