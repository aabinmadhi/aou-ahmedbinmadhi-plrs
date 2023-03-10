package aou.ahmedbinmadhi.plrs.web.rest;

import aou.ahmedbinmadhi.plrs.repository.AnalyticsCenterRepository;
import aou.ahmedbinmadhi.plrs.service.AnalyticsCenterService;
import aou.ahmedbinmadhi.plrs.service.dto.AnalyticsCenterDTO;
import aou.ahmedbinmadhi.plrs.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link aou.ahmedbinmadhi.plrs.domain.AnalyticsCenter}.
 */
@RestController
@RequestMapping("/api")
public class AnalyticsCenterResource {

    private final Logger log = LoggerFactory.getLogger(AnalyticsCenterResource.class);

    private static final String ENTITY_NAME = "analyticsCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalyticsCenterService analyticsCenterService;

    private final AnalyticsCenterRepository analyticsCenterRepository;

    public AnalyticsCenterResource(AnalyticsCenterService analyticsCenterService, AnalyticsCenterRepository analyticsCenterRepository) {
        this.analyticsCenterService = analyticsCenterService;
        this.analyticsCenterRepository = analyticsCenterRepository;
    }

    /**
     * {@code POST  /analytics-centers} : Create a new analyticsCenter.
     *
     * @param analyticsCenterDTO the analyticsCenterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analyticsCenterDTO, or with status {@code 400 (Bad Request)} if the analyticsCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analytics-centers")
    public ResponseEntity<AnalyticsCenterDTO> createAnalyticsCenter(@RequestBody AnalyticsCenterDTO analyticsCenterDTO)
        throws URISyntaxException {
        log.debug("REST request to save AnalyticsCenter : {}", analyticsCenterDTO);
        if (analyticsCenterDTO.getId() != null) {
            throw new BadRequestAlertException("A new analyticsCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalyticsCenterDTO result = analyticsCenterService.save(analyticsCenterDTO);
        return ResponseEntity
            .created(new URI("/api/analytics-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analytics-centers/:id} : Updates an existing analyticsCenter.
     *
     * @param id the id of the analyticsCenterDTO to save.
     * @param analyticsCenterDTO the analyticsCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analyticsCenterDTO,
     * or with status {@code 400 (Bad Request)} if the analyticsCenterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analyticsCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analytics-centers/{id}")
    public ResponseEntity<AnalyticsCenterDTO> updateAnalyticsCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalyticsCenterDTO analyticsCenterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnalyticsCenter : {}, {}", id, analyticsCenterDTO);
        if (analyticsCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analyticsCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analyticsCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnalyticsCenterDTO result = analyticsCenterService.update(analyticsCenterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analyticsCenterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /analytics-centers/:id} : Partial updates given fields of an existing analyticsCenter, field will ignore if it is null
     *
     * @param id the id of the analyticsCenterDTO to save.
     * @param analyticsCenterDTO the analyticsCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analyticsCenterDTO,
     * or with status {@code 400 (Bad Request)} if the analyticsCenterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the analyticsCenterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the analyticsCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analytics-centers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnalyticsCenterDTO> partialUpdateAnalyticsCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnalyticsCenterDTO analyticsCenterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnalyticsCenter partially : {}, {}", id, analyticsCenterDTO);
        if (analyticsCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analyticsCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analyticsCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnalyticsCenterDTO> result = analyticsCenterService.partialUpdate(analyticsCenterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analyticsCenterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /analytics-centers} : get all the analyticsCenters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analyticsCenters in body.
     */
    @GetMapping("/analytics-centers")
    public List<AnalyticsCenterDTO> getAllAnalyticsCenters() {
        log.debug("REST request to get all AnalyticsCenters");
        return analyticsCenterService.findAll();
    }

    /**
     * {@code GET  /analytics-centers/:id} : get the "id" analyticsCenter.
     *
     * @param id the id of the analyticsCenterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analyticsCenterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analytics-centers/{id}")
    public ResponseEntity<AnalyticsCenterDTO> getAnalyticsCenter(@PathVariable Long id) {
        log.debug("REST request to get AnalyticsCenter : {}", id);
        Optional<AnalyticsCenterDTO> analyticsCenterDTO = analyticsCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analyticsCenterDTO);
    }

    /**
     * {@code DELETE  /analytics-centers/:id} : delete the "id" analyticsCenter.
     *
     * @param id the id of the analyticsCenterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analytics-centers/{id}")
    public ResponseEntity<Void> deleteAnalyticsCenter(@PathVariable Long id) {
        log.debug("REST request to delete AnalyticsCenter : {}", id);
        analyticsCenterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
