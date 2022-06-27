package project.tenis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.tenis.model.Court;
import project.tenis.model.Surface;
import project.tenis.repository.CourtRepository;

/**
 * Controller for initializing courts for testing purpose
 */
@RestController
@RequestMapping("/api/init")
public class InitialCourtController {

    private final CourtRepository courtRepository;

    @Autowired
    public InitialCourtController(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    @PostMapping
    public void initialize() {
        courtRepository.save(new Court(1, Surface.GRASS));
        courtRepository.save(new Court(2, Surface.ARTIFICIAL_GRASS));
        courtRepository.save(new Court(3, Surface.CLAY));
        courtRepository.save(new Court(4, Surface.CLAY));
        courtRepository.save(new Court(5, Surface.HARD));
    }

}
