package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.StoreSettingsService;
import com.diviso.graeshoppe.domain.StoreSettings;
import com.diviso.graeshoppe.repository.StoreSettingsRepository;
import com.diviso.graeshoppe.service.dto.StoreSettingsDTO;
import com.diviso.graeshoppe.service.mapper.StoreSettingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing StoreSettings.
 */
@Service
@Transactional
public class StoreSettingsServiceImpl implements StoreSettingsService {

    private final Logger log = LoggerFactory.getLogger(StoreSettingsServiceImpl.class);

    private final StoreSettingsRepository storeSettingsRepository;

    private final StoreSettingsMapper storeSettingsMapper;

    public StoreSettingsServiceImpl(StoreSettingsRepository storeSettingsRepository, StoreSettingsMapper storeSettingsMapper) {
        this.storeSettingsRepository = storeSettingsRepository;
        this.storeSettingsMapper = storeSettingsMapper;
    }

    /**
     * Save a storeSettings.
     *
     * @param storeSettingsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StoreSettingsDTO save(StoreSettingsDTO storeSettingsDTO) {
        log.debug("Request to save StoreSettings : {}", storeSettingsDTO);
        StoreSettings storeSettings = storeSettingsMapper.toEntity(storeSettingsDTO);
        storeSettings = storeSettingsRepository.save(storeSettings);
        return storeSettingsMapper.toDto(storeSettings);
    }

    /**
     * Get all the storeSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StoreSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StoreSettings");
        return storeSettingsRepository.findAll(pageable)
            .map(storeSettingsMapper::toDto);
    }


    /**
     * Get one storeSettings by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StoreSettingsDTO> findOne(Long id) {
        log.debug("Request to get StoreSettings : {}", id);
        return storeSettingsRepository.findById(id)
            .map(storeSettingsMapper::toDto);
    }

    /**
     * Delete the storeSettings by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StoreSettings : {}", id);        storeSettingsRepository.deleteById(id);
    }
}
