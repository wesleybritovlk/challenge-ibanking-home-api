package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.controller.dto.UserDto;
import me.dio.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;
import static java.util.Objects.requireNonNull;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Tag(name = "Users Controller", description = "RESTful API for managing users.")
public record UserController(UserService service) {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<UserDto>> getAll() {
        long startTime = currentTimeMillis();
        var users = service.findAll();
        var getAll = ResponseEntity.ok(users.stream().map(UserDto::new).collect(Collectors.toList()));
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("M GET /users {} : Returned {} users in {}ms", getAll.getStatusCode(), requireNonNull(getAll.getBody()).size(), endTime);
        return getAll;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Retrieve a specific user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        long startTime = currentTimeMillis();
        var user = service.findById(id);
        var getById = ResponseEntity.ok(new UserDto(user));
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("M GET /users/ID {} : Returned user ID: {} in {}ms", getById.getStatusCode(), requireNonNull(getById.getBody()).id(), endTime);
        return getById;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user and return the created user's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        long startTime = currentTimeMillis();
        var user = service.create(userDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        var post = ResponseEntity.created(location).body(new UserDto(user));
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("M POST /users {} : Created user ID: {} in {}ms", post.getStatusCode(), requireNonNull(post.getBody()).id(), endTime);
        return post;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Update the data of an existing user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @RequestBody UserDto userDto) {
        long startTime = currentTimeMillis();
        var user = service.update(id, userDto.toModel());
        var put = ResponseEntity.ok(new UserDto(user));
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("M PUT /users/ID {} : Updated user ID: {} in {}ms", put.getStatusCode(), requireNonNull(put.getBody()).id(), endTime);
        return put;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Delete an existing user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        long startTime = currentTimeMillis();
        service.delete(id);
        ResponseEntity<Void> delete = ResponseEntity.noContent().build();
        long endTime = currentTimeMillis() - startTime;
        LOGGER.info("M DELETE /users/ID {} : Deleted user ID: {} in {}ms", delete.getStatusCode(), id, endTime);
        return delete;
    }
}