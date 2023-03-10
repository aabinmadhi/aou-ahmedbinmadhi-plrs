package aou.ahmedbinmadhi.plrs.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PlrsCommission.
 */
@Entity
@Table(name = "plrs_commission")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlrsCommission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "commission_rate", precision = 21, scale = 2, nullable = false)
    private BigDecimal commissionRate;

    @Column(name = "commission_start_date")
    private ZonedDateTime commissionStartDate;

    @Column(name = "commission_end_date")
    private ZonedDateTime commissionEndDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlrsCommission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCommissionRate() {
        return this.commissionRate;
    }

    public PlrsCommission commissionRate(BigDecimal commissionRate) {
        this.setCommissionRate(commissionRate);
        return this;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public ZonedDateTime getCommissionStartDate() {
        return this.commissionStartDate;
    }

    public PlrsCommission commissionStartDate(ZonedDateTime commissionStartDate) {
        this.setCommissionStartDate(commissionStartDate);
        return this;
    }

    public void setCommissionStartDate(ZonedDateTime commissionStartDate) {
        this.commissionStartDate = commissionStartDate;
    }

    public ZonedDateTime getCommissionEndDate() {
        return this.commissionEndDate;
    }

    public PlrsCommission commissionEndDate(ZonedDateTime commissionEndDate) {
        this.setCommissionEndDate(commissionEndDate);
        return this;
    }

    public void setCommissionEndDate(ZonedDateTime commissionEndDate) {
        this.commissionEndDate = commissionEndDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlrsCommission)) {
            return false;
        }
        return id != null && id.equals(((PlrsCommission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlrsCommission{" +
            "id=" + getId() +
            ", commissionRate=" + getCommissionRate() +
            ", commissionStartDate='" + getCommissionStartDate() + "'" +
            ", commissionEndDate='" + getCommissionEndDate() + "'" +
            "}";
    }
}
