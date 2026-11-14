package com.rphub.api.controller;

import com.rphub.api.dto.request.EmailUpdateRequest;
import com.rphub.api.dto.request.PasswordUpdateRequest;
import com.rphub.api.dto.request.UserCreateRequest;
import com.rphub.api.dto.response.UserResponse;
import com.rphub.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponse> getAll(Pageable pageable) {
        return userService.readAll(pageable);
    }

    @GetMapping("/{publicId}")
    public UserResponse get(@PathVariable UUID publicId) {
        return userService.read(publicId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse post(
        @Valid @RequestBody UserCreateRequest request
    ) {
        return userService.create(request);
    }

    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID publicId) {
        userService.delete(publicId);
    }

    @PatchMapping("/{publicId}/password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> patchPassword(
        @PathVariable UUID publicId,
        @Valid @RequestBody PasswordUpdateRequest request
    ) {
        userService.updatePassword(publicId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{publicId}/email")
    public ResponseEntity<UserResponse> patchEmailAddress(
        @PathVariable UUID publicId,
        @Valid @RequestBody EmailUpdateRequest request
    ) {
        UserResponse response = userService.updateEmailAddress(publicId, request);
        return ResponseEntity.ok(response);
    }

}
