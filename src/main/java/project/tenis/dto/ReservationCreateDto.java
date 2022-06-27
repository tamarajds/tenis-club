package project.tenis.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Data transfer object for creating reservation
 */
public class ReservationCreateDto {

    @NotNull
    private int courtId;
    @NotNull
    @Size(min=9, max=9)
    private String number;
    @NotNull
    private String name;
    @NotNull
    private String matchType;
    @NotNull
    private String startTime;
    @NotNull
    private String endTime;

    public ReservationCreateDto(int courtId, String number, String name, String matchType, String startTime, String endTime) {
        this.courtId = courtId;
        this.number = number;
        this.name = name;
        this.matchType = matchType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getCourtId() {
        return courtId;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getMatchType() {
        return matchType;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "ReservationCreationDto{" +
                "courtId='" + courtId + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", matchType='" + matchType + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(courtId, number, name, matchType, startTime, endTime);
    }
}
