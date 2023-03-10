package aou.ahmedbinmadhi.plrs.service.dto;

import aou.ahmedbinmadhi.plrs.domain.enumeration.City;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link aou.ahmedbinmadhi.plrs.domain.LocationService} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationServiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String locationServiceTitle;

    @Lob
    private byte[] locationServiceImage;

    private String locationServiceImageContentType;
    private String mapCoordinates;

    private String locationDiscription;

    @NotNull
    private Integer capacity;

    @NotNull
    private Double hourlyRate;

    @NotNull
    private Boolean whiteBoard;

    @NotNull
    private Boolean coffee;

    @NotNull
    private Boolean wiFi;

    @NotNull
    private Boolean monitor;

    @NotNull
    private Boolean pcOrLaptop;

    @NotNull
    private Boolean printer;

    @NotNull
    private City city;

    private UserDTO internalUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationServiceTitle() {
        return locationServiceTitle;
    }

    public void setLocationServiceTitle(String locationServiceTitle) {
        this.locationServiceTitle = locationServiceTitle;
    }

    public byte[] getLocationServiceImage() {
        return locationServiceImage;
    }

    public void setLocationServiceImage(byte[] locationServiceImage) {
        this.locationServiceImage = locationServiceImage;
    }

    public String getLocationServiceImageContentType() {
        return locationServiceImageContentType;
    }

    public void setLocationServiceImageContentType(String locationServiceImageContentType) {
        this.locationServiceImageContentType = locationServiceImageContentType;
    }

    public String getMapCoordinates() {
        return mapCoordinates;
    }

    public void setMapCoordinates(String mapCoordinates) {
        this.mapCoordinates = mapCoordinates;
    }

    public String getLocationDiscription() {
        return locationDiscription;
    }

    public void setLocationDiscription(String locationDiscription) {
        this.locationDiscription = locationDiscription;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Boolean getWhiteBoard() {
        return whiteBoard;
    }

    public void setWhiteBoard(Boolean whiteBoard) {
        this.whiteBoard = whiteBoard;
    }

    public Boolean getCoffee() {
        return coffee;
    }

    public void setCoffee(Boolean coffee) {
        this.coffee = coffee;
    }

    public Boolean getWiFi() {
        return wiFi;
    }

    public void setWiFi(Boolean wiFi) {
        this.wiFi = wiFi;
    }

    public Boolean getMonitor() {
        return monitor;
    }

    public void setMonitor(Boolean monitor) {
        this.monitor = monitor;
    }

    public Boolean getPcOrLaptop() {
        return pcOrLaptop;
    }

    public void setPcOrLaptop(Boolean pcOrLaptop) {
        this.pcOrLaptop = pcOrLaptop;
    }

    public Boolean getPrinter() {
        return printer;
    }

    public void setPrinter(Boolean printer) {
        this.printer = printer;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public UserDTO getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDTO internalUser) {
        this.internalUser = internalUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationServiceDTO)) {
            return false;
        }

        LocationServiceDTO locationServiceDTO = (LocationServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locationServiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationServiceDTO{" +
            "id=" + getId() +
            ", locationServiceTitle='" + getLocationServiceTitle() + "'" +
            ", locationServiceImage='" + getLocationServiceImage() + "'" +
            ", mapCoordinates='" + getMapCoordinates() + "'" +
            ", locationDiscription='" + getLocationDiscription() + "'" +
            ", capacity=" + getCapacity() +
            ", hourlyRate=" + getHourlyRate() +
            ", whiteBoard='" + getWhiteBoard() + "'" +
            ", coffee='" + getCoffee() + "'" +
            ", wiFi='" + getWiFi() + "'" +
            ", monitor='" + getMonitor() + "'" +
            ", pcOrLaptop='" + getPcOrLaptop() + "'" +
            ", printer='" + getPrinter() + "'" +
            ", city='" + getCity() + "'" +
            ", internalUser=" + getInternalUser() +
            "}";
    }
}
