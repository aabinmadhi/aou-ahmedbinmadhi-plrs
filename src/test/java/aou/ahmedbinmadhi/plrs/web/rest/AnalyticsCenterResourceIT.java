package aou.ahmedbinmadhi.plrs.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import aou.ahmedbinmadhi.plrs.IntegrationTest;
import aou.ahmedbinmadhi.plrs.domain.AnalyticsCenter;
import aou.ahmedbinmadhi.plrs.repository.AnalyticsCenterRepository;
import aou.ahmedbinmadhi.plrs.service.dto.AnalyticsCenterDTO;
import aou.ahmedbinmadhi.plrs.service.mapper.AnalyticsCenterMapper;
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
 * Integration tests for the {@link AnalyticsCenterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalyticsCenterResourceIT {

    private static final Double DEFAULT_TOTAL_REVENUE_PER_L_PROVIDER = 1D;
    private static final Double UPDATED_TOTAL_REVENUE_PER_L_PROVIDER = 2D;

    private static final Double DEFAULT_NUMBER_OF_LOCATIONS = 1D;
    private static final Double UPDATED_NUMBER_OF_LOCATIONS = 2D;

    private static final Double DEFAULT_COUNT_OF_BOOKING_PER_LOCATION_SERVICE = 1D;
    private static final Double UPDATED_COUNT_OF_BOOKING_PER_LOCATION_SERVICE = 2D;

    private static final Double DEFAULT_TOTAL_REVENUE_PER_LOCATION_SERVICE = 1D;
    private static final Double UPDATED_TOTAL_REVENUE_PER_LOCATION_SERVICE = 2D;

    private static final String ENTITY_API_URL = "/api/analytics-centers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalyticsCenterRepository analyticsCenterRepository;

    @Autowired
    private AnalyticsCenterMapper analyticsCenterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalyticsCenterMockMvc;

    private AnalyticsCenter analyticsCenter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyticsCenter createEntity(EntityManager em) {
        AnalyticsCenter analyticsCenter = new AnalyticsCenter()
            .totalRevenuePerLProvider(DEFAULT_TOTAL_REVENUE_PER_L_PROVIDER)
            .numberOfLocations(DEFAULT_NUMBER_OF_LOCATIONS)
            .countOfBookingPerLocationService(DEFAULT_COUNT_OF_BOOKING_PER_LOCATION_SERVICE)
            .totalRevenuePerLocationService(DEFAULT_TOTAL_REVENUE_PER_LOCATION_SERVICE);
        return analyticsCenter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyticsCenter createUpdatedEntity(EntityManager em) {
        AnalyticsCenter analyticsCenter = new AnalyticsCenter()
            .totalRevenuePerLProvider(UPDATED_TOTAL_REVENUE_PER_L_PROVIDER)
            .numberOfLocations(UPDATED_NUMBER_OF_LOCATIONS)
            .countOfBookingPerLocationService(UPDATED_COUNT_OF_BOOKING_PER_LOCATION_SERVICE)
            .totalRevenuePerLocationService(UPDATED_TOTAL_REVENUE_PER_LOCATION_SERVICE);
        return analyticsCenter;
    }

    @BeforeEach
    public void initTest() {
        analyticsCenter = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalyticsCenter() throws Exception {
        int databaseSizeBeforeCreate = analyticsCenterRepository.findAll().size();
        // Create the AnalyticsCenter
        AnalyticsCenterDTO analyticsCenterDTO = analyticsCenterMapper.toDto(analyticsCenter);
        restAnalyticsCenterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyticsCenterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeCreate + 1);
        AnalyticsCenter testAnalyticsCenter = analyticsCenterList.get(analyticsCenterList.size() - 1);
        assertThat(testAnalyticsCenter.getTotalRevenuePerLProvider()).isEqualTo(DEFAULT_TOTAL_REVENUE_PER_L_PROVIDER);
        assertThat(testAnalyticsCenter.getNumberOfLocations()).isEqualTo(DEFAULT_NUMBER_OF_LOCATIONS);
        assertThat(testAnalyticsCenter.getCountOfBookingPerLocationService()).isEqualTo(DEFAULT_COUNT_OF_BOOKING_PER_LOCATION_SERVICE);
        assertThat(testAnalyticsCenter.getTotalRevenuePerLocationService()).isEqualTo(DEFAULT_TOTAL_REVENUE_PER_LOCATION_SERVICE);
    }

    @Test
    @Transactional
    void createAnalyticsCenterWithExistingId() throws Exception {
        // Create the AnalyticsCenter with an existing ID
        analyticsCenter.setId(1L);
        AnalyticsCenterDTO analyticsCenterDTO = analyticsCenterMapper.toDto(analyticsCenter);

        int databaseSizeBeforeCreate = analyticsCenterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalyticsCenterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyticsCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnalyticsCenters() throws Exception {
        // Initialize the database
        analyticsCenterRepository.saveAndFlush(analyticsCenter);

        // Get all the analyticsCenterList
        restAnalyticsCenterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analyticsCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalRevenuePerLProvider").value(hasItem(DEFAULT_TOTAL_REVENUE_PER_L_PROVIDER.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfLocations").value(hasItem(DEFAULT_NUMBER_OF_LOCATIONS.doubleValue())))
            .andExpect(
                jsonPath("$.[*].countOfBookingPerLocationService")
                    .value(hasItem(DEFAULT_COUNT_OF_BOOKING_PER_LOCATION_SERVICE.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].totalRevenuePerLocationService").value(hasItem(DEFAULT_TOTAL_REVENUE_PER_LOCATION_SERVICE.doubleValue()))
            );
    }

    @Test
    @Transactional
    void getAnalyticsCenter() throws Exception {
        // Initialize the database
        analyticsCenterRepository.saveAndFlush(analyticsCenter);

        // Get the analyticsCenter
        restAnalyticsCenterMockMvc
            .perform(get(ENTITY_API_URL_ID, analyticsCenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analyticsCenter.getId().intValue()))
            .andExpect(jsonPath("$.totalRevenuePerLProvider").value(DEFAULT_TOTAL_REVENUE_PER_L_PROVIDER.doubleValue()))
            .andExpect(jsonPath("$.numberOfLocations").value(DEFAULT_NUMBER_OF_LOCATIONS.doubleValue()))
            .andExpect(jsonPath("$.countOfBookingPerLocationService").value(DEFAULT_COUNT_OF_BOOKING_PER_LOCATION_SERVICE.doubleValue()))
            .andExpect(jsonPath("$.totalRevenuePerLocationService").value(DEFAULT_TOTAL_REVENUE_PER_LOCATION_SERVICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnalyticsCenter() throws Exception {
        // Get the analyticsCenter
        restAnalyticsCenterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnalyticsCenter() throws Exception {
        // Initialize the database
        analyticsCenterRepository.saveAndFlush(analyticsCenter);

        int databaseSizeBeforeUpdate = analyticsCenterRepository.findAll().size();

        // Update the analyticsCenter
        AnalyticsCenter updatedAnalyticsCenter = analyticsCenterRepository.findById(analyticsCenter.getId()).get();
        // Disconnect from session so that the updates on updatedAnalyticsCenter are not directly saved in db
        em.detach(updatedAnalyticsCenter);
        updatedAnalyticsCenter
            .totalRevenuePerLProvider(UPDATED_TOTAL_REVENUE_PER_L_PROVIDER)
            .numberOfLocations(UPDATED_NUMBER_OF_LOCATIONS)
            .countOfBookingPerLocationService(UPDATED_COUNT_OF_BOOKING_PER_LOCATION_SERVICE)
            .totalRevenuePerLocationService(UPDATED_TOTAL_REVENUE_PER_LOCATION_SERVICE);
        AnalyticsCenterDTO analyticsCenterDTO = analyticsCenterMapper.toDto(updatedAnalyticsCenter);

        restAnalyticsCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyticsCenterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyticsCenterDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeUpdate);
        AnalyticsCenter testAnalyticsCenter = analyticsCenterList.get(analyticsCenterList.size() - 1);
        assertThat(testAnalyticsCenter.getTotalRevenuePerLProvider()).isEqualTo(UPDATED_TOTAL_REVENUE_PER_L_PROVIDER);
        assertThat(testAnalyticsCenter.getNumberOfLocations()).isEqualTo(UPDATED_NUMBER_OF_LOCATIONS);
        assertThat(testAnalyticsCenter.getCountOfBookingPerLocationService()).isEqualTo(UPDATED_COUNT_OF_BOOKING_PER_LOCATION_SERVICE);
        assertThat(testAnalyticsCenter.getTotalRevenuePerLocationService()).isEqualTo(UPDATED_TOTAL_REVENUE_PER_LOCATION_SERVICE);
    }

    @Test
    @Transactional
    void putNonExistingAnalyticsCenter() throws Exception {
        int databaseSizeBeforeUpdate = analyticsCenterRepository.findAll().size();
        analyticsCenter.setId(count.incrementAndGet());

        // Create the AnalyticsCenter
        AnalyticsCenterDTO analyticsCenterDTO = analyticsCenterMapper.toDto(analyticsCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyticsCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyticsCenterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyticsCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalyticsCenter() throws Exception {
        int databaseSizeBeforeUpdate = analyticsCenterRepository.findAll().size();
        analyticsCenter.setId(count.incrementAndGet());

        // Create the AnalyticsCenter
        AnalyticsCenterDTO analyticsCenterDTO = analyticsCenterMapper.toDto(analyticsCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyticsCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyticsCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalyticsCenter() throws Exception {
        int databaseSizeBeforeUpdate = analyticsCenterRepository.findAll().size();
        analyticsCenter.setId(count.incrementAndGet());

        // Create the AnalyticsCenter
        AnalyticsCenterDTO analyticsCenterDTO = analyticsCenterMapper.toDto(analyticsCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyticsCenterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyticsCenterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalyticsCenterWithPatch() throws Exception {
        // Initialize the database
        analyticsCenterRepository.saveAndFlush(analyticsCenter);

        int databaseSizeBeforeUpdate = analyticsCenterRepository.findAll().size();

        // Update the analyticsCenter using partial update
        AnalyticsCenter partialUpdatedAnalyticsCenter = new AnalyticsCenter();
        partialUpdatedAnalyticsCenter.setId(analyticsCenter.getId());

        partialUpdatedAnalyticsCenter.countOfBookingPerLocationService(UPDATED_COUNT_OF_BOOKING_PER_LOCATION_SERVICE);

        restAnalyticsCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyticsCenter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyticsCenter))
            )
            .andExpect(status().isOk());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeUpdate);
        AnalyticsCenter testAnalyticsCenter = analyticsCenterList.get(analyticsCenterList.size() - 1);
        assertThat(testAnalyticsCenter.getTotalRevenuePerLProvider()).isEqualTo(DEFAULT_TOTAL_REVENUE_PER_L_PROVIDER);
        assertThat(testAnalyticsCenter.getNumberOfLocations()).isEqualTo(DEFAULT_NUMBER_OF_LOCATIONS);
        assertThat(testAnalyticsCenter.getCountOfBookingPerLocationService()).isEqualTo(UPDATED_COUNT_OF_BOOKING_PER_LOCATION_SERVICE);
        assertThat(testAnalyticsCenter.getTotalRevenuePerLocationService()).isEqualTo(DEFAULT_TOTAL_REVENUE_PER_LOCATION_SERVICE);
    }

    @Test
    @Transactional
    void fullUpdateAnalyticsCenterWithPatch() throws Exception {
        // Initialize the database
        analyticsCenterRepository.saveAndFlush(analyticsCenter);

        int databaseSizeBeforeUpdate = analyticsCenterRepository.findAll().size();

        // Update the analyticsCenter using partial update
        AnalyticsCenter partialUpdatedAnalyticsCenter = new AnalyticsCenter();
        partialUpdatedAnalyticsCenter.setId(analyticsCenter.getId());

        partialUpdatedAnalyticsCenter
            .totalRevenuePerLProvider(UPDATED_TOTAL_REVENUE_PER_L_PROVIDER)
            .numberOfLocations(UPDATED_NUMBER_OF_LOCATIONS)
            .countOfBookingPerLocationService(UPDATED_COUNT_OF_BOOKING_PER_LOCATION_SERVICE)
            .totalRevenuePerLocationService(UPDATED_TOTAL_REVENUE_PER_LOCATION_SERVICE);

        restAnalyticsCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyticsCenter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyticsCenter))
            )
            .andExpect(status().isOk());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeUpdate);
        AnalyticsCenter testAnalyticsCenter = analyticsCenterList.get(analyticsCenterList.size() - 1);
        assertThat(testAnalyticsCenter.getTotalRevenuePerLProvider()).isEqualTo(UPDATED_TOTAL_REVENUE_PER_L_PROVIDER);
        assertThat(testAnalyticsCenter.getNumberOfLocations()).isEqualTo(UPDATED_NUMBER_OF_LOCATIONS);
        assertThat(testAnalyticsCenter.getCountOfBookingPerLocationService()).isEqualTo(UPDATED_COUNT_OF_BOOKING_PER_LOCATION_SERVICE);
        assertThat(testAnalyticsCenter.getTotalRevenuePerLocationService()).isEqualTo(UPDATED_TOTAL_REVENUE_PER_LOCATION_SERVICE);
    }

    @Test
    @Transactional
    void patchNonExistingAnalyticsCenter() throws Exception {
        int databaseSizeBeforeUpdate = analyticsCenterRepository.findAll().size();
        analyticsCenter.setId(count.incrementAndGet());

        // Create the AnalyticsCenter
        AnalyticsCenterDTO analyticsCenterDTO = analyticsCenterMapper.toDto(analyticsCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyticsCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analyticsCenterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyticsCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalyticsCenter() throws Exception {
        int databaseSizeBeforeUpdate = analyticsCenterRepository.findAll().size();
        analyticsCenter.setId(count.incrementAndGet());

        // Create the AnalyticsCenter
        AnalyticsCenterDTO analyticsCenterDTO = analyticsCenterMapper.toDto(analyticsCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyticsCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyticsCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalyticsCenter() throws Exception {
        int databaseSizeBeforeUpdate = analyticsCenterRepository.findAll().size();
        analyticsCenter.setId(count.incrementAndGet());

        // Create the AnalyticsCenter
        AnalyticsCenterDTO analyticsCenterDTO = analyticsCenterMapper.toDto(analyticsCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyticsCenterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyticsCenterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyticsCenter in the database
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalyticsCenter() throws Exception {
        // Initialize the database
        analyticsCenterRepository.saveAndFlush(analyticsCenter);

        int databaseSizeBeforeDelete = analyticsCenterRepository.findAll().size();

        // Delete the analyticsCenter
        restAnalyticsCenterMockMvc
            .perform(delete(ENTITY_API_URL_ID, analyticsCenter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalyticsCenter> analyticsCenterList = analyticsCenterRepository.findAll();
        assertThat(analyticsCenterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
