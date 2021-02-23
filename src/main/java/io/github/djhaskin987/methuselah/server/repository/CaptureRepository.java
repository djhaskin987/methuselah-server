package io.github.djhaskin987.methuselah.server.repository;

import io.github.djhaskin987.methuselah.server.model.Capture;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Capture object.
 */

@Repository
public interface CaptureRepository extends JpaRepository<Capture, Long> {
    List<Capture> findByRevisionId(Long revisionId);

}
