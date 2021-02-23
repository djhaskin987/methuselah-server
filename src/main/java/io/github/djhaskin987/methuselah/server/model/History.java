package io.github.djhaskin987.methuselah.server.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Represents a series of revisions to a set of files.
 */
@Entity
@Table(name = "history")
public final class History extends Container {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 562913777254526430L;

    /**
     * Database primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Latest assigned sequence number.
     */
    @Column(name = "last_assigned")
    private Long lastAssigned;

    /**
     * Series associated with this history.
     */
    @OneToMany(targetEntity = Series.class, mappedBy = "history")
    private Set<Series> series;

    /**
     * Parent organization that houses this series.
     */
    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
