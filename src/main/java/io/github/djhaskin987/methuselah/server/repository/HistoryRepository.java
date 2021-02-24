package io.github.djhaskin987.methuselah.server.repository;

import io.github.djhaskin987.methuselah.server.model.History;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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
     * @param pageable
     *                           a Spring pagination object.
     * @return A paginated list of History objects.
     */
    Page<History> findByOrganizationId(Long organizationId, Pageable pageable);
}
