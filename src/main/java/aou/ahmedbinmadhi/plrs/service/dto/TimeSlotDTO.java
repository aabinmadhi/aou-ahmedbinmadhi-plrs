package aou.ahmedbinmadhi.plrs.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link aou.ahmedbinmadhi.plrs.domain.TimeSlot} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimeSlotDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime startTime;

    @NotNull
    private ZonedDateTime endTime;

    private Boolean isDeleted;

    private LocationServiceDTO locationService;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocationServiceDTO getLocationService() {
        return locationService;
    }

    public void setLocationService(LocationServiceDTO locationService) {
        this.locationService = locationService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSlotDTO)) {
            return false;
        }

        TimeSlotDTO timeSlotDTO = (TimeSlotDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, timeSlotDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSlotDTO{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", locationService=" + getLocationService() +
            "}";
    }
}
