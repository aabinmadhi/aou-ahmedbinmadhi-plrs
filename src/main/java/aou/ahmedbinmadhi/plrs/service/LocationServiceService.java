package aou.ahmedbinmadhi.plrs.service;

import aou.ahmedbinmadhi.plrs.domain.LocationService;
import aou.ahmedbinmadhi.plrs.repository.LocationServiceRepository;
import aou.ahmedbinmadhi.plrs.service.dto.LocationServiceDTO;
import aou.ahmedbinmadhi.plrs.service.mapper.LocationServiceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LocationService}.
 */
@Service
@Transactional
public class LocationServiceService {

    private final Logger log = LoggerFactory.getLogger(LocationServiceService.class);

    private final LocationServiceRepository locationServiceRepository;

    private final LocationServiceMapper locationServiceMapper;

    public LocationServiceService(LocationServiceRepository locationServiceRepository, LocationServiceMapper locationServiceMapper) {
        this.locationServiceRepository = locationServiceRepository;
        this.locationServiceMapper = locationServiceMapper;
    }

    /**
     * Save a locationService.
     *
     * @param locationServiceDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationServiceDTO save(LocationServiceDTO locationServiceDTO) {
        log.debug("Request to save LocationService : {}", locationServiceDTO);
        LocationService locationService = locationServiceMapper.toEntity(locationServiceDTO);
        locationService = locationServiceRepository.save(locationService);
        return locationServiceMapper.toDto(locationService);
    }

    /**
     * Update a locationService.
     *
     * @param locationServiceDTO the entity to save.
     * @return the persisted entity.
     */
    public LocationServiceDTO update(LocationServiceDTO locationServiceDTO) {
        log.debug("Request to update LocationService : {}", locationServiceDTO);
        LocationService locationService = locationServiceMapper.toEntity(locationServiceDTO);
        locationService = locationServiceRepository.save(locationService);
        return locationServiceMapper.toDto(locationService);
    }

    /**
     * Partially update a locationService.
     *
     * @param locationServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LocationServiceDTO> partialUpdate(LocationServiceDTO locationServiceDTO) {
        log.debug("Request to partially update LocationService : {}", locationServiceDTO);

        return locationServiceRepository
            .findById(locationServiceDTO.getId())
            .map(existingLocationService -> {
                locationServiceMapper.partialUpdate(existingLocationService, locationServiceDTO);

                return existingLocationService;
            })
            .map(locationServiceRepository::save)
            .map(locationServiceMapper::toDto);
    }

    /**
     * Get all the locationServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocationServices");
        return locationServiceRepository.findAll(pageable).map(locationServiceMapper::toDto);
    }

    /**
     * Get one locationService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationServiceDTO> findOne(Long id) {
        log.debug("Request to get LocationService : {}", id);
        return locationServiceRepository.findById(id).map(locationServiceMapper::toDto);
    }

    /**
     * Delete the locationService by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocationService : {}", id);
        locationServiceRepository.deleteById(id);
    }
}
