package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.BannerService;
import com.diviso.graeshoppe.domain.Banner;
import com.diviso.graeshoppe.repository.BannerRepository;
import com.diviso.graeshoppe.service.dto.BannerDTO;
import com.diviso.graeshoppe.service.mapper.BannerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Banner.
 */
@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    private final Logger log = LoggerFactory.getLogger(BannerServiceImpl.class);

    private final BannerRepository bannerRepository;

    private final BannerMapper bannerMapper;

    public BannerServiceImpl(BannerRepository bannerRepository, BannerMapper bannerMapper) {
        this.bannerRepository = bannerRepository;
        this.bannerMapper = bannerMapper;
    }

    /**
     * Save a banner.
     *
     * @param bannerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BannerDTO save(BannerDTO bannerDTO) {
        log.debug("Request to save Banner : {}", bannerDTO);
        Banner banner = bannerMapper.toEntity(bannerDTO);
        banner = bannerRepository.save(banner);
        return bannerMapper.toDto(banner);
    }

    /**
     * Get all the banners.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BannerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Banners");
        return bannerRepository.findAll(pageable)
            .map(bannerMapper::toDto);
    }


    /**
     * Get one banner by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BannerDTO> findOne(Long id) {
        log.debug("Request to get Banner : {}", id);
        return bannerRepository.findById(id)
            .map(bannerMapper::toDto);
    }

    /**
     * Delete the banner by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Banner : {}", id);        bannerRepository.deleteById(id);
    }
}
