package com.xideral.gestion_usuarios_be.controller;

import com.xideral.gestion_usuarios_be.dto.user.UserDto;
import com.xideral.gestion_usuarios_be.enums.ErrorCode;
import com.xideral.gestion_usuarios_be.exception.UseCaseException;
import com.xideral.gestion_usuarios_be.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;
import java.util.regex.Pattern;

@Controller
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-z]{2,7}$");

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        validateId(id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String name) {
        validateName(name);
        return ResponseEntity.ok(userService.getUserByName(name));
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        validateUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {

        validateId(id);
        validateId(userDto.id());

        if(!id.equals(userDto.id())) {
            throw new UseCaseException(ErrorCode.INVALID_PARAMETERS, "El ID debe coincidir en el path y en el body");
        }

        validateUser(userDto);
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable Long id) {
        validateId(id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    public static void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new UseCaseException(ErrorCode.INVALID_PARAMETERS, "El ID debe ser un número positivo");
        }
    }

    private static void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new UseCaseException(ErrorCode.INVALID_PARAMETERS, "El nombre no puede estar vacío");
        }
    }

    private static void validateUser(UserDto userDto) {
        if (StringUtils.isBlank(userDto.name())) {
            throw new UseCaseException(ErrorCode.INVALID_PARAMETERS, "El nombre no puede estar vacío");
        }

        if (!EMAIL_PATTERN.matcher(userDto.email()).matches()) {
            throw new UseCaseException(ErrorCode.INVALID_PARAMETERS, "El correo electrónico no tiene un formato válido");
        }

        validateDate(userDto.dateCreated());

    }

    public static void validateDate(Date date) {
        if(date == null || StringUtils.isBlank(date.toString())){
            throw new UseCaseException(ErrorCode.INVALID_PARAMETERS, "La fecha no puede estar vacía");
        }
    }

}
