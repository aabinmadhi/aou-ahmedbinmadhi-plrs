package aou.ahmedbinmadhi.plrs.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link aou.ahmedbinmadhi.plrs.domain.AnalyticsCenter} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnalyticsCenterDTO implements Serializable {

    private Long id;

    private Double totalRevenuePerLProvider;

    private Double numberOfLocations;

    private Double countOfBookingPerLocationService;

    private Double totalRevenuePerLocationService;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalRevenuePerLProvider() {
        return totalRevenuePerLProvider;
    }

    public void setTotalRevenuePerLProvider(Double totalRevenuePerLProvider) {
        this.totalRevenuePerLProvider = totalRevenuePerLProvider;
    }

    public Double getNumberOfLocations() {
        return numberOfLocations;
    }

    public void setNumberOfLocations(Double numberOfLocations) {
        this.numberOfLocations = numberOfLocations;
    }

    public Double getCountOfBookingPerLocationService() {
        return countOfBookingPerLocationService;
    }

    public void setCountOfBookingPerLocationService(Double countOfBookingPerLocationService) {
        this.countOfBookingPerLocationService = countOfBookingPerLocationService;
    }

    public Double getTotalRevenuePerLocationService() {
        return totalRevenuePerLocationService;
    }

    public void setTotalRevenuePerLocationService(Double totalRevenuePerLocationService) {
        this.totalRevenuePerLocationService = totalRevenuePerLocationService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalyticsCenterDTO)) {
            return false;
        }

        AnalyticsCenterDTO analyticsCenterDTO = (AnalyticsCenterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, analyticsCenterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyticsCenterDTO{" +
            "id=" + getId() +
            ", totalRevenuePerLProvider=" + getTotalRevenuePerLProvider() +
            ", numberOfLocations=" + getNumberOfLocations() +
            ", countOfBookingPerLocationService=" + getCountOfBookingPerLocationService() +
            ", totalRevenuePerLocationService=" + getTotalRevenuePerLocationService() +
            "}";
    }
}
