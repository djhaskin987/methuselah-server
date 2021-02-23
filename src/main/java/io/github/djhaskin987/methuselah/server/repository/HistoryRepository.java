package io.github.djhaskin987.methuselah.server.repository;

import io.github.djhaskin987.methuselah.server.model.History;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Series object.
 */

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    /**
     * Method allowing the finding of organizations by ID.
     *
     * @param organizationId
     *                           the organizationId of the organization to
     *                           search in for child history objects.
     * @return A list of History objects.
     */
    List<History> findByOrganizationId(Long organizationId);

}
