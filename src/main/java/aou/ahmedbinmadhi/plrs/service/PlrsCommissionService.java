package aou.ahmedbinmadhi.plrs.service;

import aou.ahmedbinmadhi.plrs.domain.PlrsCommission;
import aou.ahmedbinmadhi.plrs.repository.PlrsCommissionRepository;
import aou.ahmedbinmadhi.plrs.service.dto.PlrsCommissionDTO;
import aou.ahmedbinmadhi.plrs.service.mapper.PlrsCommissionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlrsCommission}.
 */
@Service
@Transactional
public class PlrsCommissionService {

    private final Logger log = LoggerFactory.getLogger(PlrsCommissionService.class);

    private final PlrsCommissionRepository plrsCommissionRepository;

    private final PlrsCommissionMapper plrsCommissionMapper;

    public PlrsCommissionService(PlrsCommissionRepository plrsCommissionRepository, PlrsCommissionMapper plrsCommissionMapper) {
        this.plrsCommissionRepository = plrsCommissionRepository;
        this.plrsCommissionMapper = plrsCommissionMapper;
    }

    /**
     * Save a plrsCommission.
     *
     * @param plrsCommissionDTO the entity to save.
     * @return the persisted entity.
     */
    public PlrsCommissionDTO save(PlrsCommissionDTO plrsCommissionDTO) {
        log.debug("Request to save PlrsCommission : {}", plrsCommissionDTO);
        PlrsCommission plrsCommission = plrsCommissionMapper.toEntity(plrsCommissionDTO);
        plrsCommission = plrsCommissionRepository.save(plrsCommission);
        return plrsCommissionMapper.toDto(plrsCommission);
    }

    /**
     * Update a plrsCommission.
     *
     * @param plrsCommissionDTO the entity to save.
     * @return the persisted entity.
     */
    public PlrsCommissionDTO update(PlrsCommissionDTO plrsCommissionDTO) {
        log.debug("Request to update PlrsCommission : {}", plrsCommissionDTO);
        PlrsCommission plrsCommission = plrsCommissionMapper.toEntity(plrsCommissionDTO);
        plrsCommission = plrsCommissionRepository.save(plrsCommission);
        return plrsCommissionMapper.toDto(plrsCommission);
    }

    /**
     * Partially update a plrsCommission.
     *
     * @param plrsCommissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlrsCommissionDTO> partialUpdate(PlrsCommissionDTO plrsCommissionDTO) {
        log.debug("Request to partially update PlrsCommission : {}", plrsCommissionDTO);

        return plrsCommissionRepository
            .findById(plrsCommissionDTO.getId())
            .map(existingPlrsCommission -> {
                plrsCommissionMapper.partialUpdate(existingPlrsCommission, plrsCommissionDTO);

                return existingPlrsCommission;
            })
            .map(plrsCommissionRepository::save)
            .map(plrsCommissionMapper::toDto);
    }

    /**
     * Get all the plrsCommissions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PlrsCommissionDTO> findAll() {
        log.debug("Request to get all PlrsCommissions");
        return plrsCommissionRepository
            .findAll()
            .stream()
            .map(plrsCommissionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one plrsCommission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlrsCommissionDTO> findOne(Long id) {
        log.debug("Request to get PlrsCommission : {}", id);
        return plrsCommissionRepository.findById(id).map(plrsCommissionMapper::toDto);
    }

    /**
     * Delete the plrsCommission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlrsCommission : {}", id);
        plrsCommissionRepository.deleteById(id);
    }
}
