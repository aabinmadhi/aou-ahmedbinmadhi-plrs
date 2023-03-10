package aou.ahmedbinmadhi.plrs.web.rest;

import aou.ahmedbinmadhi.plrs.repository.PlrsCommissionRepository;
import aou.ahmedbinmadhi.plrs.service.PlrsCommissionService;
import aou.ahmedbinmadhi.plrs.service.dto.PlrsCommissionDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link aou.ahmedbinmadhi.plrs.domain.PlrsCommission}.
 */
@RestController
@RequestMapping("/api")
public class PlrsCommissionResource {

    private final Logger log = LoggerFactory.getLogger(PlrsCommissionResource.class);

    private static final String ENTITY_NAME = "plrsCommission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlrsCommissionService plrsCommissionService;

    private final PlrsCommissionRepository plrsCommissionRepository;

    public PlrsCommissionResource(PlrsCommissionService plrsCommissionService, PlrsCommissionRepository plrsCommissionRepository) {
        this.plrsCommissionService = plrsCommissionService;
        this.plrsCommissionRepository = plrsCommissionRepository;
    }

    /**
     * {@code POST  /plrs-commissions} : Create a new plrsCommission.
     *
     * @param plrsCommissionDTO the plrsCommissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plrsCommissionDTO, or with status {@code 400 (Bad Request)} if the plrsCommission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plrs-commissions")
    public ResponseEntity<PlrsCommissionDTO> createPlrsCommission(@Valid @RequestBody PlrsCommissionDTO plrsCommissionDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlrsCommission : {}", plrsCommissionDTO);
        if (plrsCommissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new plrsCommission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlrsCommissionDTO result = plrsCommissionService.save(plrsCommissionDTO);
        return ResponseEntity
            .created(new URI("/api/plrs-commissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plrs-commissions/:id} : Updates an existing plrsCommission.
     *
     * @param id the id of the plrsCommissionDTO to save.
     * @param plrsCommissionDTO the plrsCommissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plrsCommissionDTO,
     * or with status {@code 400 (Bad Request)} if the plrsCommissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plrsCommissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plrs-commissions/{id}")
    public ResponseEntity<PlrsCommissionDTO> updatePlrsCommission(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlrsCommissionDTO plrsCommissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlrsCommission : {}, {}", id, plrsCommissionDTO);
        if (plrsCommissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plrsCommissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plrsCommissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlrsCommissionDTO result = plrsCommissionService.update(plrsCommissionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plrsCommissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plrs-commissions/:id} : Partial updates given fields of an existing plrsCommission, field will ignore if it is null
     *
     * @param id the id of the plrsCommissionDTO to save.
     * @param plrsCommissionDTO the plrsCommissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plrsCommissionDTO,
     * or with status {@code 400 (Bad Request)} if the plrsCommissionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plrsCommissionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plrsCommissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plrs-commissions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlrsCommissionDTO> partialUpdatePlrsCommission(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlrsCommissionDTO plrsCommissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlrsCommission partially : {}, {}", id, plrsCommissionDTO);
        if (plrsCommissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plrsCommissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plrsCommissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlrsCommissionDTO> result = plrsCommissionService.partialUpdate(plrsCommissionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plrsCommissionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plrs-commissions} : get all the plrsCommissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plrsCommissions in body.
     */
    @GetMapping("/plrs-commissions")
    public List<PlrsCommissionDTO> getAllPlrsCommissions() {
        log.debug("REST request to get all PlrsCommissions");
        return plrsCommissionService.findAll();
    }

    /**
     * {@code GET  /plrs-commissions/:id} : get the "id" plrsCommission.
     *
     * @param id the id of the plrsCommissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plrsCommissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plrs-commissions/{id}")
    public ResponseEntity<PlrsCommissionDTO> getPlrsCommission(@PathVariable Long id) {
        log.debug("REST request to get PlrsCommission : {}", id);
        Optional<PlrsCommissionDTO> plrsCommissionDTO = plrsCommissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plrsCommissionDTO);
    }

    /**
     * {@code DELETE  /plrs-commissions/:id} : delete the "id" plrsCommission.
     *
     * @param id the id of the plrsCommissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plrs-commissions/{id}")
    public ResponseEntity<Void> deletePlrsCommission(@PathVariable Long id) {
        log.debug("REST request to delete PlrsCommission : {}", id);
        plrsCommissionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
