package project.tenis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.tenis.model.Court;
import project.tenis.repository.CourtRepository;
import project.tenis.exception.CourtNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Service class for tennis courts
 */
@Service
public class CourtService {

    private final CourtRepository courtRepository;

    @Autowired
    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }
    public List<Court> findAll() {
        return courtRepository.findAll();
    }

    public Court findById(int id) throws CourtNotFoundException {
        Optional<Court> court = courtRepository.findById(id);
        if (court.isEmpty()) {
            throw new CourtNotFoundException();
        }
        return court.get();
    }
}
