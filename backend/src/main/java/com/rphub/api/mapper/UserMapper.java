package com.rphub.api.mapper;

import com.rphub.api.dto.request.UserCreateRequest;
import com.rphub.api.dto.response.UserResponse;
import com.rphub.api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getPublicId(),
                user.getUsername(),
                user.getEmailAddress(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toEntity(UserCreateRequest request) {
        return new User(
            request.username(),
            request.password(),
            request.emailAddress()
        );
    }

}
