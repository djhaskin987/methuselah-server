package io.github.djhaskin987.methuselah.server.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a revision of a set of files.
 */
@Entity
@Table(name = "revisions")
@EntityListeners(AuditingEntityListener.class)
public class Revision {

    /**
     * Database primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Sequence number of the revision.
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
    @OneToMany(mappedBy = "revision")
    private Set<Capture> captures;

    /**
     * Parent series that houses this revision.
     */
    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;
}
