package aou.ahmedbinmadhi.plrs.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import aou.ahmedbinmadhi.plrs.IntegrationTest;
import aou.ahmedbinmadhi.plrs.domain.LocationService;
import aou.ahmedbinmadhi.plrs.domain.enumeration.City;
import aou.ahmedbinmadhi.plrs.repository.LocationServiceRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link LocationServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationServiceResourceIT {

    private static final String DEFAULT_LOCATION_SERVICE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_SERVICE_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOCATION_SERVICE_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOCATION_SERVICE_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOCATION_SERVICE_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOCATION_SERVICE_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MAP_COORDINATES = "AAAAAAAAAA";
    private static final String UPDATED_MAP_COORDINATES = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_DISCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final Double DEFAULT_HOURLY_RATE = 1D;
    private static final Double UPDATED_HOURLY_RATE = 2D;

    private static final Boolean DEFAULT_WHITE_BOARD = false;
    private static final Boolean UPDATED_WHITE_BOARD = true;

    private static final Boolean DEFAULT_COFFEE = false;
    private static final Boolean UPDATED_COFFEE = true;

    private static final Boolean DEFAULT_WI_FI = false;
    private static final Boolean UPDATED_WI_FI = true;

    private static final Boolean DEFAULT_MONITOR = false;
    private static final Boolean UPDATED_MONITOR = true;

    private static final Boolean DEFAULT_PC_OR_LAPTOP = false;
    private static final Boolean UPDATED_PC_OR_LAPTOP = true;

    private static final Boolean DEFAULT_PRINTER = false;
    private static final Boolean UPDATED_PRINTER = true;

    private static final City DEFAULT_CITY = City.RIYADH;
    private static final City UPDATED_CITY = City.JEDDAH;

    private static final String ENTITY_API_URL = "/api/location-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocationServiceRepository locationServiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationServiceMockMvc;

    private LocationService locationService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationService createEntity(EntityManager em) {
        LocationService locationService = new LocationService()
            .locationServiceTitle(DEFAULT_LOCATION_SERVICE_TITLE)
            .locationServiceImage(DEFAULT_LOCATION_SERVICE_IMAGE)
            .locationServiceImageContentType(DEFAULT_LOCATION_SERVICE_IMAGE_CONTENT_TYPE)
            .mapCoordinates(DEFAULT_MAP_COORDINATES)
            .locationDiscription(DEFAULT_LOCATION_DISCRIPTION)
            .capacity(DEFAULT_CAPACITY)
            .hourlyRate(DEFAULT_HOURLY_RATE)
            .whiteBoard(DEFAULT_WHITE_BOARD)
            .coffee(DEFAULT_COFFEE)
            .wiFi(DEFAULT_WI_FI)
            .monitor(DEFAULT_MONITOR)
            .pcOrLaptop(DEFAULT_PC_OR_LAPTOP)
            .printer(DEFAULT_PRINTER)
            .city(DEFAULT_CITY);
        return locationService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationService createUpdatedEntity(EntityManager em) {
        LocationService locationService = new LocationService()
            .locationServiceTitle(UPDATED_LOCATION_SERVICE_TITLE)
            .locationServiceImage(UPDATED_LOCATION_SERVICE_IMAGE)
            .locationServiceImageContentType(UPDATED_LOCATION_SERVICE_IMAGE_CONTENT_TYPE)
            .mapCoordinates(UPDATED_MAP_COORDINATES)
            .locationDiscription(UPDATED_LOCATION_DISCRIPTION)
            .capacity(UPDATED_CAPACITY)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .whiteBoard(UPDATED_WHITE_BOARD)
            .coffee(UPDATED_COFFEE)
            .wiFi(UPDATED_WI_FI)
            .monitor(UPDATED_MONITOR)
            .pcOrLaptop(UPDATED_PC_OR_LAPTOP)
            .printer(UPDATED_PRINTER)
            .city(UPDATED_CITY);
        return locationService;
    }

    @BeforeEach
    public void initTest() {
        locationService = createEntity(em);
    }

    @Test
    @Transactional
    void createLocationService() throws Exception {
        int databaseSizeBeforeCreate = locationServiceRepository.findAll().size();
        // Create the LocationService
        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isCreated());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeCreate + 1);
        LocationService testLocationService = locationServiceList.get(locationServiceList.size() - 1);
        assertThat(testLocationService.getLocationServiceTitle()).isEqualTo(DEFAULT_LOCATION_SERVICE_TITLE);
        assertThat(testLocationService.getLocationServiceImage()).isEqualTo(DEFAULT_LOCATION_SERVICE_IMAGE);
        assertThat(testLocationService.getLocationServiceImageContentType()).isEqualTo(DEFAULT_LOCATION_SERVICE_IMAGE_CONTENT_TYPE);
        assertThat(testLocationService.getMapCoordinates()).isEqualTo(DEFAULT_MAP_COORDINATES);
        assertThat(testLocationService.getLocationDiscription()).isEqualTo(DEFAULT_LOCATION_DISCRIPTION);
        assertThat(testLocationService.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testLocationService.getHourlyRate()).isEqualTo(DEFAULT_HOURLY_RATE);
        assertThat(testLocationService.getWhiteBoard()).isEqualTo(DEFAULT_WHITE_BOARD);
        assertThat(testLocationService.getCoffee()).isEqualTo(DEFAULT_COFFEE);
        assertThat(testLocationService.getWiFi()).isEqualTo(DEFAULT_WI_FI);
        assertThat(testLocationService.getMonitor()).isEqualTo(DEFAULT_MONITOR);
        assertThat(testLocationService.getPcOrLaptop()).isEqualTo(DEFAULT_PC_OR_LAPTOP);
        assertThat(testLocationService.getPrinter()).isEqualTo(DEFAULT_PRINTER);
        assertThat(testLocationService.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    void createLocationServiceWithExistingId() throws Exception {
        // Create the LocationService with an existing ID
        locationService.setId(1L);

        int databaseSizeBeforeCreate = locationServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLocationServiceTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setLocationServiceTitle(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setCapacity(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHourlyRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setHourlyRate(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWhiteBoardIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setWhiteBoard(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoffeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setCoffee(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWiFiIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setWiFi(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMonitorIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setMonitor(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPcOrLaptopIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setPcOrLaptop(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrinterIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setPrinter(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationServiceRepository.findAll().size();
        // set the field null
        locationService.setCity(null);

        // Create the LocationService, which fails.

        restLocationServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLocationServices() throws Exception {
        // Initialize the database
        locationServiceRepository.saveAndFlush(locationService);

        // Get all the locationServiceList
        restLocationServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationService.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationServiceTitle").value(hasItem(DEFAULT_LOCATION_SERVICE_TITLE)))
            .andExpect(jsonPath("$.[*].locationServiceImageContentType").value(hasItem(DEFAULT_LOCATION_SERVICE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].locationServiceImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOCATION_SERVICE_IMAGE))))
            .andExpect(jsonPath("$.[*].mapCoordinates").value(hasItem(DEFAULT_MAP_COORDINATES)))
            .andExpect(jsonPath("$.[*].locationDiscription").value(hasItem(DEFAULT_LOCATION_DISCRIPTION)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].whiteBoard").value(hasItem(DEFAULT_WHITE_BOARD.booleanValue())))
            .andExpect(jsonPath("$.[*].coffee").value(hasItem(DEFAULT_COFFEE.booleanValue())))
            .andExpect(jsonPath("$.[*].wiFi").value(hasItem(DEFAULT_WI_FI.booleanValue())))
            .andExpect(jsonPath("$.[*].monitor").value(hasItem(DEFAULT_MONITOR.booleanValue())))
            .andExpect(jsonPath("$.[*].pcOrLaptop").value(hasItem(DEFAULT_PC_OR_LAPTOP.booleanValue())))
            .andExpect(jsonPath("$.[*].printer").value(hasItem(DEFAULT_PRINTER.booleanValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())));
    }

    @Test
    @Transactional
    void getLocationService() throws Exception {
        // Initialize the database
        locationServiceRepository.saveAndFlush(locationService);

        // Get the locationService
        restLocationServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, locationService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locationService.getId().intValue()))
            .andExpect(jsonPath("$.locationServiceTitle").value(DEFAULT_LOCATION_SERVICE_TITLE))
            .andExpect(jsonPath("$.locationServiceImageContentType").value(DEFAULT_LOCATION_SERVICE_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.locationServiceImage").value(Base64Utils.encodeToString(DEFAULT_LOCATION_SERVICE_IMAGE)))
            .andExpect(jsonPath("$.mapCoordinates").value(DEFAULT_MAP_COORDINATES))
            .andExpect(jsonPath("$.locationDiscription").value(DEFAULT_LOCATION_DISCRIPTION))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.hourlyRate").value(DEFAULT_HOURLY_RATE.doubleValue()))
            .andExpect(jsonPath("$.whiteBoard").value(DEFAULT_WHITE_BOARD.booleanValue()))
            .andExpect(jsonPath("$.coffee").value(DEFAULT_COFFEE.booleanValue()))
            .andExpect(jsonPath("$.wiFi").value(DEFAULT_WI_FI.booleanValue()))
            .andExpect(jsonPath("$.monitor").value(DEFAULT_MONITOR.booleanValue()))
            .andExpect(jsonPath("$.pcOrLaptop").value(DEFAULT_PC_OR_LAPTOP.booleanValue()))
            .andExpect(jsonPath("$.printer").value(DEFAULT_PRINTER.booleanValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLocationService() throws Exception {
        // Get the locationService
        restLocationServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocationService() throws Exception {
        // Initialize the database
        locationServiceRepository.saveAndFlush(locationService);

        int databaseSizeBeforeUpdate = locationServiceRepository.findAll().size();

        // Update the locationService
        LocationService updatedLocationService = locationServiceRepository.findById(locationService.getId()).get();
        // Disconnect from session so that the updates on updatedLocationService are not directly saved in db
        em.detach(updatedLocationService);
        updatedLocationService
            .locationServiceTitle(UPDATED_LOCATION_SERVICE_TITLE)
            .locationServiceImage(UPDATED_LOCATION_SERVICE_IMAGE)
            .locationServiceImageContentType(UPDATED_LOCATION_SERVICE_IMAGE_CONTENT_TYPE)
            .mapCoordinates(UPDATED_MAP_COORDINATES)
            .locationDiscription(UPDATED_LOCATION_DISCRIPTION)
            .capacity(UPDATED_CAPACITY)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .whiteBoard(UPDATED_WHITE_BOARD)
            .coffee(UPDATED_COFFEE)
            .wiFi(UPDATED_WI_FI)
            .monitor(UPDATED_MONITOR)
            .pcOrLaptop(UPDATED_PC_OR_LAPTOP)
            .printer(UPDATED_PRINTER)
            .city(UPDATED_CITY);

        restLocationServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocationService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLocationService))
            )
            .andExpect(status().isOk());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeUpdate);
        LocationService testLocationService = locationServiceList.get(locationServiceList.size() - 1);
        assertThat(testLocationService.getLocationServiceTitle()).isEqualTo(UPDATED_LOCATION_SERVICE_TITLE);
        assertThat(testLocationService.getLocationServiceImage()).isEqualTo(UPDATED_LOCATION_SERVICE_IMAGE);
        assertThat(testLocationService.getLocationServiceImageContentType()).isEqualTo(UPDATED_LOCATION_SERVICE_IMAGE_CONTENT_TYPE);
        assertThat(testLocationService.getMapCoordinates()).isEqualTo(UPDATED_MAP_COORDINATES);
        assertThat(testLocationService.getLocationDiscription()).isEqualTo(UPDATED_LOCATION_DISCRIPTION);
        assertThat(testLocationService.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testLocationService.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testLocationService.getWhiteBoard()).isEqualTo(UPDATED_WHITE_BOARD);
        assertThat(testLocationService.getCoffee()).isEqualTo(UPDATED_COFFEE);
        assertThat(testLocationService.getWiFi()).isEqualTo(UPDATED_WI_FI);
        assertThat(testLocationService.getMonitor()).isEqualTo(UPDATED_MONITOR);
        assertThat(testLocationService.getPcOrLaptop()).isEqualTo(UPDATED_PC_OR_LAPTOP);
        assertThat(testLocationService.getPrinter()).isEqualTo(UPDATED_PRINTER);
        assertThat(testLocationService.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    void putNonExistingLocationService() throws Exception {
        int databaseSizeBeforeUpdate = locationServiceRepository.findAll().size();
        locationService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationService.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocationService() throws Exception {
        int databaseSizeBeforeUpdate = locationServiceRepository.findAll().size();
        locationService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocationService() throws Exception {
        int databaseSizeBeforeUpdate = locationServiceRepository.findAll().size();
        locationService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationServiceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationServiceWithPatch() throws Exception {
        // Initialize the database
        locationServiceRepository.saveAndFlush(locationService);

        int databaseSizeBeforeUpdate = locationServiceRepository.findAll().size();

        // Update the locationService using partial update
        LocationService partialUpdatedLocationService = new LocationService();
        partialUpdatedLocationService.setId(locationService.getId());

        partialUpdatedLocationService
            .locationServiceTitle(UPDATED_LOCATION_SERVICE_TITLE)
            .locationServiceImage(UPDATED_LOCATION_SERVICE_IMAGE)
            .locationServiceImageContentType(UPDATED_LOCATION_SERVICE_IMAGE_CONTENT_TYPE)
            .mapCoordinates(UPDATED_MAP_COORDINATES)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .printer(UPDATED_PRINTER)
            .city(UPDATED_CITY);

        restLocationServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocationService))
            )
            .andExpect(status().isOk());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeUpdate);
        LocationService testLocationService = locationServiceList.get(locationServiceList.size() - 1);
        assertThat(testLocationService.getLocationServiceTitle()).isEqualTo(UPDATED_LOCATION_SERVICE_TITLE);
        assertThat(testLocationService.getLocationServiceImage()).isEqualTo(UPDATED_LOCATION_SERVICE_IMAGE);
        assertThat(testLocationService.getLocationServiceImageContentType()).isEqualTo(UPDATED_LOCATION_SERVICE_IMAGE_CONTENT_TYPE);
        assertThat(testLocationService.getMapCoordinates()).isEqualTo(UPDATED_MAP_COORDINATES);
        assertThat(testLocationService.getLocationDiscription()).isEqualTo(DEFAULT_LOCATION_DISCRIPTION);
        assertThat(testLocationService.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testLocationService.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testLocationService.getWhiteBoard()).isEqualTo(DEFAULT_WHITE_BOARD);
        assertThat(testLocationService.getCoffee()).isEqualTo(DEFAULT_COFFEE);
        assertThat(testLocationService.getWiFi()).isEqualTo(DEFAULT_WI_FI);
        assertThat(testLocationService.getMonitor()).isEqualTo(DEFAULT_MONITOR);
        assertThat(testLocationService.getPcOrLaptop()).isEqualTo(DEFAULT_PC_OR_LAPTOP);
        assertThat(testLocationService.getPrinter()).isEqualTo(UPDATED_PRINTER);
        assertThat(testLocationService.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    void fullUpdateLocationServiceWithPatch() throws Exception {
        // Initialize the database
        locationServiceRepository.saveAndFlush(locationService);

        int databaseSizeBeforeUpdate = locationServiceRepository.findAll().size();

        // Update the locationService using partial update
        LocationService partialUpdatedLocationService = new LocationService();
        partialUpdatedLocationService.setId(locationService.getId());

        partialUpdatedLocationService
            .locationServiceTitle(UPDATED_LOCATION_SERVICE_TITLE)
            .locationServiceImage(UPDATED_LOCATION_SERVICE_IMAGE)
            .locationServiceImageContentType(UPDATED_LOCATION_SERVICE_IMAGE_CONTENT_TYPE)
            .mapCoordinates(UPDATED_MAP_COORDINATES)
            .locationDiscription(UPDATED_LOCATION_DISCRIPTION)
            .capacity(UPDATED_CAPACITY)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .whiteBoard(UPDATED_WHITE_BOARD)
            .coffee(UPDATED_COFFEE)
            .wiFi(UPDATED_WI_FI)
            .monitor(UPDATED_MONITOR)
            .pcOrLaptop(UPDATED_PC_OR_LAPTOP)
            .printer(UPDATED_PRINTER)
            .city(UPDATED_CITY);

        restLocationServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocationService))
            )
            .andExpect(status().isOk());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeUpdate);
        LocationService testLocationService = locationServiceList.get(locationServiceList.size() - 1);
        assertThat(testLocationService.getLocationServiceTitle()).isEqualTo(UPDATED_LOCATION_SERVICE_TITLE);
        assertThat(testLocationService.getLocationServiceImage()).isEqualTo(UPDATED_LOCATION_SERVICE_IMAGE);
        assertThat(testLocationService.getLocationServiceImageContentType()).isEqualTo(UPDATED_LOCATION_SERVICE_IMAGE_CONTENT_TYPE);
        assertThat(testLocationService.getMapCoordinates()).isEqualTo(UPDATED_MAP_COORDINATES);
        assertThat(testLocationService.getLocationDiscription()).isEqualTo(UPDATED_LOCATION_DISCRIPTION);
        assertThat(testLocationService.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testLocationService.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testLocationService.getWhiteBoard()).isEqualTo(UPDATED_WHITE_BOARD);
        assertThat(testLocationService.getCoffee()).isEqualTo(UPDATED_COFFEE);
        assertThat(testLocationService.getWiFi()).isEqualTo(UPDATED_WI_FI);
        assertThat(testLocationService.getMonitor()).isEqualTo(UPDATED_MONITOR);
        assertThat(testLocationService.getPcOrLaptop()).isEqualTo(UPDATED_PC_OR_LAPTOP);
        assertThat(testLocationService.getPrinter()).isEqualTo(UPDATED_PRINTER);
        assertThat(testLocationService.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    void patchNonExistingLocationService() throws Exception {
        int databaseSizeBeforeUpdate = locationServiceRepository.findAll().size();
        locationService.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocationService() throws Exception {
        int databaseSizeBeforeUpdate = locationServiceRepository.findAll().size();
        locationService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocationService() throws Exception {
        int databaseSizeBeforeUpdate = locationServiceRepository.findAll().size();
        locationService.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationServiceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationService))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationService in the database
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocationService() throws Exception {
        // Initialize the database
        locationServiceRepository.saveAndFlush(locationService);

        int databaseSizeBeforeDelete = locationServiceRepository.findAll().size();

        // Delete the locationService
        restLocationServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, locationService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocationService> locationServiceList = locationServiceRepository.findAll();
        assertThat(locationServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
