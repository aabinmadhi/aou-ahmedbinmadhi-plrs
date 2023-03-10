package aou.ahmedbinmadhi.plrs.service;

import aou.ahmedbinmadhi.plrs.domain.TimeSlot;
import aou.ahmedbinmadhi.plrs.repository.TimeSlotRepository;
import aou.ahmedbinmadhi.plrs.service.dto.TimeSlotDTO;
import aou.ahmedbinmadhi.plrs.service.mapper.TimeSlotMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TimeSlot}.
 */
@Service
@Transactional
public class TimeSlotService {

    private final Logger log = LoggerFactory.getLogger(TimeSlotService.class);

    private final TimeSlotRepository timeSlotRepository;

    private final TimeSlotMapper timeSlotMapper;

    public TimeSlotService(TimeSlotRepository timeSlotRepository, TimeSlotMapper timeSlotMapper) {
        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotMapper = timeSlotMapper;
    }

    /**
     * Save a timeSlot.
     *
     * @param timeSlotDTO the entity to save.
     * @return the persisted entity.
     */
    public TimeSlotDTO save(TimeSlotDTO timeSlotDTO) {
        log.debug("Request to save TimeSlot : {}", timeSlotDTO);
        TimeSlot timeSlot = timeSlotMapper.toEntity(timeSlotDTO);
        timeSlot = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toDto(timeSlot);
    }

    /**
     * Update a timeSlot.
     *
     * @param timeSlotDTO the entity to save.
     * @return the persisted entity.
     */
    public TimeSlotDTO update(TimeSlotDTO timeSlotDTO) {
        log.debug("Request to update TimeSlot : {}", timeSlotDTO);
        TimeSlot timeSlot = timeSlotMapper.toEntity(timeSlotDTO);
        timeSlot = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toDto(timeSlot);
    }

    /**
     * Partially update a timeSlot.
     *
     * @param timeSlotDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TimeSlotDTO> partialUpdate(TimeSlotDTO timeSlotDTO) {
        log.debug("Request to partially update TimeSlot : {}", timeSlotDTO);

        return timeSlotRepository
            .findById(timeSlotDTO.getId())
            .map(existingTimeSlot -> {
                timeSlotMapper.partialUpdate(existingTimeSlot, timeSlotDTO);

                return existingTimeSlot;
            })
            .map(timeSlotRepository::save)
            .map(timeSlotMapper::toDto);
    }

    /**
     * Get all the timeSlots.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TimeSlotDTO> findAll() {
        log.debug("Request to get all TimeSlots");
        return timeSlotRepository.findAll().stream().map(timeSlotMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one timeSlot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TimeSlotDTO> findOne(Long id) {
        log.debug("Request to get TimeSlot : {}", id);
        return timeSlotRepository.findById(id).map(timeSlotMapper::toDto);
    }

    /**
     * Delete the timeSlot by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TimeSlot : {}", id);
        timeSlotRepository.deleteById(id);
    }
}
