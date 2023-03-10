package aou.ahmedbinmadhi.plrs.web.rest;

import aou.ahmedbinmadhi.plrs.repository.ProfitRepository;
import aou.ahmedbinmadhi.plrs.service.ProfitService;
import aou.ahmedbinmadhi.plrs.service.dto.ProfitDTO;
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
 * REST controller for managing {@link aou.ahmedbinmadhi.plrs.domain.Profit}.
 */
@RestController
@RequestMapping("/api")
public class ProfitResource {

    private final Logger log = LoggerFactory.getLogger(ProfitResource.class);

    private static final String ENTITY_NAME = "profit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfitService profitService;

    private final ProfitRepository profitRepository;

    public ProfitResource(ProfitService profitService, ProfitRepository profitRepository) {
        this.profitService = profitService;
        this.profitRepository = profitRepository;
    }

    /**
     * {@code POST  /profits} : Create a new profit.
     *
     * @param profitDTO the profitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profitDTO, or with status {@code 400 (Bad Request)} if the profit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profits")
    public ResponseEntity<ProfitDTO> createProfit(@RequestBody ProfitDTO profitDTO) throws URISyntaxException {
        log.debug("REST request to save Profit : {}", profitDTO);
        if (profitDTO.getId() != null) {
            throw new BadRequestAlertException("A new profit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfitDTO result = profitService.save(profitDTO);
        return ResponseEntity
            .created(new URI("/api/profits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profits/:id} : Updates an existing profit.
     *
     * @param id the id of the profitDTO to save.
     * @param profitDTO the profitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profitDTO,
     * or with status {@code 400 (Bad Request)} if the profitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profits/{id}")
    public ResponseEntity<ProfitDTO> updateProfit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProfitDTO profitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Profit : {}, {}", id, profitDTO);
        if (profitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProfitDTO result = profitService.update(profitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /profits/:id} : Partial updates given fields of an existing profit, field will ignore if it is null
     *
     * @param id the id of the profitDTO to save.
     * @param profitDTO the profitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profitDTO,
     * or with status {@code 400 (Bad Request)} if the profitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the profitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the profitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/profits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProfitDTO> partialUpdateProfit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProfitDTO profitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Profit partially : {}, {}", id, profitDTO);
        if (profitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProfitDTO> result = profitService.partialUpdate(profitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /profits} : get all the profits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profits in body.
     */
    @GetMapping("/profits")
    public List<ProfitDTO> getAllProfits() {
        log.debug("REST request to get all Profits");
        return profitService.findAll();
    }

    /**
     * {@code GET  /profits/:id} : get the "id" profit.
     *
     * @param id the id of the profitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profits/{id}")
    public ResponseEntity<ProfitDTO> getProfit(@PathVariable Long id) {
        log.debug("REST request to get Profit : {}", id);
        Optional<ProfitDTO> profitDTO = profitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profitDTO);
    }

    /**
     * {@code DELETE  /profits/:id} : delete the "id" profit.
     *
     * @param id the id of the profitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profits/{id}")
    public ResponseEntity<Void> deleteProfit(@PathVariable Long id) {
        log.debug("REST request to delete Profit : {}", id);
        profitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
