package me.dio.controller.dto;

import me.dio.domain.model.Feature;

import java.util.UUID;

import static java.time.ZoneId.of;
import static java.time.ZonedDateTime.now;

public record FeatureDto(UUID id, String icon, String description) {

    public FeatureDto(Feature model) {
        this(model.getId(), model.getIcon(), model.getDescription());
    }

    public Feature toModel() {
        Feature model = new Feature();
        model.setId(this.id);
        model.setIcon(this.icon);
        model.setDescription(this.description);
        model.setCreatedAt(now(of("America/Sao_Paulo")));
        model.setUpdatedAt(now(of("America/Sao_Paulo")));
        return model;
    }
}