package me.dio.controller.dto;

import me.dio.domain.model.Card;

import java.math.BigDecimal;
import java.util.UUID;

import static java.time.ZoneId.of;
import static java.time.ZonedDateTime.now;

public record CardDto(UUID id, String number, BigDecimal limit) {

    public CardDto(Card model) {
        this(model.getId(), model.getNumber(), model.getLimit());
    }

    public Card toModel() {
        return Card.builder()
                .id(this.id)
                .number(this.number)
                .limit(this.limit)
                .createdAt(now(of("America/Sao_Paulo")))
                .updatedAt(now(of("America/Sao_Paulo")))
                .build();
    }
}