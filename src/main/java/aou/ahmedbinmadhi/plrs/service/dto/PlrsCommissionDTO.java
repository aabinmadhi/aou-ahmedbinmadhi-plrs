package aou.ahmedbinmadhi.plrs.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link aou.ahmedbinmadhi.plrs.domain.PlrsCommission} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlrsCommissionDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal commissionRate;

    private ZonedDateTime commissionStartDate;

    private ZonedDateTime commissionEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public ZonedDateTime getCommissionStartDate() {
        return commissionStartDate;
    }

    public void setCommissionStartDate(ZonedDateTime commissionStartDate) {
        this.commissionStartDate = commissionStartDate;
    }

    public ZonedDateTime getCommissionEndDate() {
        return commissionEndDate;
    }

    public void setCommissionEndDate(ZonedDateTime commissionEndDate) {
        this.commissionEndDate = commissionEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlrsCommissionDTO)) {
            return false;
        }

        PlrsCommissionDTO plrsCommissionDTO = (PlrsCommissionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plrsCommissionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlrsCommissionDTO{" +
            "id=" + getId() +
            ", commissionRate=" + getCommissionRate() +
            ", commissionStartDate='" + getCommissionStartDate() + "'" +
            ", commissionEndDate='" + getCommissionEndDate() + "'" +
            "}";
    }
}
