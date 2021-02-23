package io.github.djhaskin987.methuselah.server.repository;

import io.github.djhaskin987.methuselah.server.model.Organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Series object.
 */

@Repository
public interface OrganizationRepository
        extends JpaRepository<Organization, Long> {

}
