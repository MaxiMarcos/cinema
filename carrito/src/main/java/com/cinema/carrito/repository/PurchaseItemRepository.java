package com.cinema.carrito.repository;

import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    List<PurchaseItem> findByStatusAndCreatedAtBefore(Status status, LocalDateTime dateTime);

    List<PurchaseItem> findByScheduleIdAndSeatIdAndStatusIn(Long scheduleId, Long seatId, List<Status> statuses);

    List<PurchaseItem> findByScheduleIdAndStatusIn(Long scheduleId, List<Status> statuses);

}
