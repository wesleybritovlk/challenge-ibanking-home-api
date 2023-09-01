package me.dio.controller.dto;

import me.dio.domain.model.User;

import java.util.List;
import java.util.UUID;

import static java.time.ZoneId.of;
import static java.time.ZonedDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record UserDto(
        UUID id,
        String name,
        AccountDto account,
        CardDto card,
        List<FeatureDto> features,
        List<NewsDto> news) {

    public UserDto(User model) {
        this(
                model.getId(),
                model.getName(),
                ofNullable(model.getAccount()).map(AccountDto::new).orElse(null),
                ofNullable(model.getCard()).map(CardDto::new).orElse(null),
                ofNullable(model.getFeatures()).orElse(emptyList()).stream().map(FeatureDto::new).collect(toList()),
                ofNullable(model.getNews()).orElse(emptyList()).stream().map(NewsDto::new).collect(toList())
        );
    }

    public User toModel() {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .account(ofNullable(this.account).map(AccountDto::toModel).orElse(null))
                .card(ofNullable(this.card).map(CardDto::toModel).orElse(null))
                .features(ofNullable(this.features).orElse(emptyList()).stream().map(FeatureDto::toModel).collect(toList()))
                .news(ofNullable(this.news).orElse(emptyList()).stream().map(NewsDto::toModel).collect(toList()))
                .createdAt(now(of("America/Sao_Paulo")))
                .updatedAt(now(of("America/Sao_Paulo")))
                .build();
    }

}

