package aou.ahmedbinmadhi.plrs.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import aou.ahmedbinmadhi.plrs.IntegrationTest;
import aou.ahmedbinmadhi.plrs.domain.Profit;
import aou.ahmedbinmadhi.plrs.repository.ProfitRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProfitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfitResourceIT {

    private static final Double DEFAULT_USER_PROFIT_AMOUNT_PER_BOOKING = 1D;
    private static final Double UPDATED_USER_PROFIT_AMOUNT_PER_BOOKING = 2D;

    private static final Double DEFAULT_PLRS_PROFIT_AMOUNT_PER_BOOKING = 1D;
    private static final Double UPDATED_PLRS_PROFIT_AMOUNT_PER_BOOKING = 2D;

    private static final Double DEFAULT_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE = 1D;
    private static final Double UPDATED_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE = 2D;

    private static final Double DEFAULT_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE = 1D;
    private static final Double UPDATED_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE = 2D;

    private static final Double DEFAULT_USER_TOTAL_PROFIT = 1D;
    private static final Double UPDATED_USER_TOTAL_PROFIT = 2D;

    private static final Double DEFAULT_PLRS_TOTAL_PROFIT = 1D;
    private static final Double UPDATED_PLRS_TOTAL_PROFIT = 2D;

    private static final String ENTITY_API_URL = "/api/profits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfitRepository profitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfitMockMvc;

    private Profit profit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profit createEntity(EntityManager em) {
        Profit profit = new Profit()
            .userProfitAmountPerBooking(DEFAULT_USER_PROFIT_AMOUNT_PER_BOOKING)
            .plrsProfitAmountPerBooking(DEFAULT_PLRS_PROFIT_AMOUNT_PER_BOOKING)
            .userProfitAmountPerLocationService(DEFAULT_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE)
            .plrsProfitAmountPerLocationService(DEFAULT_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE)
            .userTotalProfit(DEFAULT_USER_TOTAL_PROFIT)
            .plrsTotalProfit(DEFAULT_PLRS_TOTAL_PROFIT);
        return profit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profit createUpdatedEntity(EntityManager em) {
        Profit profit = new Profit()
            .userProfitAmountPerBooking(UPDATED_USER_PROFIT_AMOUNT_PER_BOOKING)
            .plrsProfitAmountPerBooking(UPDATED_PLRS_PROFIT_AMOUNT_PER_BOOKING)
            .userProfitAmountPerLocationService(UPDATED_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE)
            .plrsProfitAmountPerLocationService(UPDATED_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE)
            .userTotalProfit(UPDATED_USER_TOTAL_PROFIT)
            .plrsTotalProfit(UPDATED_PLRS_TOTAL_PROFIT);
        return profit;
    }

    @BeforeEach
    public void initTest() {
        profit = createEntity(em);
    }

    @Test
    @Transactional
    void createProfit() throws Exception {
        int databaseSizeBeforeCreate = profitRepository.findAll().size();
        // Create the Profit
        restProfitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profit)))
            .andExpect(status().isCreated());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeCreate + 1);
        Profit testProfit = profitList.get(profitList.size() - 1);
        assertThat(testProfit.getUserProfitAmountPerBooking()).isEqualTo(DEFAULT_USER_PROFIT_AMOUNT_PER_BOOKING);
        assertThat(testProfit.getPlrsProfitAmountPerBooking()).isEqualTo(DEFAULT_PLRS_PROFIT_AMOUNT_PER_BOOKING);
        assertThat(testProfit.getUserProfitAmountPerLocationService()).isEqualTo(DEFAULT_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE);
        assertThat(testProfit.getPlrsProfitAmountPerLocationService()).isEqualTo(DEFAULT_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE);
        assertThat(testProfit.getUserTotalProfit()).isEqualTo(DEFAULT_USER_TOTAL_PROFIT);
        assertThat(testProfit.getPlrsTotalProfit()).isEqualTo(DEFAULT_PLRS_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void createProfitWithExistingId() throws Exception {
        // Create the Profit with an existing ID
        profit.setId(1L);

        int databaseSizeBeforeCreate = profitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profit)))
            .andExpect(status().isBadRequest());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfits() throws Exception {
        // Initialize the database
        profitRepository.saveAndFlush(profit);

        // Get all the profitList
        restProfitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profit.getId().intValue())))
            .andExpect(jsonPath("$.[*].userProfitAmountPerBooking").value(hasItem(DEFAULT_USER_PROFIT_AMOUNT_PER_BOOKING.doubleValue())))
            .andExpect(jsonPath("$.[*].plrsProfitAmountPerBooking").value(hasItem(DEFAULT_PLRS_PROFIT_AMOUNT_PER_BOOKING.doubleValue())))
            .andExpect(
                jsonPath("$.[*].userProfitAmountPerLocationService")
                    .value(hasItem(DEFAULT_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].plrsProfitAmountPerLocationService")
                    .value(hasItem(DEFAULT_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].userTotalProfit").value(hasItem(DEFAULT_USER_TOTAL_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].plrsTotalProfit").value(hasItem(DEFAULT_PLRS_TOTAL_PROFIT.doubleValue())));
    }

    @Test
    @Transactional
    void getProfit() throws Exception {
        // Initialize the database
        profitRepository.saveAndFlush(profit);

        // Get the profit
        restProfitMockMvc
            .perform(get(ENTITY_API_URL_ID, profit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profit.getId().intValue()))
            .andExpect(jsonPath("$.userProfitAmountPerBooking").value(DEFAULT_USER_PROFIT_AMOUNT_PER_BOOKING.doubleValue()))
            .andExpect(jsonPath("$.plrsProfitAmountPerBooking").value(DEFAULT_PLRS_PROFIT_AMOUNT_PER_BOOKING.doubleValue()))
            .andExpect(
                jsonPath("$.userProfitAmountPerLocationService").value(DEFAULT_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE.doubleValue())
            )
            .andExpect(
                jsonPath("$.plrsProfitAmountPerLocationService").value(DEFAULT_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE.doubleValue())
            )
            .andExpect(jsonPath("$.userTotalProfit").value(DEFAULT_USER_TOTAL_PROFIT.doubleValue()))
            .andExpect(jsonPath("$.plrsTotalProfit").value(DEFAULT_PLRS_TOTAL_PROFIT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingProfit() throws Exception {
        // Get the profit
        restProfitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfit() throws Exception {
        // Initialize the database
        profitRepository.saveAndFlush(profit);

        int databaseSizeBeforeUpdate = profitRepository.findAll().size();

        // Update the profit
        Profit updatedProfit = profitRepository.findById(profit.getId()).get();
        // Disconnect from session so that the updates on updatedProfit are not directly saved in db
        em.detach(updatedProfit);
        updatedProfit
            .userProfitAmountPerBooking(UPDATED_USER_PROFIT_AMOUNT_PER_BOOKING)
            .plrsProfitAmountPerBooking(UPDATED_PLRS_PROFIT_AMOUNT_PER_BOOKING)
            .userProfitAmountPerLocationService(UPDATED_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE)
            .plrsProfitAmountPerLocationService(UPDATED_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE)
            .userTotalProfit(UPDATED_USER_TOTAL_PROFIT)
            .plrsTotalProfit(UPDATED_PLRS_TOTAL_PROFIT);

        restProfitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProfit))
            )
            .andExpect(status().isOk());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeUpdate);
        Profit testProfit = profitList.get(profitList.size() - 1);
        assertThat(testProfit.getUserProfitAmountPerBooking()).isEqualTo(UPDATED_USER_PROFIT_AMOUNT_PER_BOOKING);
        assertThat(testProfit.getPlrsProfitAmountPerBooking()).isEqualTo(UPDATED_PLRS_PROFIT_AMOUNT_PER_BOOKING);
        assertThat(testProfit.getUserProfitAmountPerLocationService()).isEqualTo(UPDATED_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE);
        assertThat(testProfit.getPlrsProfitAmountPerLocationService()).isEqualTo(UPDATED_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE);
        assertThat(testProfit.getUserTotalProfit()).isEqualTo(UPDATED_USER_TOTAL_PROFIT);
        assertThat(testProfit.getPlrsTotalProfit()).isEqualTo(UPDATED_PLRS_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void putNonExistingProfit() throws Exception {
        int databaseSizeBeforeUpdate = profitRepository.findAll().size();
        profit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfit() throws Exception {
        int databaseSizeBeforeUpdate = profitRepository.findAll().size();
        profit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfit() throws Exception {
        int databaseSizeBeforeUpdate = profitRepository.findAll().size();
        profit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfitWithPatch() throws Exception {
        // Initialize the database
        profitRepository.saveAndFlush(profit);

        int databaseSizeBeforeUpdate = profitRepository.findAll().size();

        // Update the profit using partial update
        Profit partialUpdatedProfit = new Profit();
        partialUpdatedProfit.setId(profit.getId());

        partialUpdatedProfit
            .userProfitAmountPerBooking(UPDATED_USER_PROFIT_AMOUNT_PER_BOOKING)
            .userProfitAmountPerLocationService(UPDATED_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE)
            .userTotalProfit(UPDATED_USER_TOTAL_PROFIT)
            .plrsTotalProfit(UPDATED_PLRS_TOTAL_PROFIT);

        restProfitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfit))
            )
            .andExpect(status().isOk());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeUpdate);
        Profit testProfit = profitList.get(profitList.size() - 1);
        assertThat(testProfit.getUserProfitAmountPerBooking()).isEqualTo(UPDATED_USER_PROFIT_AMOUNT_PER_BOOKING);
        assertThat(testProfit.getPlrsProfitAmountPerBooking()).isEqualTo(DEFAULT_PLRS_PROFIT_AMOUNT_PER_BOOKING);
        assertThat(testProfit.getUserProfitAmountPerLocationService()).isEqualTo(UPDATED_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE);
        assertThat(testProfit.getPlrsProfitAmountPerLocationService()).isEqualTo(DEFAULT_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE);
        assertThat(testProfit.getUserTotalProfit()).isEqualTo(UPDATED_USER_TOTAL_PROFIT);
        assertThat(testProfit.getPlrsTotalProfit()).isEqualTo(UPDATED_PLRS_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void fullUpdateProfitWithPatch() throws Exception {
        // Initialize the database
        profitRepository.saveAndFlush(profit);

        int databaseSizeBeforeUpdate = profitRepository.findAll().size();

        // Update the profit using partial update
        Profit partialUpdatedProfit = new Profit();
        partialUpdatedProfit.setId(profit.getId());

        partialUpdatedProfit
            .userProfitAmountPerBooking(UPDATED_USER_PROFIT_AMOUNT_PER_BOOKING)
            .plrsProfitAmountPerBooking(UPDATED_PLRS_PROFIT_AMOUNT_PER_BOOKING)
            .userProfitAmountPerLocationService(UPDATED_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE)
            .plrsProfitAmountPerLocationService(UPDATED_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE)
            .userTotalProfit(UPDATED_USER_TOTAL_PROFIT)
            .plrsTotalProfit(UPDATED_PLRS_TOTAL_PROFIT);

        restProfitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfit))
            )
            .andExpect(status().isOk());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeUpdate);
        Profit testProfit = profitList.get(profitList.size() - 1);
        assertThat(testProfit.getUserProfitAmountPerBooking()).isEqualTo(UPDATED_USER_PROFIT_AMOUNT_PER_BOOKING);
        assertThat(testProfit.getPlrsProfitAmountPerBooking()).isEqualTo(UPDATED_PLRS_PROFIT_AMOUNT_PER_BOOKING);
        assertThat(testProfit.getUserProfitAmountPerLocationService()).isEqualTo(UPDATED_USER_PROFIT_AMOUNT_PER_LOCATION_SERVICE);
        assertThat(testProfit.getPlrsProfitAmountPerLocationService()).isEqualTo(UPDATED_PLRS_PROFIT_AMOUNT_PER_LOCATION_SERVICE);
        assertThat(testProfit.getUserTotalProfit()).isEqualTo(UPDATED_USER_TOTAL_PROFIT);
        assertThat(testProfit.getPlrsTotalProfit()).isEqualTo(UPDATED_PLRS_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void patchNonExistingProfit() throws Exception {
        int databaseSizeBeforeUpdate = profitRepository.findAll().size();
        profit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfit() throws Exception {
        int databaseSizeBeforeUpdate = profitRepository.findAll().size();
        profit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfit() throws Exception {
        int databaseSizeBeforeUpdate = profitRepository.findAll().size();
        profit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(profit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profit in the database
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfit() throws Exception {
        // Initialize the database
        profitRepository.saveAndFlush(profit);

        int databaseSizeBeforeDelete = profitRepository.findAll().size();

        // Delete the profit
        restProfitMockMvc
            .perform(delete(ENTITY_API_URL_ID, profit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profit> profitList = profitRepository.findAll();
        assertThat(profitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
