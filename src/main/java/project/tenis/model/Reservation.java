package project.tenis.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class representing court reservation
 */
@Entity
@Table
public class Reservation implements Comparable<Reservation> {

    @Id @GeneratedValue
    private long id;
    @NotNull
    @Size(min=9, max=9)
    private String number;
    @NotNull
    private String name;
    @NotNull
    @ManyToOne @JoinColumn(name = "courtId")
    private Court court;
    @NotNull
    private MatchType matchType;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;

    public Reservation() { }
    public Reservation(String number, String name, Court court, MatchType matchType,
                       LocalDateTime startTime, LocalDateTime endTime) {
        this.number = number;
        this.name = name;
        this.court = court;
        this.matchType = matchType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Court getCourt() {
        return court;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", matchType=" + matchType +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", court=" + court +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name, court, matchType, startTime, endTime);
    }

    @Override
    public int compareTo(Reservation o) {
        return startTime.compareTo(o.startTime);
    }
}
