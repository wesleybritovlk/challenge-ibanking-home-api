package me.dio.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.service.UserService;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static java.time.ZonedDateTime.now;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    /**
     * ID de usuário utilizado na Santander Dev Week 2023.
     * Por isso, vamos criar algumas regras para mantê-lo integro.
     */
    private static final Long UNCHANGEABLE_USER_ID = 1L;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private final UserRepository repository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
        var findAll = repository.findAll();
        return findAll;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(NotFoundException::new);
    public User findById(UUID id) {
        User findById = repository.findById(id).orElseThrow(NotFoundException::new);
        return findById;
    }

    @Transactional
    public User create(User userToCreate) {
        ofNullable(userToCreate).orElseThrow(() -> new BusinessException("User to create must not be null."));
        ofNullable(userToCreate.getAccount()).orElseThrow(() -> new BusinessException("User account must not be null."));
        ofNullable(userToCreate.getCard()).orElseThrow(() -> new BusinessException("User card must not be null."));
        if (repository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new BusinessException("account number already exists.");
        }
        if (repository.existsByCardNumber(userToCreate.getCard().getNumber())) {
            throw new BusinessException("card number already exists.");
        }
        return this.userRepository.save(userToCreate);
        User create = repository.save(userToCreate);
        return create;
    }

    @Transactional
    public User update(Long id, User userToUpdate) {
        this.validateChangeableId(id, "updated");
        User dbUser = this.findById(id);
        if (!dbUser.getId().equals(userToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbUser.setName(userToUpdate.getName());
        dbUser.setAccount(userToUpdate.getAccount());
        dbUser.setCard(userToUpdate.getCard());
        dbUser.setFeatures(userToUpdate.getFeatures());
        dbUser.setNews(userToUpdate.getNews());

        return this.userRepository.save(dbUser);
    public User update(UUID id, User userToUpdate) {
        User dbUser = findById(id);
        if (!dbUser.getId().equals(userToUpdate.getId())) throw new BusinessException("Update IDs must be the same.");
        dbUser = User.builder().id(id).name(userToUpdate.getName()).account(userToUpdate.getAccount()).card(userToUpdate.getCard()).features(userToUpdate.getFeatures()).news(userToUpdate.getNews()).updatedAt(now(ZoneId.of("America/Sao_Paulo"))).build();
        User update = repository.save(dbUser);
        return update;
    }

    @Transactional
    public void delete(Long id) {
        this.validateChangeableId(id, "deleted");
        User dbUser = this.findById(id);
        this.userRepository.delete(dbUser);
    }

    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("User with ID %d can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}


    public void delete(UUID id) {
        User dbUser = findById(id);
        repository.delete(dbUser);
    }
}