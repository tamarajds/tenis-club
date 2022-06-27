package project.tenis.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Class representing tennis court
 */
@Entity
@Table
public class Court {

    @Id @NotNull
    private int id;
    @NotNull
    private Surface surface;
    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new TreeSet<>();

    public Court() { }
    public Court(int id, Surface surface) {
        this.id = id;
        this.surface = surface;
    }

    public int getId() {
        return id;
    }

    public Surface getSurface() {
        return surface;
    }

    @Override
    public String toString() {
        return "Court{" +
                "id='" + id + '\'' +
                ", surface='" + surface + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surface);
    }
}
