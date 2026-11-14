package com.rphub.api.controller;

import com.rphub.api.dto.response.PlayerCharacterResponse;
import com.rphub.api.service.PlayerCharacterService;
import com.rphub.api.dto.request.PlayerCharacterCreateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/player-characters")
public class PlayerCharacterController {

    private final PlayerCharacterService playerCharacterService;

    public PlayerCharacterController(PlayerCharacterService playerCharacterService) {
        this.playerCharacterService = playerCharacterService;
    }

    @GetMapping
    public Page<PlayerCharacterResponse> getAll(Pageable pageable) {
        return playerCharacterService.readAll(pageable);
    }

    @GetMapping("/{publicId}")
    public PlayerCharacterResponse get(@PathVariable UUID publicId) {
        return playerCharacterService.read(publicId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerCharacterResponse post(
        @Valid @RequestBody PlayerCharacterCreateRequest request
    ) {
        return playerCharacterService.create(request);
    }

    @DeleteMapping("/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID publicId) {
        playerCharacterService.delete(publicId);
    }

}
