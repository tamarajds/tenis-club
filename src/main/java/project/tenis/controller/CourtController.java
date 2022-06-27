package project.tenis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.tenis.model.Court;
import project.tenis.service.CourtService;
import project.tenis.exception.CourtNotFoundException;

import java.util.List;

/**
 * REST controller for courts
 */
@RestController
@RequestMapping("/api/courts")
public class CourtController {

    private final CourtService courtService;

    @Autowired
    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping
    public List<Court> findAll() {
        return courtService.findAll();
    }

    @GetMapping("/{id}")
    public Court findById(@PathVariable int id) throws CourtNotFoundException {
        return courtService.findById(id);
    }
}
