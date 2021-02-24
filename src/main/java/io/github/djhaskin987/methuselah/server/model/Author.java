package io.github.djhaskin987.methuselah.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents an author of a capture.
 */
@Entity
@Table(name = "authors")
@EntityListeners(AuditingEntityListener.class)
public class Author {

    /**
     * Database primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Name of the author.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Email of the author.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public final Long getId() {
        return id;
    }

    public final void setId(final Long id) {
        this.id = id;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(final String email) {
        this.email = email;
    }
}
