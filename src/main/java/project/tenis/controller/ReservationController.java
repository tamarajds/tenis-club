package project.tenis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.tenis.exception.CourtNotFoundException;
import project.tenis.exception.InvalidReservationException;
import project.tenis.model.Reservation;
import project.tenis.dto.ReservationCreateDto;
import project.tenis.service.ReservationService;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller class for reservations
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{courtId}")
    public List<Reservation> findByCourtId(@PathVariable int courtId) {
        return reservationService.findByCourtId(courtId);
    }

    @GetMapping("/number={number}")
    public List<Reservation> findByNumber(@PathVariable String number) {
        return reservationService.findByNumber(number);
    }

    @PostMapping
    public double makeReservation(@Valid @RequestBody ReservationCreateDto reservationDto) {
        Reservation reservation = reservationService.addReservation(reservationDto);
        return reservationService.calculatePrice(reservation);
    }

}
