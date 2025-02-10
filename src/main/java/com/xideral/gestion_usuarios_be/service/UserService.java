package com.xideral.gestion_usuarios_be.service;

import static java.lang.String.format;

import com.xideral.gestion_usuarios_be.dto.user.UserDto;
import com.xideral.gestion_usuarios_be.entity.User;
import com.xideral.gestion_usuarios_be.enums.ErrorCode;
import com.xideral.gestion_usuarios_be.exception.UseCaseException;
import com.xideral.gestion_usuarios_be.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private static final String USER_EXIST = "El Usuario %s ya se encuentra registrado";
    private static final String USER_NOT_EXIST = "El usuario %s no existe";
    private static final String USER_NOT_EXIST_BY_ID = "El usuario con id %d no existe";

    private UserRepository userRepository;

    public UserDto getUserByName (String name) {
        return UserDto.from(userRepository.findByName(name)
                .orElseThrow(() -> new UseCaseException(ErrorCode.USER_NOT_FOUND, format(USER_NOT_EXIST, name))));
    }

    public UserDto getUserById (Long id) {
        return UserDto.from(userRepository.findById(id)
                .orElseThrow(() -> new UseCaseException(ErrorCode.USER_NOT_FOUND, format(USER_NOT_EXIST_BY_ID, id))));
    }

    public UserDto createUser (UserDto userRequest) {

        final Optional<User> user = userRepository.findByName(userRequest.name());

        if(user.isPresent()) {
            throw new UseCaseException(ErrorCode.DUPLICATE_ELEMENT, format(USER_EXIST, userRequest.name()));
        }

        return UserDto.from(userRepository.save(
                User.builder()
                        .name(userRequest.name())
                        .email(userRequest.email())
                        .build()
        ));
    }

    public UserDto updateUser (UserDto userRequest) {

        final Optional<User> user = userRepository.findById(userRequest.id());

        if(user.isEmpty()) {
            throw new UseCaseException(ErrorCode.USER_NOT_FOUND, format(USER_NOT_EXIST_BY_ID, userRequest.id()));
        }

        final Optional<User> userNewName = userRepository.findByName(userRequest.name());

        if(userNewName.isPresent()) {
            throw new UseCaseException(ErrorCode.DUPLICATE_ELEMENT, format(USER_EXIST, userRequest.name()));
        }

        return UserDto.from(userRepository.save(
                user.get().toBuilder()
                        .name(userRequest.name())
                        .email(userRequest.email())
                        .build()
        ));
    }

    public void deleteUser (Long id) {

        final Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new UseCaseException(ErrorCode.USER_NOT_FOUND, format(USER_NOT_EXIST_BY_ID, id));
        }

        userRepository.deleteById(id);
    }
}
