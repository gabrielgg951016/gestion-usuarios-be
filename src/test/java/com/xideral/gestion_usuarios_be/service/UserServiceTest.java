package com.xideral.gestion_usuarios_be.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.xideral.gestion_usuarios_be.dto.user.UserDto;
import com.xideral.gestion_usuarios_be.entity.User;
import com.xideral.gestion_usuarios_be.exception.UseCaseException;
import com.xideral.gestion_usuarios_be.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final Date DATE_CREATED = new Date(123456789000L);
    private static final String USER_NAME = "testUser";
    private static final String USER_EMAIL = "correo@gmail.com";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserById_Success() {

        User user = User.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        UserDto userDtoResponse = UserDto.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserById(1L);

        assertEquals(userDtoResponse, userDto);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_Failed() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class, () -> userService.getUserById(1L));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserByName_Success() {
        User user = User.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        UserDto userDtoResponse = UserDto.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserByName(USER_NAME);

        assertEquals(userDtoResponse, userDto);

        verify(userRepository, times(1)).findByName(USER_NAME);

    }

    @Test
    void getUserByName_Failed() {

        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class, () -> userService.getUserByName(USER_NAME));

        verify(userRepository, times(1)).findByName(USER_NAME);
    }

    @Test
    void createUser_Success() {

        User user = User.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        UserDto userDtoRequest = UserDto.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        UserDto userDtoResponse = UserDto.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.empty());

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto userCreated = userService.createUser(userDtoRequest);

        assertAll("Validación del token",
                () ->  assertNotNull(userCreated),
                () -> assertEquals(userDtoResponse.id(), userCreated.id()),
                () -> assertEquals(userDtoResponse.name(), userCreated.name()),
                () -> assertEquals(userDtoResponse.email(), userCreated.email()),
                () -> assertEquals(userDtoResponse.dateCreated(), userCreated.dateCreated())
        );

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_Failed() {

        User user = User.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        UserDto userDtoRequest = UserDto.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.of(user));

        assertThrows(UseCaseException.class, () -> userService.createUser(userDtoRequest));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteOrder_Sucess() {

        User user = User.builder()
                .id(1L)
                .name("user")
                .email("correo@gmail.com")
                .dateCreated(new Date(123456789000L))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteOrder_Failed() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class, () -> userService.deleteUser(1L));

        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void updateUser_Sucess() {

        User user = User.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        UserDto userDtoRequest = UserDto.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto userCreated = userService.updateUser(1L, userDtoRequest);

        assertEquals(userDtoRequest, userCreated);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateOrder_Failed_IDNotFound() {

        UserDto userDtoRequest = UserDto.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UseCaseException.class, () -> userService.updateUser(1L, userDtoRequest));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateOrder_Failed_UserDuplicated() {

        User user = User.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        User userDuplicated = User.builder()
                .id(2L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        UserDto userDtoRequest = UserDto.builder()
                .id(1L)
                .name(USER_NAME)
                .email(USER_EMAIL)
                .dateCreated(DATE_CREATED)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.of(userDuplicated));

        assertThrows(UseCaseException.class, () -> userService.updateUser(1L, userDtoRequest));

        verify(userRepository, never()).save(any(User.class));
    }

}
