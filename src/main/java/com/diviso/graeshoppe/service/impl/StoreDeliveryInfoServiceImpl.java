package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.StoreDeliveryInfoService;
import com.diviso.graeshoppe.domain.StoreDeliveryInfo;
import com.diviso.graeshoppe.repository.StoreDeliveryInfoRepository;
import com.diviso.graeshoppe.service.dto.StoreDeliveryInfoDTO;
import com.diviso.graeshoppe.service.mapper.StoreDeliveryInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing StoreDeliveryInfo.
 */
@Service
@Transactional
public class StoreDeliveryInfoServiceImpl implements StoreDeliveryInfoService {

    private final Logger log = LoggerFactory.getLogger(StoreDeliveryInfoServiceImpl.class);

    private final StoreDeliveryInfoRepository storeDeliveryInfoRepository;

    private final StoreDeliveryInfoMapper storeDeliveryInfoMapper;

    public StoreDeliveryInfoServiceImpl(StoreDeliveryInfoRepository storeDeliveryInfoRepository, StoreDeliveryInfoMapper storeDeliveryInfoMapper) {
        this.storeDeliveryInfoRepository = storeDeliveryInfoRepository;
        this.storeDeliveryInfoMapper = storeDeliveryInfoMapper;
    }

    /**
     * Save a storeDeliveryInfo.
     *
     * @param storeDeliveryInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StoreDeliveryInfoDTO save(StoreDeliveryInfoDTO storeDeliveryInfoDTO) {
        log.debug("Request to save StoreDeliveryInfo : {}", storeDeliveryInfoDTO);
        StoreDeliveryInfo storeDeliveryInfo = storeDeliveryInfoMapper.toEntity(storeDeliveryInfoDTO);
        storeDeliveryInfo = storeDeliveryInfoRepository.save(storeDeliveryInfo);
        return storeDeliveryInfoMapper.toDto(storeDeliveryInfo);
    }

    /**
     * Get all the storeDeliveryInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StoreDeliveryInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StoreDeliveryInfos");
        return storeDeliveryInfoRepository.findAll(pageable)
            .map(storeDeliveryInfoMapper::toDto);
    }


    /**
     * Get one storeDeliveryInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StoreDeliveryInfoDTO> findOne(Long id) {
        log.debug("Request to get StoreDeliveryInfo : {}", id);
        return storeDeliveryInfoRepository.findById(id)
            .map(storeDeliveryInfoMapper::toDto);
    }

    /**
     * Delete the storeDeliveryInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StoreDeliveryInfo : {}", id);        storeDeliveryInfoRepository.deleteById(id);
    }
}
