package aou.ahmedbinmadhi.plrs.service;

import aou.ahmedbinmadhi.plrs.domain.AnalyticsCenter;
import aou.ahmedbinmadhi.plrs.repository.AnalyticsCenterRepository;
import aou.ahmedbinmadhi.plrs.service.dto.AnalyticsCenterDTO;
import aou.ahmedbinmadhi.plrs.service.mapper.AnalyticsCenterMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnalyticsCenter}.
 */
@Service
@Transactional
public class AnalyticsCenterService {

    private final Logger log = LoggerFactory.getLogger(AnalyticsCenterService.class);

    private final AnalyticsCenterRepository analyticsCenterRepository;

    private final AnalyticsCenterMapper analyticsCenterMapper;

    public AnalyticsCenterService(AnalyticsCenterRepository analyticsCenterRepository, AnalyticsCenterMapper analyticsCenterMapper) {
        this.analyticsCenterRepository = analyticsCenterRepository;
        this.analyticsCenterMapper = analyticsCenterMapper;
    }

    /**
     * Save a analyticsCenter.
     *
     * @param analyticsCenterDTO the entity to save.
     * @return the persisted entity.
     */
    public AnalyticsCenterDTO save(AnalyticsCenterDTO analyticsCenterDTO) {
        log.debug("Request to save AnalyticsCenter : {}", analyticsCenterDTO);
        AnalyticsCenter analyticsCenter = analyticsCenterMapper.toEntity(analyticsCenterDTO);
        analyticsCenter = analyticsCenterRepository.save(analyticsCenter);
        return analyticsCenterMapper.toDto(analyticsCenter);
    }

    /**
     * Update a analyticsCenter.
     *
     * @param analyticsCenterDTO the entity to save.
     * @return the persisted entity.
     */
    public AnalyticsCenterDTO update(AnalyticsCenterDTO analyticsCenterDTO) {
        log.debug("Request to update AnalyticsCenter : {}", analyticsCenterDTO);
        AnalyticsCenter analyticsCenter = analyticsCenterMapper.toEntity(analyticsCenterDTO);
        analyticsCenter = analyticsCenterRepository.save(analyticsCenter);
        return analyticsCenterMapper.toDto(analyticsCenter);
    }

    /**
     * Partially update a analyticsCenter.
     *
     * @param analyticsCenterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnalyticsCenterDTO> partialUpdate(AnalyticsCenterDTO analyticsCenterDTO) {
        log.debug("Request to partially update AnalyticsCenter : {}", analyticsCenterDTO);

        return analyticsCenterRepository
            .findById(analyticsCenterDTO.getId())
            .map(existingAnalyticsCenter -> {
                analyticsCenterMapper.partialUpdate(existingAnalyticsCenter, analyticsCenterDTO);

                return existingAnalyticsCenter;
            })
            .map(analyticsCenterRepository::save)
            .map(analyticsCenterMapper::toDto);
    }

    /**
     * Get all the analyticsCenters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnalyticsCenterDTO> findAll() {
        log.debug("Request to get all AnalyticsCenters");
        return analyticsCenterRepository
            .findAll()
            .stream()
            .map(analyticsCenterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one analyticsCenter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnalyticsCenterDTO> findOne(Long id) {
        log.debug("Request to get AnalyticsCenter : {}", id);
        return analyticsCenterRepository.findById(id).map(analyticsCenterMapper::toDto);
    }

    /**
     * Delete the analyticsCenter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnalyticsCenter : {}", id);
        analyticsCenterRepository.deleteById(id);
    }
}
