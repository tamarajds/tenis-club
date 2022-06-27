package project.tenis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.tenis.dto.ReservationCreateDto;
import project.tenis.exception.CourtNotFoundException;
import project.tenis.exception.InvalidReservationException;
import project.tenis.model.Court;
import project.tenis.model.MatchType;
import project.tenis.model.Reservation;
import project.tenis.repository.CourtRepository;
import project.tenis.repository.ReservationRepository;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

/**
 * Service class for reservations
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CourtRepository courtRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, CourtRepository courtRepository) {
        this.reservationRepository = reservationRepository;
        this.courtRepository = courtRepository;
    }

    public List<Reservation> findByCourtId(int courtId) {
        Optional<Court> court = courtRepository.findById(courtId);
        if (court.isEmpty()) {
            throw new CourtNotFoundException();
        }
        return reservationRepository.findByCourtId(courtId);
    }

    public List<Reservation> findByNumber(String number) {
        if (!checkNumberFormat(number)) {
            throw new InvalidReservationException("Invalid number format");
        }
        return reservationRepository.findByNumber(number);
    }

    public Reservation addReservation(ReservationCreateDto reservationDto) {
        Reservation reservation = mapReservation(reservationDto);
        return reservationRepository.save(reservation);
    }

    /**
     * Calculates price for given reservation
     *
     * @param reservation given reservation
     * @return price
     */
     public double calculatePrice(Reservation reservation) {
        double timeInMinutes = timeDifferenceInMinutes(reservation.getStartTime(), reservation.getEndTime());
        double pricePerMinute = reservation.getCourt().getSurface().getPricePerMinute();
        double price = timeInMinutes * pricePerMinute;
        if (reservation.getMatchType().equals(MatchType.DOUBLES)) {
            price *= 1.5;
        }
        return price;
    }

    /**
     * Maps reservation DTO to Reservation object
     *
     * @param reservationDto reservation data transfer object
     * @return mapped reservation
     * @throws CourtNotFoundException if court is not in system
     * @throws InvalidReservationException if invalid reservation arguments
     */
    private Reservation mapReservation(ReservationCreateDto reservationDto) {
        Optional<Court> court = courtRepository.findById(reservationDto.getCourtId());
        if (court.isEmpty()) {
            throw new CourtNotFoundException();
        }

        if (!checkNumberFormat(reservationDto.getNumber())) {
            throw new InvalidReservationException("Invalid number format");
        }

        if (!checkNumber(reservationDto.getNumber(), reservationDto.getName())) {
            throw new InvalidReservationException("Number already in system associated with different name");
        }

        Optional<MatchType> matchType = stringToMatchType(reservationDto.getMatchType());
        if (matchType.isEmpty()) {
                throw new InvalidReservationException("Invalid match type");
        }

        Optional<LocalDateTime> start = stringToDateTime(reservationDto.getStartTime());
        Optional<LocalDateTime> end = stringToDateTime(reservationDto.getEndTime());
        if (start.isEmpty() || end.isEmpty()) {
            throw new InvalidReservationException("Invalid reservation time format");
        }

        if (!checkTimes(start.get(), end.get())) {
            throw new InvalidReservationException("Invalid dates");
        }

        if (!checkCourtAvailability(reservationDto.getCourtId(), start.get(), end.get())) {
            throw new InvalidReservationException("Court not available");
        }

        return new Reservation(reservationDto.getNumber(), reservationDto.getName(), court.get(),
                matchType.get(), start.get(), end.get());
    }

    /**
     * Converts string to MatchType
     *
     * @param matchTypeStr given string
     * @return match type if existing
     *         else empty
     */
    private Optional<MatchType> stringToMatchType(String matchTypeStr) {
        switch (matchTypeStr.toLowerCase()) {
            case ("doubles"):
                return Optional.of(MatchType.DOUBLES);
            case("singles"):
                return Optional.of(MatchType.SINGLES);
            default:
                return Optional.empty();
        }
    }

    /**
     * Checks court availability in given date and time
     *
     * @param courtId court id
     * @param start beginning of period
     * @param end end of period
     * @return true if court is available in given time
     */
    private Boolean checkCourtAvailability(int courtId, LocalDateTime start, LocalDateTime end) {
        List<Reservation> reservations = reservationRepository.findByCourtId(courtId);
        for (Reservation reservation:reservations) {
            if (!(end.compareTo(reservation.getStartTime()) <= 0 || start.compareTo(reservation.getEndTime()) >= 0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if given number is assigned to only one (or none) name
     *
     * @param number phone number
     * @param name customer's name
     * @return true if number is correct
     */
    private boolean checkNumber(String number, String name) {
        List<Reservation> reservationsByName = reservationRepository.findByNumber(number);
        return reservationsByName.isEmpty() || reservationsByName.get(0).getName().equals(name);
    }

    /**
     * Converts string with date to LocalDateTime object
     *
     * @param dateStr string with date
     * @return converted LocalDateTime object if valid
     *         else empty
     */
    private Optional<LocalDateTime> stringToDateTime(String dateStr) {
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return Optional.of(LocalDateTime.parse(dateStr, inputFormat));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }

    /**
     * Checks phone number format
     *
     * @param number phone number
     * @return true if correct phone number
     */
    private Boolean checkNumberFormat(String number) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(number).matches() && number.length() == 9;
    }

    /**
     * Checks time of reservations
     *
     * @param start start of reservation time period
     * @param end end of reservation time period
     * @return true if valid reservation time
     */
    private Boolean checkTimes(LocalDateTime start, LocalDateTime end) {
        return start.compareTo(LocalDateTime.now()) >= 0 &&
                0 < timeDifferenceInMinutes(start, end) &&
                start.toLocalDate().equals(end.toLocalDate()) &&
                (start.getMinute() == 0 || start.getMinute() == 30);
    }

    /**
     * Returns time difference in minutes between two times
     *
     * @param time1 first time
     * @param time2 second time
     * @return time difference in hours
     */
    private double timeDifferenceInMinutes(LocalDateTime time1, LocalDateTime time2) {
        return (double) abs(Duration.between(time1, time2).getSeconds()) / 60;
    }
}
