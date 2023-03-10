package aou.ahmedbinmadhi.plrs.web.rest;

import static aou.ahmedbinmadhi.plrs.web.rest.TestUtil.sameInstant;
import static aou.ahmedbinmadhi.plrs.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import aou.ahmedbinmadhi.plrs.IntegrationTest;
import aou.ahmedbinmadhi.plrs.domain.PlrsCommission;
import aou.ahmedbinmadhi.plrs.repository.PlrsCommissionRepository;
import aou.ahmedbinmadhi.plrs.service.dto.PlrsCommissionDTO;
import aou.ahmedbinmadhi.plrs.service.mapper.PlrsCommissionMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link PlrsCommissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlrsCommissionResourceIT {

    private static final BigDecimal DEFAULT_COMMISSION_RATE = new BigDecimal(0);
    private static final BigDecimal UPDATED_COMMISSION_RATE = new BigDecimal(1);

    private static final ZonedDateTime DEFAULT_COMMISSION_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMMISSION_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_COMMISSION_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMMISSION_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/plrs-commissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlrsCommissionRepository plrsCommissionRepository;

    @Autowired
    private PlrsCommissionMapper plrsCommissionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlrsCommissionMockMvc;

    private PlrsCommission plrsCommission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlrsCommission createEntity(EntityManager em) {
        PlrsCommission plrsCommission = new PlrsCommission()
            .commissionRate(DEFAULT_COMMISSION_RATE)
            .commissionStartDate(DEFAULT_COMMISSION_START_DATE)
            .commissionEndDate(DEFAULT_COMMISSION_END_DATE);
        return plrsCommission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlrsCommission createUpdatedEntity(EntityManager em) {
        PlrsCommission plrsCommission = new PlrsCommission()
            .commissionRate(UPDATED_COMMISSION_RATE)
            .commissionStartDate(UPDATED_COMMISSION_START_DATE)
            .commissionEndDate(UPDATED_COMMISSION_END_DATE);
        return plrsCommission;
    }

    @BeforeEach
    public void initTest() {
        plrsCommission = createEntity(em);
    }

    @Test
    @Transactional
    void createPlrsCommission() throws Exception {
        int databaseSizeBeforeCreate = plrsCommissionRepository.findAll().size();
        // Create the PlrsCommission
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(plrsCommission);
        restPlrsCommissionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeCreate + 1);
        PlrsCommission testPlrsCommission = plrsCommissionList.get(plrsCommissionList.size() - 1);
        assertThat(testPlrsCommission.getCommissionRate()).isEqualByComparingTo(DEFAULT_COMMISSION_RATE);
        assertThat(testPlrsCommission.getCommissionStartDate()).isEqualTo(DEFAULT_COMMISSION_START_DATE);
        assertThat(testPlrsCommission.getCommissionEndDate()).isEqualTo(DEFAULT_COMMISSION_END_DATE);
    }

    @Test
    @Transactional
    void createPlrsCommissionWithExistingId() throws Exception {
        // Create the PlrsCommission with an existing ID
        plrsCommission.setId(1L);
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(plrsCommission);

        int databaseSizeBeforeCreate = plrsCommissionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlrsCommissionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCommissionRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = plrsCommissionRepository.findAll().size();
        // set the field null
        plrsCommission.setCommissionRate(null);

        // Create the PlrsCommission, which fails.
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(plrsCommission);

        restPlrsCommissionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isBadRequest());

        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlrsCommissions() throws Exception {
        // Initialize the database
        plrsCommissionRepository.saveAndFlush(plrsCommission);

        // Get all the plrsCommissionList
        restPlrsCommissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plrsCommission.getId().intValue())))
            .andExpect(jsonPath("$.[*].commissionRate").value(hasItem(sameNumber(DEFAULT_COMMISSION_RATE))))
            .andExpect(jsonPath("$.[*].commissionStartDate").value(hasItem(sameInstant(DEFAULT_COMMISSION_START_DATE))))
            .andExpect(jsonPath("$.[*].commissionEndDate").value(hasItem(sameInstant(DEFAULT_COMMISSION_END_DATE))));
    }

    @Test
    @Transactional
    void getPlrsCommission() throws Exception {
        // Initialize the database
        plrsCommissionRepository.saveAndFlush(plrsCommission);

        // Get the plrsCommission
        restPlrsCommissionMockMvc
            .perform(get(ENTITY_API_URL_ID, plrsCommission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plrsCommission.getId().intValue()))
            .andExpect(jsonPath("$.commissionRate").value(sameNumber(DEFAULT_COMMISSION_RATE)))
            .andExpect(jsonPath("$.commissionStartDate").value(sameInstant(DEFAULT_COMMISSION_START_DATE)))
            .andExpect(jsonPath("$.commissionEndDate").value(sameInstant(DEFAULT_COMMISSION_END_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingPlrsCommission() throws Exception {
        // Get the plrsCommission
        restPlrsCommissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlrsCommission() throws Exception {
        // Initialize the database
        plrsCommissionRepository.saveAndFlush(plrsCommission);

        int databaseSizeBeforeUpdate = plrsCommissionRepository.findAll().size();

        // Update the plrsCommission
        PlrsCommission updatedPlrsCommission = plrsCommissionRepository.findById(plrsCommission.getId()).get();
        // Disconnect from session so that the updates on updatedPlrsCommission are not directly saved in db
        em.detach(updatedPlrsCommission);
        updatedPlrsCommission
            .commissionRate(UPDATED_COMMISSION_RATE)
            .commissionStartDate(UPDATED_COMMISSION_START_DATE)
            .commissionEndDate(UPDATED_COMMISSION_END_DATE);
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(updatedPlrsCommission);

        restPlrsCommissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plrsCommissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeUpdate);
        PlrsCommission testPlrsCommission = plrsCommissionList.get(plrsCommissionList.size() - 1);
        assertThat(testPlrsCommission.getCommissionRate()).isEqualByComparingTo(UPDATED_COMMISSION_RATE);
        assertThat(testPlrsCommission.getCommissionStartDate()).isEqualTo(UPDATED_COMMISSION_START_DATE);
        assertThat(testPlrsCommission.getCommissionEndDate()).isEqualTo(UPDATED_COMMISSION_END_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPlrsCommission() throws Exception {
        int databaseSizeBeforeUpdate = plrsCommissionRepository.findAll().size();
        plrsCommission.setId(count.incrementAndGet());

        // Create the PlrsCommission
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(plrsCommission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlrsCommissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plrsCommissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlrsCommission() throws Exception {
        int databaseSizeBeforeUpdate = plrsCommissionRepository.findAll().size();
        plrsCommission.setId(count.incrementAndGet());

        // Create the PlrsCommission
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(plrsCommission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlrsCommissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlrsCommission() throws Exception {
        int databaseSizeBeforeUpdate = plrsCommissionRepository.findAll().size();
        plrsCommission.setId(count.incrementAndGet());

        // Create the PlrsCommission
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(plrsCommission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlrsCommissionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlrsCommissionWithPatch() throws Exception {
        // Initialize the database
        plrsCommissionRepository.saveAndFlush(plrsCommission);

        int databaseSizeBeforeUpdate = plrsCommissionRepository.findAll().size();

        // Update the plrsCommission using partial update
        PlrsCommission partialUpdatedPlrsCommission = new PlrsCommission();
        partialUpdatedPlrsCommission.setId(plrsCommission.getId());

        partialUpdatedPlrsCommission.commissionStartDate(UPDATED_COMMISSION_START_DATE);

        restPlrsCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlrsCommission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlrsCommission))
            )
            .andExpect(status().isOk());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeUpdate);
        PlrsCommission testPlrsCommission = plrsCommissionList.get(plrsCommissionList.size() - 1);
        assertThat(testPlrsCommission.getCommissionRate()).isEqualByComparingTo(DEFAULT_COMMISSION_RATE);
        assertThat(testPlrsCommission.getCommissionStartDate()).isEqualTo(UPDATED_COMMISSION_START_DATE);
        assertThat(testPlrsCommission.getCommissionEndDate()).isEqualTo(DEFAULT_COMMISSION_END_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePlrsCommissionWithPatch() throws Exception {
        // Initialize the database
        plrsCommissionRepository.saveAndFlush(plrsCommission);

        int databaseSizeBeforeUpdate = plrsCommissionRepository.findAll().size();

        // Update the plrsCommission using partial update
        PlrsCommission partialUpdatedPlrsCommission = new PlrsCommission();
        partialUpdatedPlrsCommission.setId(plrsCommission.getId());

        partialUpdatedPlrsCommission
            .commissionRate(UPDATED_COMMISSION_RATE)
            .commissionStartDate(UPDATED_COMMISSION_START_DATE)
            .commissionEndDate(UPDATED_COMMISSION_END_DATE);

        restPlrsCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlrsCommission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlrsCommission))
            )
            .andExpect(status().isOk());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeUpdate);
        PlrsCommission testPlrsCommission = plrsCommissionList.get(plrsCommissionList.size() - 1);
        assertThat(testPlrsCommission.getCommissionRate()).isEqualByComparingTo(UPDATED_COMMISSION_RATE);
        assertThat(testPlrsCommission.getCommissionStartDate()).isEqualTo(UPDATED_COMMISSION_START_DATE);
        assertThat(testPlrsCommission.getCommissionEndDate()).isEqualTo(UPDATED_COMMISSION_END_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPlrsCommission() throws Exception {
        int databaseSizeBeforeUpdate = plrsCommissionRepository.findAll().size();
        plrsCommission.setId(count.incrementAndGet());

        // Create the PlrsCommission
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(plrsCommission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlrsCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plrsCommissionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlrsCommission() throws Exception {
        int databaseSizeBeforeUpdate = plrsCommissionRepository.findAll().size();
        plrsCommission.setId(count.incrementAndGet());

        // Create the PlrsCommission
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(plrsCommission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlrsCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlrsCommission() throws Exception {
        int databaseSizeBeforeUpdate = plrsCommissionRepository.findAll().size();
        plrsCommission.setId(count.incrementAndGet());

        // Create the PlrsCommission
        PlrsCommissionDTO plrsCommissionDTO = plrsCommissionMapper.toDto(plrsCommission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlrsCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plrsCommissionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlrsCommission in the database
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlrsCommission() throws Exception {
        // Initialize the database
        plrsCommissionRepository.saveAndFlush(plrsCommission);

        int databaseSizeBeforeDelete = plrsCommissionRepository.findAll().size();

        // Delete the plrsCommission
        restPlrsCommissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, plrsCommission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlrsCommission> plrsCommissionList = plrsCommissionRepository.findAll();
        assertThat(plrsCommissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
