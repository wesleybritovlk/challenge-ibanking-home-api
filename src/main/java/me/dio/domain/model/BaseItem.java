package me.dio.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public class BaseItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String icon;
    private String description;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    protected BaseItem() {
    }

    protected BaseItem(UUID id, String icon, String description, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.icon = icon;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}