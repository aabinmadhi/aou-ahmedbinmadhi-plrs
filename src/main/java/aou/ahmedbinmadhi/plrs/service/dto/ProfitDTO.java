package aou.ahmedbinmadhi.plrs.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link aou.ahmedbinmadhi.plrs.domain.Profit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfitDTO implements Serializable {

    private Long id;

    private Double userProfitAmountPerBooking;

    private Double plrsProfitAmountPerBooking;

    private Double userProfitAmountPerLocationService;

    private Double plrsProfitAmountPerLocationService;

    private Double userTotalProfit;

    private Double plrsTotalProfit;

    private BookingDTO booking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getUserProfitAmountPerBooking() {
        return userProfitAmountPerBooking;
    }

    public void setUserProfitAmountPerBooking(Double userProfitAmountPerBooking) {
        this.userProfitAmountPerBooking = userProfitAmountPerBooking;
    }

    public Double getPlrsProfitAmountPerBooking() {
        return plrsProfitAmountPerBooking;
    }

    public void setPlrsProfitAmountPerBooking(Double plrsProfitAmountPerBooking) {
        this.plrsProfitAmountPerBooking = plrsProfitAmountPerBooking;
    }

    public Double getUserProfitAmountPerLocationService() {
        return userProfitAmountPerLocationService;
    }

    public void setUserProfitAmountPerLocationService(Double userProfitAmountPerLocationService) {
        this.userProfitAmountPerLocationService = userProfitAmountPerLocationService;
    }

    public Double getPlrsProfitAmountPerLocationService() {
        return plrsProfitAmountPerLocationService;
    }

    public void setPlrsProfitAmountPerLocationService(Double plrsProfitAmountPerLocationService) {
        this.plrsProfitAmountPerLocationService = plrsProfitAmountPerLocationService;
    }

    public Double getUserTotalProfit() {
        return userTotalProfit;
    }

    public void setUserTotalProfit(Double userTotalProfit) {
        this.userTotalProfit = userTotalProfit;
    }

    public Double getPlrsTotalProfit() {
        return plrsTotalProfit;
    }

    public void setPlrsTotalProfit(Double plrsTotalProfit) {
        this.plrsTotalProfit = plrsTotalProfit;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfitDTO)) {
            return false;
        }

        ProfitDTO profitDTO = (ProfitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfitDTO{" +
            "id=" + getId() +
            ", userProfitAmountPerBooking=" + getUserProfitAmountPerBooking() +
            ", plrsProfitAmountPerBooking=" + getPlrsProfitAmountPerBooking() +
            ", userProfitAmountPerLocationService=" + getUserProfitAmountPerLocationService() +
            ", plrsProfitAmountPerLocationService=" + getPlrsProfitAmountPerLocationService() +
            ", userTotalProfit=" + getUserTotalProfit() +
            ", plrsTotalProfit=" + getPlrsTotalProfit() +
            ", booking=" + getBooking() +
            "}";
    }
}
