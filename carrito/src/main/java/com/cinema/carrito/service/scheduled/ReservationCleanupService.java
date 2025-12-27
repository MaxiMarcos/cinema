package com.cinema.carrito.service.scheduled;

import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.repository.PurchaseItemRepository;
import com.cinema.carrito.repository.SeatClientAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationCleanupService {

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private SeatClientAPI seatClientAPI;

    // Se ejecuta cada minuto
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cleanupExpiredReservations() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<PurchaseItem> expiredItems = purchaseItemRepository.findByStatusAndCreatedAtBefore(Status.PENDING, tenMinutesAgo);

        for (PurchaseItem item : expiredItems) {
            item.setStatus(Status.CANCELED);
            purchaseItemRepository.save(item);

            // Liberar el asiento en el microservicio theater
            try {
                seatClientAPI.editStatusSeat(item.getSeatId(), true);
                System.out.println("Asiento " + item.getSeatId() + " liberado debido a expiración de reserva.");
            } catch (Exception e) {
                System.err.println("Error al liberar el asiento " + item.getSeatId() + ": " + e.getMessage());
            }
        }
    }
}
