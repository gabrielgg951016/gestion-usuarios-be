package com.xideral.gestion_usuarios_be.repository;

import com.xideral.gestion_usuarios_be.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByUser_Id(Long userId);
}
