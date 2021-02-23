package io.github.djhaskin987.methuselah.server.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.persistence.Column;
import javax.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
