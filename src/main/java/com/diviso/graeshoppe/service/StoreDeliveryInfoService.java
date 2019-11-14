package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.service.dto.StoreDeliveryInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StoreDeliveryInfo.
 */
public interface StoreDeliveryInfoService {

    /**
     * Save a storeDeliveryInfo.
     *
     * @param storeDeliveryInfoDTO the entity to save
     * @return the persisted entity
     */
    StoreDeliveryInfoDTO save(StoreDeliveryInfoDTO storeDeliveryInfoDTO);

    /**
     * Get all the storeDeliveryInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StoreDeliveryInfoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" storeDeliveryInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StoreDeliveryInfoDTO> findOne(Long id);

    /**
     * Delete the "id" storeDeliveryInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
