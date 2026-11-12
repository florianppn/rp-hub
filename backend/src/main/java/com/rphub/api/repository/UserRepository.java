package com.rphub.api.repository;

import com.rphub.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPublicId(UUID publicId);

    void deleteByPublicId(UUID publicId);

    boolean existsByUsernameOrEmailAddress(String username, String emailAddress);

}
