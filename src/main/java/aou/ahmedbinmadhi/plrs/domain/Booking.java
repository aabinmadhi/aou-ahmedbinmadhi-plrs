package aou.ahmedbinmadhi.plrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @ManyToOne
    private User internalUser;

    @JsonIgnoreProperties(value = { "booking" }, allowSetters = true)
    @OneToOne(mappedBy = "booking")
    private Profit profit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bookings", "timeSlots", "internalUser" }, allowSetters = true)
    private LocationService locationService;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Booking id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return this.startTime;
    }

    public Booking startTime(ZonedDateTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return this.endTime;
    }

    public Booking endTime(ZonedDateTime endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public Booking totalPrice(Double totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getIsPaid() {
        return this.isPaid;
    }

    public Booking isPaid(Boolean isPaid) {
        this.setIsPaid(isPaid);
        return this;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Booking internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Profit getProfit() {
        return this.profit;
    }

    public void setProfit(Profit profit) {
        if (this.profit != null) {
            this.profit.setBooking(null);
        }
        if (profit != null) {
            profit.setBooking(this);
        }
        this.profit = profit;
    }

    public Booking profit(Profit profit) {
        this.setProfit(profit);
        return this;
    }

    public LocationService getLocationService() {
        return this.locationService;
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public Booking locationService(LocationService locationService) {
        this.setLocationService(locationService);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", isPaid='" + getIsPaid() + "'" +
            "}";
    }
}
