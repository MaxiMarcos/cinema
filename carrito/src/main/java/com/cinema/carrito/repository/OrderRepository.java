package com.cinema.carrito.repository;

import com.cinema.carrito.entity.TheOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<TheOrder, Long> {
}
