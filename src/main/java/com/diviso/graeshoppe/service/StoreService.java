package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.StoreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Store.
 */
public interface StoreService {

    /**
     * Save a store.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    StoreDTO save(StoreDTO storeDTO,String status);

    /**
     * Get all the stores.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StoreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" store.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StoreDTO> findOne(Long id);

    /**
     * Delete the "id" store.
     *
     * @param id the id of the entity
     */
    void delete(Long id,String status);
}
