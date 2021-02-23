package io.github.djhaskin987.methuselah.server.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Represents a organization of revisions to a set of files.
 */
@Entity
@Table(name = "organization")
public final class Organization extends NamedContainer {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -65664323490021620L;

    /**
     * Series associated with this organization.
     */
    @OneToMany(mappedBy = "organization")
    private Set<History> histories;

}
