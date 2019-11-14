package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.PropreitorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Propreitor.
 */
public interface PropreitorService {

    /**
     * Save a propreitor.
     *
     * @param propreitorDTO the entity to save
     * @return the persisted entity
     */
    PropreitorDTO save(PropreitorDTO propreitorDTO);

    /**
     * Get all the propreitors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PropreitorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" propreitor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PropreitorDTO> findOne(Long id);

    /**
     * Delete the "id" propreitor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
