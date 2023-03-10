package aou.ahmedbinmadhi.plrs.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A AnalyticsCenter.
 */
@Entity
@Table(name = "analytics_center")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnalyticsCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_revenue_per_l_provider")
    private Double totalRevenuePerLProvider;

    @Column(name = "number_of_locations")
    private Double numberOfLocations;

    @Column(name = "count_of_booking_per_location_service")
    private Double countOfBookingPerLocationService;

    @Column(name = "total_revenue_per_location_service")
    private Double totalRevenuePerLocationService;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnalyticsCenter id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalRevenuePerLProvider() {
        return this.totalRevenuePerLProvider;
    }

    public AnalyticsCenter totalRevenuePerLProvider(Double totalRevenuePerLProvider) {
        this.setTotalRevenuePerLProvider(totalRevenuePerLProvider);
        return this;
    }

    public void setTotalRevenuePerLProvider(Double totalRevenuePerLProvider) {
        this.totalRevenuePerLProvider = totalRevenuePerLProvider;
    }

    public Double getNumberOfLocations() {
        return this.numberOfLocations;
    }

    public AnalyticsCenter numberOfLocations(Double numberOfLocations) {
        this.setNumberOfLocations(numberOfLocations);
        return this;
    }

    public void setNumberOfLocations(Double numberOfLocations) {
        this.numberOfLocations = numberOfLocations;
    }

    public Double getCountOfBookingPerLocationService() {
        return this.countOfBookingPerLocationService;
    }

    public AnalyticsCenter countOfBookingPerLocationService(Double countOfBookingPerLocationService) {
        this.setCountOfBookingPerLocationService(countOfBookingPerLocationService);
        return this;
    }

    public void setCountOfBookingPerLocationService(Double countOfBookingPerLocationService) {
        this.countOfBookingPerLocationService = countOfBookingPerLocationService;
    }

    public Double getTotalRevenuePerLocationService() {
        return this.totalRevenuePerLocationService;
    }

    public AnalyticsCenter totalRevenuePerLocationService(Double totalRevenuePerLocationService) {
        this.setTotalRevenuePerLocationService(totalRevenuePerLocationService);
        return this;
    }

    public void setTotalRevenuePerLocationService(Double totalRevenuePerLocationService) {
        this.totalRevenuePerLocationService = totalRevenuePerLocationService;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalyticsCenter)) {
            return false;
        }
        return id != null && id.equals(((AnalyticsCenter) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyticsCenter{" +
            "id=" + getId() +
            ", totalRevenuePerLProvider=" + getTotalRevenuePerLProvider() +
            ", numberOfLocations=" + getNumberOfLocations() +
            ", countOfBookingPerLocationService=" + getCountOfBookingPerLocationService() +
            ", totalRevenuePerLocationService=" + getTotalRevenuePerLocationService() +
            "}";
    }
}
