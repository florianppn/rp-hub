package com.rphub.api.mapper;

import com.rphub.api.dto.request.PlayerCharacterCreateRequest;
import com.rphub.api.dto.response.PlayerCharacterResponse;
import com.rphub.api.entity.PlayerCharacter;
import com.rphub.api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PlayerCharacterMapper {

    public PlayerCharacterResponse toResponse(PlayerCharacter playerCharacter) {
        return new PlayerCharacterResponse(
                playerCharacter.getPublicId(),
                playerCharacter.getFirstName(),
                playerCharacter.getLastName(),
                playerCharacter.getDateOfBirth(),
                playerCharacter.getGender(),
                playerCharacter.getHeight(),
                playerCharacter.getStatus(),
                playerCharacter.getCreatedAt(),
                playerCharacter.getUpdatedAt()
        );
    }

    public PlayerCharacter toEntity(User user, PlayerCharacterCreateRequest request) {
        return new PlayerCharacter(
                user,
                request.firstName(),
                request.lastName(),
                request.dateOfBirth(),
                request.gender(),
                request.height()
        );
    }

}
