package me.dio.controller.dto;

import me.dio.domain.model.News;

import java.util.UUID;

import static java.time.ZoneId.of;
import static java.time.ZonedDateTime.now;

public record NewsDto(UUID id, String icon, String description) {

    public NewsDto(News model) {
        this(model.getId(), model.getIcon(), model.getDescription());
    }

    public News toModel() {
        News model = new News();
        model.setId(this.id);
        model.setIcon(this.icon);
        model.setDescription(this.description);
        model.setCreatedAt(now(of("America/Sao_Paulo")));
        model.setUpdatedAt(now(of("America/Sao_Paulo")));
        return model;
    }
}

