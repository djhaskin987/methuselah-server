package io.github.djhaskin987.methuselah.server.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Represents a revision of a set of files.
 */
@Entity
@Table(name = "revisions")
public class Revision extends Container {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -7894070529842096658L;

    /**
     * Sequence number of the revision.
     */
    @Column(name = "sequence_number", nullable = false)
    private Long sequenceNumber;

    /**
     * Captures associated with this revision.
     */
    @OneToMany(mappedBy = "revision")
    private Set<Capture> captures;

    /**
     * Parent series that houses this revision.
     */
    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    public final Set<Capture> getCaptures() {
        return captures;
    }

    public final Long getSequenceNumber() {
        return sequenceNumber;
    }

    public final void setSequenceNumber(final Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public final Series getSeries() {
        return series;
    }

    public final void setSeries(final Series series) {
        this.series = series;
    }
}
