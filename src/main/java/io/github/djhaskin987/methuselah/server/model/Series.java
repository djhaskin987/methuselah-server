package io.github.djhaskin987.methuselah.server.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a series of revisions to a set of files.
 */
@Entity
@Table(name = "series")
@EntityListeners(AuditingEntityListener.class)
public final class Series {

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
     * Sequence number at which this series starts. /** Sequence number of the
     * revision.
     */
    @Column(name = "start_sequence_number", nullable = false)
    private Long startSequenceNumber;

    /**
     * Author name.
     */
    @Column(name = "author_name", nullable = false)
    private String authorName;

    /**
     * Author email.
     */
    @Column(name = "author_email", nullable = false)
    private String authorEmail;

    /**
     * Author message.
     */
    @Column(name = "author_message", nullable = false)
    private String authorMessage;

    /**
     * Time of composition.
     */
    @Column(name = "authored_date", nullable = false)
    private Date authoredDate;

    /**
     * Captures associated with this revision.
     */
    @OneToMany(mappedBy = "series")
    private Set<Revision> revisions;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(final String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorMessage() {
        return authorMessage;
    }

    public void setAuthorMessage(final String authorMessage) {
        this.authorMessage = authorMessage;
    }

    public Date getAuthoredDate() {
        return authoredDate;
    }

    public void setAuthoredDate(final Date authoredDate) {
        this.authoredDate = authoredDate;
    }

}
