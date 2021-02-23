package io.github.djhaskin987.methuselah.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a capture in the database.
 */
@Entity
@Table(name = "captures")
@EntityListeners(AuditingEntityListener.class)
public class Capture {

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Content address, which is usually the sha512 hash of the object/file.
     */
    @NotBlank
    @Column(name = "content_address", nullable = false)
    private String contentAddress;

    /**
     * Filename for when the file is re-hydrated. it gets put here.
     */
    @NotBlank
    @Column(name = "file_address", nullable = false)
    private String fileAddress;

    /**
     * Parent revision housing this capture.
     */
    @ManyToOne
    @JoinColumn(name = "revision_id", nullable = false)
    private Revision revision;

    /**
     * Getter for content address.
     *
     * @return content address.
     */
    public String getContentAddress() {
        return contentAddress;
    }

    /**
     * Getter for file address.
     *
     * @return file address.
     */
    public String getFileAddress() {
        return fileAddress;
    }
}
