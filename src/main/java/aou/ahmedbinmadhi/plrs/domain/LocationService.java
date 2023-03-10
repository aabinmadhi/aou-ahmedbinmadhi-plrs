package aou.ahmedbinmadhi.plrs.domain;

import aou.ahmedbinmadhi.plrs.domain.enumeration.City;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LocationService.
 */
@Entity
@Table(name = "location_service")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "location_service_title", nullable = false)
    private String locationServiceTitle;

    @Lob
    @Column(name = "location_service_image", nullable = false)
    private byte[] locationServiceImage;

    @NotNull
    @Column(name = "location_service_image_content_type", nullable = false)
    private String locationServiceImageContentType;

    @Column(name = "map_coordinates")
    private String mapCoordinates;

    @Column(name = "location_discription")
    private String locationDiscription;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Column(name = "hourly_rate", nullable = false)
    private Double hourlyRate;

    @NotNull
    @Column(name = "white_board", nullable = false)
    private Boolean whiteBoard;

    @NotNull
    @Column(name = "coffee", nullable = false)
    private Boolean coffee;

    @NotNull
    @Column(name = "wi_fi", nullable = false)
    private Boolean wiFi;

    @NotNull
    @Column(name = "monitor", nullable = false)
    private Boolean monitor;

    @NotNull
    @Column(name = "pc_or_laptop", nullable = false)
    private Boolean pcOrLaptop;

    @NotNull
    @Column(name = "printer", nullable = false)
    private Boolean printer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "city", nullable = false)
    private City city;

    @OneToMany(mappedBy = "locationService")
    @JsonIgnoreProperties(value = { "internalUser", "profit", "locationService" }, allowSetters = true)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "locationService")
    @JsonIgnoreProperties(value = { "locationService" }, allowSetters = true)
    private Set<TimeSlot> timeSlots = new HashSet<>();

    @ManyToOne
    private User internalUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LocationService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationServiceTitle() {
        return this.locationServiceTitle;
    }

    public LocationService locationServiceTitle(String locationServiceTitle) {
        this.setLocationServiceTitle(locationServiceTitle);
        return this;
    }

    public void setLocationServiceTitle(String locationServiceTitle) {
        this.locationServiceTitle = locationServiceTitle;
    }

    public byte[] getLocationServiceImage() {
        return this.locationServiceImage;
    }

    public LocationService locationServiceImage(byte[] locationServiceImage) {
        this.setLocationServiceImage(locationServiceImage);
        return this;
    }

    public void setLocationServiceImage(byte[] locationServiceImage) {
        this.locationServiceImage = locationServiceImage;
    }

    public String getLocationServiceImageContentType() {
        return this.locationServiceImageContentType;
    }

    public LocationService locationServiceImageContentType(String locationServiceImageContentType) {
        this.locationServiceImageContentType = locationServiceImageContentType;
        return this;
    }

    public void setLocationServiceImageContentType(String locationServiceImageContentType) {
        this.locationServiceImageContentType = locationServiceImageContentType;
    }

    public String getMapCoordinates() {
        return this.mapCoordinates;
    }

    public LocationService mapCoordinates(String mapCoordinates) {
        this.setMapCoordinates(mapCoordinates);
        return this;
    }

    public void setMapCoordinates(String mapCoordinates) {
        this.mapCoordinates = mapCoordinates;
    }

    public String getLocationDiscription() {
        return this.locationDiscription;
    }

    public LocationService locationDiscription(String locationDiscription) {
        this.setLocationDiscription(locationDiscription);
        return this;
    }

    public void setLocationDiscription(String locationDiscription) {
        this.locationDiscription = locationDiscription;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public LocationService capacity(Integer capacity) {
        this.setCapacity(capacity);
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getHourlyRate() {
        return this.hourlyRate;
    }

    public LocationService hourlyRate(Double hourlyRate) {
        this.setHourlyRate(hourlyRate);
        return this;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Boolean getWhiteBoard() {
        return this.whiteBoard;
    }

    public LocationService whiteBoard(Boolean whiteBoard) {
        this.setWhiteBoard(whiteBoard);
        return this;
    }

    public void setWhiteBoard(Boolean whiteBoard) {
        this.whiteBoard = whiteBoard;
    }

    public Boolean getCoffee() {
        return this.coffee;
    }

    public LocationService coffee(Boolean coffee) {
        this.setCoffee(coffee);
        return this;
    }

    public void setCoffee(Boolean coffee) {
        this.coffee = coffee;
    }

    public Boolean getWiFi() {
        return this.wiFi;
    }

    public LocationService wiFi(Boolean wiFi) {
        this.setWiFi(wiFi);
        return this;
    }

    public void setWiFi(Boolean wiFi) {
        this.wiFi = wiFi;
    }

    public Boolean getMonitor() {
        return this.monitor;
    }

    public LocationService monitor(Boolean monitor) {
        this.setMonitor(monitor);
        return this;
    }

    public void setMonitor(Boolean monitor) {
        this.monitor = monitor;
    }

    public Boolean getPcOrLaptop() {
        return this.pcOrLaptop;
    }

    public LocationService pcOrLaptop(Boolean pcOrLaptop) {
        this.setPcOrLaptop(pcOrLaptop);
        return this;
    }

    public void setPcOrLaptop(Boolean pcOrLaptop) {
        this.pcOrLaptop = pcOrLaptop;
    }

    public Boolean getPrinter() {
        return this.printer;
    }

    public LocationService printer(Boolean printer) {
        this.setPrinter(printer);
        return this;
    }

    public void setPrinter(Boolean printer) {
        this.printer = printer;
    }

    public City getCity() {
        return this.city;
    }

    public LocationService city(City city) {
        this.setCity(city);
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        if (this.bookings != null) {
            this.bookings.forEach(i -> i.setLocationService(null));
        }
        if (bookings != null) {
            bookings.forEach(i -> i.setLocationService(this));
        }
        this.bookings = bookings;
    }

    public LocationService bookings(Set<Booking> bookings) {
        this.setBookings(bookings);
        return this;
    }

    public LocationService addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setLocationService(this);
        return this;
    }

    public LocationService removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setLocationService(null);
        return this;
    }

    public Set<TimeSlot> getTimeSlots() {
        return this.timeSlots;
    }

    public void setTimeSlots(Set<TimeSlot> timeSlots) {
        if (this.timeSlots != null) {
            this.timeSlots.forEach(i -> i.setLocationService(null));
        }
        if (timeSlots != null) {
            timeSlots.forEach(i -> i.setLocationService(this));
        }
        this.timeSlots = timeSlots;
    }

    public LocationService timeSlots(Set<TimeSlot> timeSlots) {
        this.setTimeSlots(timeSlots);
        return this;
    }

    public LocationService addTimeSlot(TimeSlot timeSlot) {
        this.timeSlots.add(timeSlot);
        timeSlot.setLocationService(this);
        return this;
    }

    public LocationService removeTimeSlot(TimeSlot timeSlot) {
        this.timeSlots.remove(timeSlot);
        timeSlot.setLocationService(null);
        return this;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public LocationService internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationService)) {
            return false;
        }
        return id != null && id.equals(((LocationService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationService{" +
            "id=" + getId() +
            ", locationServiceTitle='" + getLocationServiceTitle() + "'" +
            ", locationServiceImage='" + getLocationServiceImage() + "'" +
            ", locationServiceImageContentType='" + getLocationServiceImageContentType() + "'" +
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
            "}";
    }
}
