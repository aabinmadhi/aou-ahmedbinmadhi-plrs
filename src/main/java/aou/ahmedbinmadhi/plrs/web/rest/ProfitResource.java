package aou.ahmedbinmadhi.plrs.web.rest;

import aou.ahmedbinmadhi.plrs.domain.Profit;
import aou.ahmedbinmadhi.plrs.repository.ProfitRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link aou.ahmedbinmadhi.plrs.domain.Profit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProfitResource {

    private final Logger log = LoggerFactory.getLogger(ProfitResource.class);

    private static final String ENTITY_NAME = "profit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfitRepository profitRepository;

    public ProfitResource(ProfitRepository profitRepository) {
        this.profitRepository = profitRepository;
    }

    /**
     * {@code POST  /profits} : Create a new profit.
     *
     * @param profit the profit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profit, or with status {@code 400 (Bad Request)} if the profit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profits")
    public ResponseEntity<Profit> createProfit(@RequestBody Profit profit) throws URISyntaxException {
        log.debug("REST request to save Profit : {}", profit);
        if (profit.getId() != null) {
            throw new BadRequestAlertException("A new profit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Profit result = profitRepository.save(profit);
        return ResponseEntity
            .created(new URI("/api/profits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profits/:id} : Updates an existing profit.
     *
     * @param id the id of the profit to save.
     * @param profit the profit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profit,
     * or with status {@code 400 (Bad Request)} if the profit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profits/{id}")
    public ResponseEntity<Profit> updateProfit(@PathVariable(value = "id", required = false) final Long id, @RequestBody Profit profit)
        throws URISyntaxException {
        log.debug("REST request to update Profit : {}, {}", id, profit);
        if (profit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Profit result = profitRepository.save(profit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /profits/:id} : Partial updates given fields of an existing profit, field will ignore if it is null
     *
     * @param id the id of the profit to save.
     * @param profit the profit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profit,
     * or with status {@code 400 (Bad Request)} if the profit is not valid,
     * or with status {@code 404 (Not Found)} if the profit is not found,
     * or with status {@code 500 (Internal Server Error)} if the profit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/profits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Profit> partialUpdateProfit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Profit profit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Profit partially : {}, {}", id, profit);
        if (profit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Profit> result = profitRepository
            .findById(profit.getId())
            .map(existingProfit -> {
                if (profit.getUserProfitAmountPerBooking() != null) {
                    existingProfit.setUserProfitAmountPerBooking(profit.getUserProfitAmountPerBooking());
                }
                if (profit.getPlrsProfitAmountPerBooking() != null) {
                    existingProfit.setPlrsProfitAmountPerBooking(profit.getPlrsProfitAmountPerBooking());
                }
                if (profit.getUserProfitAmountPerLocationService() != null) {
                    existingProfit.setUserProfitAmountPerLocationService(profit.getUserProfitAmountPerLocationService());
                }
                if (profit.getPlrsProfitAmountPerLocationService() != null) {
                    existingProfit.setPlrsProfitAmountPerLocationService(profit.getPlrsProfitAmountPerLocationService());
                }
                if (profit.getUserTotalProfit() != null) {
                    existingProfit.setUserTotalProfit(profit.getUserTotalProfit());
                }
                if (profit.getPlrsTotalProfit() != null) {
                    existingProfit.setPlrsTotalProfit(profit.getPlrsTotalProfit());
                }

                return existingProfit;
            })
            .map(profitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profit.getId().toString())
        );
    }

    /**
     * {@code GET  /profits} : get all the profits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profits in body.
     */
    @GetMapping("/profits")
    public List<Profit> getAllProfits() {
        log.debug("REST request to get all Profits");
        return profitRepository.findAll();
    }

    /**
     * {@code GET  /profits/:id} : get the "id" profit.
     *
     * @param id the id of the profit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profits/{id}")
    public ResponseEntity<Profit> getProfit(@PathVariable Long id) {
        log.debug("REST request to get Profit : {}", id);
        Optional<Profit> profit = profitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(profit);
    }

    /**
     * {@code DELETE  /profits/:id} : delete the "id" profit.
     *
     * @param id the id of the profit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profits/{id}")
    public ResponseEntity<Void> deleteProfit(@PathVariable Long id) {
        log.debug("REST request to delete Profit : {}", id);
        profitRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
