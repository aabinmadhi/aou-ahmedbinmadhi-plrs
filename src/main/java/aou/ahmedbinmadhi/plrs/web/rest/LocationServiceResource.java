package aou.ahmedbinmadhi.plrs.web.rest;

import aou.ahmedbinmadhi.plrs.repository.LocationServiceRepository;
import aou.ahmedbinmadhi.plrs.service.LocationServiceService;
import aou.ahmedbinmadhi.plrs.service.dto.LocationServiceDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link aou.ahmedbinmadhi.plrs.domain.LocationService}.
 */
@RestController
@RequestMapping("/api")
public class LocationServiceResource {

    private final Logger log = LoggerFactory.getLogger(LocationServiceResource.class);

    private static final String ENTITY_NAME = "locationService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationServiceService locationServiceService;

    private final LocationServiceRepository locationServiceRepository;

    public LocationServiceResource(LocationServiceService locationServiceService, LocationServiceRepository locationServiceRepository) {
        this.locationServiceService = locationServiceService;
        this.locationServiceRepository = locationServiceRepository;
    }

    /**
     * {@code POST  /location-services} : Create a new locationService.
     *
     * @param locationServiceDTO the locationServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationServiceDTO, or with status {@code 400 (Bad Request)} if the locationService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/location-services")
    public ResponseEntity<LocationServiceDTO> createLocationService(@Valid @RequestBody LocationServiceDTO locationServiceDTO)
        throws URISyntaxException {
        log.debug("REST request to save LocationService : {}", locationServiceDTO);
        if (locationServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new locationService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationServiceDTO result = locationServiceService.save(locationServiceDTO);
        return ResponseEntity
            .created(new URI("/api/location-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /location-services/:id} : Updates an existing locationService.
     *
     * @param id the id of the locationServiceDTO to save.
     * @param locationServiceDTO the locationServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationServiceDTO,
     * or with status {@code 400 (Bad Request)} if the locationServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/location-services/{id}")
    public ResponseEntity<LocationServiceDTO> updateLocationService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LocationServiceDTO locationServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LocationService : {}, {}", id, locationServiceDTO);
        if (locationServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LocationServiceDTO result = locationServiceService.update(locationServiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locationServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /location-services/:id} : Partial updates given fields of an existing locationService, field will ignore if it is null
     *
     * @param id the id of the locationServiceDTO to save.
     * @param locationServiceDTO the locationServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationServiceDTO,
     * or with status {@code 400 (Bad Request)} if the locationServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the locationServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the locationServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/location-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocationServiceDTO> partialUpdateLocationService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LocationServiceDTO locationServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LocationService partially : {}, {}", id, locationServiceDTO);
        if (locationServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locationServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocationServiceDTO> result = locationServiceService.partialUpdate(locationServiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locationServiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /location-services} : get all the locationServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locationServices in body.
     */
    @GetMapping("/location-services")
    public ResponseEntity<List<LocationServiceDTO>> getAllLocationServices(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of LocationServices");
        Page<LocationServiceDTO> page = locationServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /location-services/:id} : get the "id" locationService.
     *
     * @param id the id of the locationServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/location-services/{id}")
    public ResponseEntity<LocationServiceDTO> getLocationService(@PathVariable Long id) {
        log.debug("REST request to get LocationService : {}", id);
        Optional<LocationServiceDTO> locationServiceDTO = locationServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locationServiceDTO);
    }

    /**
     * {@code DELETE  /location-services/:id} : delete the "id" locationService.
     *
     * @param id the id of the locationServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/location-services/{id}")
    public ResponseEntity<Void> deleteLocationService(@PathVariable Long id) {
        log.debug("REST request to delete LocationService : {}", id);
        locationServiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
