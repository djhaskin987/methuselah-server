package io.github.djhaskin987.methuselah.server.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.persistence.Column;
import javax.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A NamedContainer is a container that has a name. It encapsulates the name
 * field in addition to all the other fields in a container table.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class NamedContainer extends Container {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -3120436135235541703L;

    /**
     * Name of this item.
     */
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

}
