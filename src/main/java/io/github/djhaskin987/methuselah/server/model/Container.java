package io.github.djhaskin987.methuselah.server.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Container is an abstract class representing an item which houses other items
 * within the model.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"created", "updated" },
        allowGetters = true)
public abstract class Container implements Serializable {
    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 6286914594966986664L;

    /**
     * Database primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Date created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private Date created;

    /**
     * Date updated.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    @LastModifiedDate
    private Date updated;

    /**
     * Description of this item.
     */
    @Column(name = "description")
    private String description;

    public final Date getCreated() {
        return created;
    }

    public final void setCreated(final Date created) {
        this.created = created;
    }

    public final Date getUpdated() {
        return updated;
    }

    public final void setUpdated(final Date updated) {
        this.updated = updated;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

}
