package io.github.djhaskin987.methuselah.server.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.djhaskin987.methuselah.server.exception.ResourceNotFoundException;
import io.github.djhaskin987.methuselah.server.model.Organization;
import io.github.djhaskin987.methuselah.server.repository.OrganizationRepository;

/**
 * Controller for adding content to the object store.
 *
 * This class houses the HTTP api that allows the user to upload content to the
 * object store.
 */
@RestController
@RequestMapping("/api/v1")
@Validated
public class OrganizationController {

    /**
     * The repository of organization objects.
     */
    @Autowired
    private OrganizationRepository organizationRepository;

    /*
     * Logging provider.
     */
    // private static final Logger logger = LoggerFactory
    // .getLogger(OrganizationController.class);

    /**
     * Function that provides read access to organizations.
     *
     * @param pageable
     *                     an object that is an artifact of Spring, allowing for
     *                     paged results.
     * @return A page object allowing the caller to list organizations
     *         paginated.
     */
    @GetMapping("/organizations")
    public Page<Organization> getOrganizations(final Pageable pageable) {
        return organizationRepository.findAll(pageable);
    }

    /**
     * Function that allows the creation of new organizations.
     *
     * @param organization
     *                         the new organization object to save.
     * @return the new organization object as saved.
     */
    @PostMapping("/organizations")
    public Organization createOrganization(@Valid @RequestBody
    final Organization organization) {
        return organizationRepository.save(organization);
    }

    /**
     * Function that allows to update an organization in-place.
     *
     * @param organizationId
     *                                The ID of the organization that needs
     *                                updating.
     * @param organizationRequest
     *                                the new organization object representing
     *                                updatess to the organization.
     * @return The updated Organization object.
     */
    @PutMapping("/organizations/{organizationId}")
    public Organization updateOrganization(@PathVariable
    final Long organizationId,
            @Valid @RequestBody
            final Organization organizationRequest) {
        return organizationRepository.findById(organizationId)
                .map(organization -> {
                    organization.setName(organizationRequest.getName());
                    organization.setDescription(
                            organizationRequest.getDescription());
                    return organizationRepository.save(organization);
                }).orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found with id " + organizationId));
    }

    /**
     * Function facilitating the deletion of an organization.
     *
     * @param organizationId
     *                           the ID of the organization to be deleted.
     * @return a Response to the caller.
     */
    @DeleteMapping("/organizations/{organizationId}")
    public ResponseEntity<?> deleteOrganization(@PathVariable
    final Long organizationId) {
        return organizationRepository.findById(organizationId)
                .map(organization -> {
                    organizationRepository.delete(organization);
                    return ResponseEntity.ok().build();
                }).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Organization not found with id "
                                        + organizationId));
    }
}
