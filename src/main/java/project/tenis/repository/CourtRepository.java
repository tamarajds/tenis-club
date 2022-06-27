package project.tenis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.tenis.model.Court;

/**
 * Court repository interface
 */
@Repository
public interface CourtRepository extends JpaRepository<Court, Integer> {

}
