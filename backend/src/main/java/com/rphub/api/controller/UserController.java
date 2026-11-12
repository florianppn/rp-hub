package com.rphub.api.controller;

import com.rphub.api.dto.request.EmailUpdateRequest;
import com.rphub.api.dto.request.PasswordUpdateRequest;
import com.rphub.api.dto.request.UserCreateRequest;
import com.rphub.api.dto.response.UserResponse;
import com.rphub.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<UserResponse> get(@PathVariable UUID publicId) {
        UserResponse response = userService.read(publicId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> post(
        @Valid @RequestBody UserCreateRequest request
    ) {
        UserResponse response = userService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.publicId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> delete(@PathVariable UUID publicId) {
        userService.delete(publicId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{publicId}/password")
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
