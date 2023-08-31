package me.dio.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@Getter
@Entity(name = "tb_account")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "tb_account_number_uk", columnNames = {"number"})})
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private String agency;
    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal balance;
    @Column(name = "additional_limit", nullable = false, precision = 13, scale = 2)
    private BigDecimal limit;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    protected Account() {
    }

    protected Account(UUID id, String number, String agency, BigDecimal balance, BigDecimal limit, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.number = number;
        this.agency = agency;
        this.balance = balance;
        this.limit = limit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}