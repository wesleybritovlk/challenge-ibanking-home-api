package me.dio.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;

@Builder
@Getter
@Entity(name = "tb_user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_fk", foreignKey = @ForeignKey(name = "tb_user_account_fk"),
            nullable = false, referencedColumnName = "id")
    private Account account;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_fk", foreignKey = @ForeignKey(name = "tb_user_card_fk"),
            nullable = false, referencedColumnName = "id")
    private Card card;
    @OneToMany(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "features_fk", foreignKey = @ForeignKey(name = "tb_user_features_fk"),
            nullable = false, referencedColumnName = "id")
    private List<Feature> features;
    @OneToMany(cascade = CascadeType.ALL, fetch = EAGER)
    @JoinColumn(name = "news_fk", foreignKey = @ForeignKey(name = "tb_user_news_fk"),
            nullable = false, referencedColumnName = "id")
    private List<News> news;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    protected User() {
    }

    protected User(UUID id, String name, Account account, Card card, List<Feature> features, List<News> news, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.card = card;
        this.features = features;
        this.news = news;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}