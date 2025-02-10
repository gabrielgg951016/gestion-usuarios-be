package com.xideral.gestion_usuarios_be.repository;

import com.xideral.gestion_usuarios_be.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
