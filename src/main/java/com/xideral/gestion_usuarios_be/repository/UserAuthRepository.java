package com.xideral.gestion_usuarios_be.repository;

import com.xideral.gestion_usuarios_be.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByUser(String user);
}
