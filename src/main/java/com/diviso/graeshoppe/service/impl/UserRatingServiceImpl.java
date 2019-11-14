package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.UserRatingService;
import com.diviso.graeshoppe.domain.UserRating;
import com.diviso.graeshoppe.repository.UserRatingRepository;
import com.diviso.graeshoppe.service.dto.UserRatingDTO;
import com.diviso.graeshoppe.service.mapper.UserRatingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing UserRating.
 */
@Service
@Transactional
public class UserRatingServiceImpl implements UserRatingService {

    private final Logger log = LoggerFactory.getLogger(UserRatingServiceImpl.class);

    private final UserRatingRepository userRatingRepository;

    private final UserRatingMapper userRatingMapper;

    public UserRatingServiceImpl(UserRatingRepository userRatingRepository, UserRatingMapper userRatingMapper) {
        this.userRatingRepository = userRatingRepository;
        this.userRatingMapper = userRatingMapper;
    }

    /**
     * Save a userRating.
     *
     * @param userRatingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserRatingDTO save(UserRatingDTO userRatingDTO) {
        log.debug("Request to save UserRating : {}", userRatingDTO);
        UserRating userRating = userRatingMapper.toEntity(userRatingDTO);
        userRating = userRatingRepository.save(userRating);
        return userRatingMapper.toDto(userRating);
    }

    /**
     * Get all the userRatings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserRatingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserRatings");
        return userRatingRepository.findAll(pageable)
            .map(userRatingMapper::toDto);
    }


    /**
     * Get one userRating by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserRatingDTO> findOne(Long id) {
        log.debug("Request to get UserRating : {}", id);
        return userRatingRepository.findById(id)
            .map(userRatingMapper::toDto);
    }

    /**
     * Delete the userRating by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserRating : {}", id);        userRatingRepository.deleteById(id);
    }
}
