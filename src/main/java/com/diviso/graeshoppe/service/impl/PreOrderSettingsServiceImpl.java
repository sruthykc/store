package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.PreOrderSettingsService;
import com.diviso.graeshoppe.domain.PreOrderSettings;
import com.diviso.graeshoppe.repository.PreOrderSettingsRepository;
import com.diviso.graeshoppe.service.dto.PreOrderSettingsDTO;
import com.diviso.graeshoppe.service.mapper.PreOrderSettingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PreOrderSettings.
 */
@Service
@Transactional
public class PreOrderSettingsServiceImpl implements PreOrderSettingsService {

    private final Logger log = LoggerFactory.getLogger(PreOrderSettingsServiceImpl.class);

    private final PreOrderSettingsRepository preOrderSettingsRepository;

    private final PreOrderSettingsMapper preOrderSettingsMapper;

    public PreOrderSettingsServiceImpl(PreOrderSettingsRepository preOrderSettingsRepository, PreOrderSettingsMapper preOrderSettingsMapper) {
        this.preOrderSettingsRepository = preOrderSettingsRepository;
        this.preOrderSettingsMapper = preOrderSettingsMapper;
    }

    /**
     * Save a preOrderSettings.
     *
     * @param preOrderSettingsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PreOrderSettingsDTO save(PreOrderSettingsDTO preOrderSettingsDTO) {
        log.debug("Request to save PreOrderSettings : {}", preOrderSettingsDTO);
        PreOrderSettings preOrderSettings = preOrderSettingsMapper.toEntity(preOrderSettingsDTO);
        preOrderSettings = preOrderSettingsRepository.save(preOrderSettings);
        return preOrderSettingsMapper.toDto(preOrderSettings);
    }

    /**
     * Get all the preOrderSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PreOrderSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PreOrderSettings");
        return preOrderSettingsRepository.findAll(pageable)
            .map(preOrderSettingsMapper::toDto);
    }


    /**
     * Get one preOrderSettings by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PreOrderSettingsDTO> findOne(Long id) {
        log.debug("Request to get PreOrderSettings : {}", id);
        return preOrderSettingsRepository.findById(id)
            .map(preOrderSettingsMapper::toDto);
    }

    /**
     * Delete the preOrderSettings by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PreOrderSettings : {}", id);        preOrderSettingsRepository.deleteById(id);
    }
}
