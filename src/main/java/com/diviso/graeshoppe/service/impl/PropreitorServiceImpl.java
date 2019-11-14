package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.PropreitorService;
import com.diviso.graeshoppe.domain.Propreitor;
import com.diviso.graeshoppe.repository.PropreitorRepository;
import com.diviso.graeshoppe.service.dto.PropreitorDTO;
import com.diviso.graeshoppe.service.mapper.PropreitorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Propreitor.
 */
@Service
@Transactional
public class PropreitorServiceImpl implements PropreitorService {

    private final Logger log = LoggerFactory.getLogger(PropreitorServiceImpl.class);

    private final PropreitorRepository propreitorRepository;

    private final PropreitorMapper propreitorMapper;

    public PropreitorServiceImpl(PropreitorRepository propreitorRepository, PropreitorMapper propreitorMapper) {
        this.propreitorRepository = propreitorRepository;
        this.propreitorMapper = propreitorMapper;
    }

    /**
     * Save a propreitor.
     *
     * @param propreitorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PropreitorDTO save(PropreitorDTO propreitorDTO) {
        log.debug("Request to save Propreitor : {}", propreitorDTO);
        Propreitor propreitor = propreitorMapper.toEntity(propreitorDTO);
        propreitor = propreitorRepository.save(propreitor);
        return propreitorMapper.toDto(propreitor);
    }

    /**
     * Get all the propreitors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PropreitorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Propreitors");
        return propreitorRepository.findAll(pageable)
            .map(propreitorMapper::toDto);
    }


    /**
     * Get one propreitor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PropreitorDTO> findOne(Long id) {
        log.debug("Request to get Propreitor : {}", id);
        return propreitorRepository.findById(id)
            .map(propreitorMapper::toDto);
    }

    /**
     * Delete the propreitor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Propreitor : {}", id);        propreitorRepository.deleteById(id);
    }
}
