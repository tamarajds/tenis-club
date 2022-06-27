package project.tenis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.tenis.model.Reservation;

import java.util.List;

/**
 * Reservation repository interface
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByNumber(String number);
    List<Reservation> findByName(String name);
    List<Reservation> findByCourtId(int courtId);
}
