package aou.ahmedbinmadhi.plrs.web.rest;

import aou.ahmedbinmadhi.plrs.domain.LocationService;
import aou.ahmedbinmadhi.plrs.repository.LocationServiceRepository;
import aou.ahmedbinmadhi.plrs.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link aou.ahmedbinmadhi.plrs.domain.LocationService}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LocationServiceResource {

    private final Logger log = LoggerFactory.getLogger(LocationServiceResource.class);

    private static final String ENTITY_NAME = "locationService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationServiceRepository locationServiceRepository;

    public LocationServiceResource(LocationServiceRepository locationServiceRepository) {
        this.locationServiceRepository = locationServiceRepository;
    }

    /**
     * {@code POST  /location-services} : Create a new locationService.
     *
     * @param locationService the locationService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationService, or with status {@code 400 (Bad Request)} if the locationService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-services")
    public ResponseEntity<LocationService> createLocationService(@Valid @RequestBody LocationService locationService)
        throws URISyntaxException {
        log.debug("REST request to save LocationService : {}", locationService);
        if (locationService.getId() != null) {
            throw new BadRequestAlertException("A new locationService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationService result = locationServiceRepository.save(locationService);
        return ResponseEntity
            .created(new URI("/api/location-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-services/:id} : Updates an existing locationService.
     *
     * @param id the id of the locationService to save.
     * @param locationService the locationService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationService,
     * or with status {@code 400 (Bad Request)} if the locationService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-services/{id}")
    public ResponseEntity<LocationService> updateLocationService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LocationService locationService
    ) throws URISyntaxException {
        log.debug("REST request to update LocationService : {}, {}", id, locationService);
        if (locationService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LocationService result = locationServiceRepository.save(locationService);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locationService.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /location-services/:id} : Partial updates given fields of an existing locationService, field will ignore if it is null
     *
     * @param id the id of the locationService to save.
     * @param locationService the locationService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationService,
     * or with status {@code 400 (Bad Request)} if the locationService is not valid,
     * or with status {@code 404 (Not Found)} if the locationService is not found,
     * or with status {@code 500 (Internal Server Error)} if the locationService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/location-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocationService> partialUpdateLocationService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LocationService locationService
    ) throws URISyntaxException {
        log.debug("REST request to partial update LocationService partially : {}, {}", id, locationService);
        if (locationService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocationService> result = locationServiceRepository
            .findById(locationService.getId())
            .map(existingLocationService -> {
                if (locationService.getLocationServiceTitle() != null) {
                    existingLocationService.setLocationServiceTitle(locationService.getLocationServiceTitle());
                }
                if (locationService.getLocationServiceImage() != null) {
                    existingLocationService.setLocationServiceImage(locationService.getLocationServiceImage());
                }
                if (locationService.getLocationServiceImageContentType() != null) {
                    existingLocationService.setLocationServiceImageContentType(locationService.getLocationServiceImageContentType());
                }
                if (locationService.getMapCoordinates() != null) {
                    existingLocationService.setMapCoordinates(locationService.getMapCoordinates());
                }
                if (locationService.getLocationDiscription() != null) {
                    existingLocationService.setLocationDiscription(locationService.getLocationDiscription());
                }
                if (locationService.getCapacity() != null) {
                    existingLocationService.setCapacity(locationService.getCapacity());
                }
                if (locationService.getHourlyRate() != null) {
                    existingLocationService.setHourlyRate(locationService.getHourlyRate());
                }
                if (locationService.getWhiteBoard() != null) {
                    existingLocationService.setWhiteBoard(locationService.getWhiteBoard());
                }
                if (locationService.getCoffee() != null) {
                    existingLocationService.setCoffee(locationService.getCoffee());
                }
                if (locationService.getWiFi() != null) {
                    existingLocationService.setWiFi(locationService.getWiFi());
                }
                if (locationService.getMonitor() != null) {
                    existingLocationService.setMonitor(locationService.getMonitor());
                }
                if (locationService.getPcOrLaptop() != null) {
                    existingLocationService.setPcOrLaptop(locationService.getPcOrLaptop());
                }
                if (locationService.getPrinter() != null) {
                    existingLocationService.setPrinter(locationService.getPrinter());
                }
                if (locationService.getCity() != null) {
                    existingLocationService.setCity(locationService.getCity());
                }

                return existingLocationService;
            })
            .map(locationServiceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locationService.getId().toString())
        );
    }

    /**
     * {@code GET  /location-services} : get all the locationServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationServices in body.
     */
    @GetMapping("/location-services")
    public List<LocationService> getAllLocationServices() {
        log.debug("REST request to get all LocationServices");
        return locationServiceRepository.findAll();
    }

    /**
     * {@code GET  /location-services/:id} : get the "id" locationService.
     *
     * @param id the id of the locationService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-services/{id}")
    public ResponseEntity<LocationService> getLocationService(@PathVariable Long id) {
        log.debug("REST request to get LocationService : {}", id);
        Optional<LocationService> locationService = locationServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locationService);
    }

    /**
     * {@code DELETE  /location-services/:id} : delete the "id" locationService.
     *
     * @param id the id of the locationService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-services/{id}")
    public ResponseEntity<Void> deleteLocationService(@PathVariable Long id) {
        log.debug("REST request to delete LocationService : {}", id);
        locationServiceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
