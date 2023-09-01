package me.dio.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.service.UserService;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;
import static java.time.ZonedDateTime.now;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Transactional(readOnly = true)
    public List<User> findAll() {
        long startTime = currentTimeMillis();
        var findAll = repository.findAll();
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("DB FindAll : Returned {} users in {}ms", findAll.size(), endTime);
        return findAll;
    }

    @Transactional(readOnly = true)
    public User findById(UUID id) {
        long startTime = currentTimeMillis();
        User findById = repository.findById(id).orElseThrow(NotFoundException::new);
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("DB FindById : Returned user ID: {} in {}ms", findById.getId(), endTime);
        return findById;
    }

    @Transactional
    public User create(User userToCreate) {
        long startTime = currentTimeMillis();
        ofNullable(userToCreate).orElseThrow(() -> new BusinessException("User to create must not be null."));
        ofNullable(userToCreate.getAccount()).orElseThrow(() -> new BusinessException("User account must not be null."));
        ofNullable(userToCreate.getCard()).orElseThrow(() -> new BusinessException("User card must not be null."));
        if (repository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new BusinessException("account number already exists.");
        }
        if (repository.existsByCardNumber(userToCreate.getCard().getNumber())) {
            throw new BusinessException("card number already exists.");
        }
        User create = repository.save(userToCreate);
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("DB Create : Persisted user ID: {} in {}ms", create.getId(), endTime);
        return create;
    }

    @Transactional
    public User update(UUID id, User userToUpdate) {
        long startTime = currentTimeMillis();
        User dbUser = findById(id);
        if (!dbUser.getId().equals(userToUpdate.getId())) throw new BusinessException("Update IDs must be the same.");
        dbUser = User.builder().id(id).name(userToUpdate.getName()).account(userToUpdate.getAccount()).card(userToUpdate.getCard()).features(userToUpdate.getFeatures()).news(userToUpdate.getNews()).updatedAt(now(ZoneId.of("America/Sao_Paulo"))).build();
        User update = repository.save(dbUser);
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("DB Update : Updated user ID: {} in {}ms", update.getId(), endTime);
        return update;
    }

    @Transactional
    public void delete(UUID id) {
        long startTime = currentTimeMillis();
        User dbUser = findById(id);
        repository.delete(dbUser);
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("DB Delete : Deleted user ID: {} in {}ms", id, endTime);
    }
}