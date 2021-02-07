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
public class Series {

    /**
     * Database primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Latest assigned sequence number.
     */
    @Column(name = "last_assigned", nullable = false)
    private long lastAssigned;

    /**
     * Sequence number at which this series starts. /** Sequence number of the
     * revision.
     */
    @Column(name = "sequence_number", nullable = false)
    private Long sequenceNumber;

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
    @OneToMany(mappedBy = "capture")
    private Set<Capture> captures;
}
