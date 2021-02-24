package io.github.djhaskin987.methuselah.server.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Represents a series of revisions to a set of files.
 */
@Entity
@Table(name = "series")
public final class Series extends NamedContainer {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 6501193021270098401L;

    /**
     * Latest assigned sequence number.
     */
    @Column(name = "last_assigned")
    private Long lastAssigned;

    /**
     * Sequence number at which this series starts.
     */
    @Column(name = "start_sequence_number", nullable = false)
    private Long startSequenceNumber;

    /**
     * Revisions associated with this series.
     */
    @OneToMany(targetEntity = Revision.class, mappedBy = "series")
    private Set<Revision> revisions;

    /**
     * Parent history that houses this series.
     */
    @ManyToOne
    @JoinColumn(name = "history_id", nullable = false)
    private History history;

    public Long getLastAssigned() {
        return lastAssigned;
    }

    public void setLastAssigned(final Long lastAssigned) {
        this.lastAssigned = lastAssigned;
    }

    public Long getStartSequenceNumber() {
        return startSequenceNumber;
    }

    public void setStartSequenceNumber(final Long startSequenceNumber) {
        this.startSequenceNumber = startSequenceNumber;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(final History history) {
        this.history = history;
    }
}
