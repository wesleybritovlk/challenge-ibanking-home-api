package me.dio.controller.dto;

import me.dio.domain.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

import static java.time.ZoneId.of;
import static java.time.ZonedDateTime.now;

public record AccountDto(UUID id, String number, String agency, BigDecimal balance, BigDecimal limit) {
    public AccountDto(Account model) {
        this(model.getId(), model.getNumber(), model.getAgency(), model.getBalance(), model.getLimit());
    }

    public Account toModel() {
        return Account.builder()
                .id(this.id)
                .number(this.number)
                .agency(this.agency)
                .balance(this.balance)
                .limit(this.limit)
                .createdAt(now(of("America/Sao_Paulo")))
                .updatedAt(now(of("America/Sao_Paulo")))
                .build();
    }
}