package aou.ahmedbinmadhi.plrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Profit.
 */
@Entity
@Table(name = "profit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_profit_amount_per_booking")
    private Double userProfitAmountPerBooking;

    @Column(name = "plrs_profit_amount_per_booking")
    private Double plrsProfitAmountPerBooking;

    @Column(name = "user_profit_amount_per_location_service")
    private Double userProfitAmountPerLocationService;

    @Column(name = "plrs_profit_amount_per_location_service")
    private Double plrsProfitAmountPerLocationService;

    @Column(name = "user_total_profit")
    private Double userTotalProfit;

    @Column(name = "plrs_total_profit")
    private Double plrsTotalProfit;

    @JsonIgnoreProperties(value = { "internalUser", "profit", "locationService" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Booking booking;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getUserProfitAmountPerBooking() {
        return this.userProfitAmountPerBooking;
    }

    public Profit userProfitAmountPerBooking(Double userProfitAmountPerBooking) {
        this.setUserProfitAmountPerBooking(userProfitAmountPerBooking);
        return this;
    }

    public void setUserProfitAmountPerBooking(Double userProfitAmountPerBooking) {
        this.userProfitAmountPerBooking = userProfitAmountPerBooking;
    }

    public Double getPlrsProfitAmountPerBooking() {
        return this.plrsProfitAmountPerBooking;
    }

    public Profit plrsProfitAmountPerBooking(Double plrsProfitAmountPerBooking) {
        this.setPlrsProfitAmountPerBooking(plrsProfitAmountPerBooking);
        return this;
    }

    public void setPlrsProfitAmountPerBooking(Double plrsProfitAmountPerBooking) {
        this.plrsProfitAmountPerBooking = plrsProfitAmountPerBooking;
    }

    public Double getUserProfitAmountPerLocationService() {
        return this.userProfitAmountPerLocationService;
    }

    public Profit userProfitAmountPerLocationService(Double userProfitAmountPerLocationService) {
        this.setUserProfitAmountPerLocationService(userProfitAmountPerLocationService);
        return this;
    }

    public void setUserProfitAmountPerLocationService(Double userProfitAmountPerLocationService) {
        this.userProfitAmountPerLocationService = userProfitAmountPerLocationService;
    }

    public Double getPlrsProfitAmountPerLocationService() {
        return this.plrsProfitAmountPerLocationService;
    }

    public Profit plrsProfitAmountPerLocationService(Double plrsProfitAmountPerLocationService) {
        this.setPlrsProfitAmountPerLocationService(plrsProfitAmountPerLocationService);
        return this;
    }

    public void setPlrsProfitAmountPerLocationService(Double plrsProfitAmountPerLocationService) {
        this.plrsProfitAmountPerLocationService = plrsProfitAmountPerLocationService;
    }

    public Double getUserTotalProfit() {
        return this.userTotalProfit;
    }

    public Profit userTotalProfit(Double userTotalProfit) {
        this.setUserTotalProfit(userTotalProfit);
        return this;
    }

    public void setUserTotalProfit(Double userTotalProfit) {
        this.userTotalProfit = userTotalProfit;
    }

    public Double getPlrsTotalProfit() {
        return this.plrsTotalProfit;
    }

    public Profit plrsTotalProfit(Double plrsTotalProfit) {
        this.setPlrsTotalProfit(plrsTotalProfit);
        return this;
    }

    public void setPlrsTotalProfit(Double plrsTotalProfit) {
        this.plrsTotalProfit = plrsTotalProfit;
    }

    public Booking getBooking() {
        return this.booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Profit booking(Booking booking) {
        this.setBooking(booking);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profit)) {
            return false;
        }
        return id != null && id.equals(((Profit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profit{" +
            "id=" + getId() +
            ", userProfitAmountPerBooking=" + getUserProfitAmountPerBooking() +
            ", plrsProfitAmountPerBooking=" + getPlrsProfitAmountPerBooking() +
            ", userProfitAmountPerLocationService=" + getUserProfitAmountPerLocationService() +
            ", plrsProfitAmountPerLocationService=" + getPlrsProfitAmountPerLocationService() +
            ", userTotalProfit=" + getUserTotalProfit() +
            ", plrsTotalProfit=" + getPlrsTotalProfit() +
            "}";
    }
}
