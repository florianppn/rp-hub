package com.rphub.api.service;

import com.rphub.api.dto.request.LoginRequest;
import com.rphub.api.dto.response.TokenResponse;
import com.rphub.api.entity.User;
import com.rphub.api.exception.InvalidCredentialsException;
import com.rphub.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        return tokenService.generate(user);
    }
}
