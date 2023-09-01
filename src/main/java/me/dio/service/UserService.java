package me.dio.service;

import me.dio.domain.model.User;

import java.util.UUID;

public interface UserService extends CrudService<UUID, User> {
}