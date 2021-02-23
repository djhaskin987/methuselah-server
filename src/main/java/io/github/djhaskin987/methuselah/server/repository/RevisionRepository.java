package io.github.djhaskin987.methuselah.server.repository;

import io.github.djhaskin987.methuselah.server.model.Revision;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Revision object.
 */

@Repository
public interface RevisionRepository extends JpaRepository<Revision, Long> {
    List<Revision> findBySeriesId(Long seriesId);
}
