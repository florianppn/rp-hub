package com.rphub.api.repository;

import com.rphub.api.entity.PlayerCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlayerCharacterRepository extends JpaRepository<PlayerCharacter, Long> {

    Optional<PlayerCharacter> findByPublicId(UUID publicId);

    long deleteByPublicId(UUID publicId);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

}
