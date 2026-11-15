package com.rphub.api.repository;

import com.rphub.api.entity.BankAccount;
import com.rphub.api.entity.PlayerCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByPublicId(UUID publicId);

    boolean existsByPlayerCharacter(PlayerCharacter playerCharacter);

    Long deleteByPublicId(UUID publicId);

}
