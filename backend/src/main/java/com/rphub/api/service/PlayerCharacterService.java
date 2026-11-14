package com.rphub.api.service;

import com.rphub.api.dto.request.PlayerCharacterCreateRequest;
import com.rphub.api.dto.response.PlayerCharacterResponse;
import com.rphub.api.entity.PlayerCharacter;
import com.rphub.api.entity.User;
import com.rphub.api.exception.ConflictException;
import com.rphub.api.exception.ResourceNotFoundException;
import com.rphub.api.mapper.PlayerCharacterMapper;
import com.rphub.api.repository.PlayerCharacterRepository;
import com.rphub.api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PlayerCharacterService {

    private final PlayerCharacterRepository playerCharacterRepository;
    private final UserRepository userRepository;
    private final PlayerCharacterMapper playerCharacterMapper;

    public PlayerCharacterService(
            PlayerCharacterRepository playerCharacterRepository,
            UserRepository userRepository,
            PlayerCharacterMapper playerCharacterMapper
    ) {
        this.playerCharacterRepository = playerCharacterRepository;
        this.userRepository = userRepository;
        this.playerCharacterMapper = playerCharacterMapper;
    }

    @Transactional(readOnly = true)
    public Page<PlayerCharacterResponse> readAll(Pageable pageable) {
        return playerCharacterRepository.findAll(pageable).map(playerCharacterMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PlayerCharacterResponse read(UUID publicId) {
        PlayerCharacter playerCharacter = playerCharacterRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Personnage non trouvé avec l'ID : " + publicId));
        return playerCharacterMapper.toResponse(playerCharacter);
    }

    @Transactional
    public PlayerCharacterResponse create(PlayerCharacterCreateRequest request) {
        User user = userRepository.findByPublicId(request.userPublicId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utilisateur non trouvé avec l'ID : " + request.userPublicId())
                );
        if(!playerCharacterRepository.existsByFirstNameAndLastName(request.firstName(), request.lastName())) {
            throw new ConflictException("Un personnage existe déjà avec ce prénom et ce nom : " +
                    request.firstName() + " " + request.lastName()
            );
        }
        PlayerCharacter playerCharacter = playerCharacterMapper.toEntity(user, request);
        return playerCharacterMapper.toResponse(playerCharacterRepository.save(playerCharacter));
    }

    @Transactional
    public void delete(UUID publicId) {
        if(playerCharacterRepository.deleteByPublicId(publicId) == 0) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + publicId);
        }
    }



}
