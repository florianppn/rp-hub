package com.rphub.api.service;

import com.rphub.api.dto.request.EmailUpdateRequest;
import com.rphub.api.dto.request.PasswordUpdateRequest;
import com.rphub.api.dto.request.UserCreateRequest;
import com.rphub.api.dto.response.UserResponse;
import com.rphub.api.entity.User;
import com.rphub.api.exception.ConflictException;
import com.rphub.api.mapper.UserMapper;
import com.rphub.api.repository.UserRepository;
import com.rphub.api.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly=true)
    public Page<UserResponse> readAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponse);
    }

    @Transactional(readOnly=true)
    public UserResponse read(UUID publicId) {
        User user = loadUser(publicId);
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByUsernameOrEmailAddress(request.username(), request.emailAddress())) {
            throw new ConflictException(
                    "Un utilisateur existe déjà avec ce nom : " + request.username() +
                    " ou cet email : " + request.emailAddress()
            );
        }
        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public void delete(UUID publicId) {
        if (userRepository.deleteByPublicId(publicId) == 0) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + publicId);
        }
    }

    @Transactional
    public void updatePassword(UUID publicId, PasswordUpdateRequest request) {
        User user = loadUser(publicId);
        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ConflictException("Mot de passe incorrect");
        }
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
    }

    @Transactional
    public UserResponse updateEmailAddress(UUID publicId, EmailUpdateRequest request) {
        User user = loadUser(publicId);
        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ConflictException("Mot de passe incorrect");
        }
        user.setEmailAddress(request.newEmailAddress());
        return userMapper.toResponse(user);
    }

    private User loadUser(UUID publicId) {
        return userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + publicId));
    }

}
