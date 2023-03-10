package aou.ahmedbinmadhi.plrs.service;

import aou.ahmedbinmadhi.plrs.domain.Profit;
import aou.ahmedbinmadhi.plrs.repository.ProfitRepository;
import aou.ahmedbinmadhi.plrs.service.dto.ProfitDTO;
import aou.ahmedbinmadhi.plrs.service.mapper.ProfitMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Profit}.
 */
@Service
@Transactional
public class ProfitService {

    private final Logger log = LoggerFactory.getLogger(ProfitService.class);

    private final ProfitRepository profitRepository;

    private final ProfitMapper profitMapper;

    public ProfitService(ProfitRepository profitRepository, ProfitMapper profitMapper) {
        this.profitRepository = profitRepository;
        this.profitMapper = profitMapper;
    }

    /**
     * Save a profit.
     *
     * @param profitDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfitDTO save(ProfitDTO profitDTO) {
        log.debug("Request to save Profit : {}", profitDTO);
        Profit profit = profitMapper.toEntity(profitDTO);
        profit = profitRepository.save(profit);
        return profitMapper.toDto(profit);
    }

    /**
     * Update a profit.
     *
     * @param profitDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfitDTO update(ProfitDTO profitDTO) {
        log.debug("Request to update Profit : {}", profitDTO);
        Profit profit = profitMapper.toEntity(profitDTO);
        profit = profitRepository.save(profit);
        return profitMapper.toDto(profit);
    }

    /**
     * Partially update a profit.
     *
     * @param profitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProfitDTO> partialUpdate(ProfitDTO profitDTO) {
        log.debug("Request to partially update Profit : {}", profitDTO);

        return profitRepository
            .findById(profitDTO.getId())
            .map(existingProfit -> {
                profitMapper.partialUpdate(existingProfit, profitDTO);

                return existingProfit;
            })
            .map(profitRepository::save)
            .map(profitMapper::toDto);
    }

    /**
     * Get all the profits.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProfitDTO> findAll() {
        log.debug("Request to get all Profits");
        return profitRepository.findAll().stream().map(profitMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one profit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfitDTO> findOne(Long id) {
        log.debug("Request to get Profit : {}", id);
        return profitRepository.findById(id).map(profitMapper::toDto);
    }

    /**
     * Delete the profit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profit : {}", id);
        profitRepository.deleteById(id);
    }
}
