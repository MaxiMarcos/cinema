package com.cinema.carrito.repository;

import com.cinema.carrito.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseItem, Long> {
}
