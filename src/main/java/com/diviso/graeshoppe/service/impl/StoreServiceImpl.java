package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.StoreService;
import com.diviso.graeshoppe.config.MessageBinderConfiguration;
import com.diviso.graeshoppe.domain.Store;
import com.diviso.graeshoppe.repository.StoreRepository;
import com.diviso.graeshoppe.service.dto.StoreDTO;
import com.diviso.graeshoppe.service.mapper.StoreAvroMapper;
import com.diviso.graeshoppe.service.mapper.StoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.integration.support.MessageBuilder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing Store.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

	private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);
	@Autowired
	private MessageBinderConfiguration messageChannel;

	private final StoreRepository storeRepository;

	private final StoreMapper storeMapper;

	private final StoreAvroMapper storeAvroMapper;

	public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper, StoreAvroMapper storeAvroMapper) {
		this.storeRepository = storeRepository;
		this.storeMapper = storeMapper;
		this.storeAvroMapper = storeAvroMapper;
	}

	/**
	 * Save a store.
	 *
	 * @param storeDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public StoreDTO save(StoreDTO storeDTO) {
		log.debug("Request to save Store : {}", storeDTO);
		Store store = storeMapper.toEntity(storeDTO);
		store = storeRepository.save(store);
		StoreDTO result = storeMapper.toDto(store);

		String status = "create";
		//String zoneId = "Asia/Tokyo";/*result.getOpeningTime().getZone().toString();*/ 
		
	/*	System.out.println("offset second##5555555555555555555555##########" +result.getOpeningTime().getOffset().toString());
		System.out.println("offset second##666666666666666666666666666666##########" +zoneId);*/
		/*result.getOpeningTime().
		 * com.diviso.graeshoppe.store.avro.Store message
		 * =storeAvroMapper.toAvro(result);
		 * System.out.println("avro mapped#############################################"
		 * +message);
		 */
		boolean publishstatus = createPublishMesssage(result, status);

		log.debug("------------------------------------------published" + publishstatus);

		// t.getZone();

		return result;

	}

	public boolean createPublishMesssage(StoreDTO storeDTO, String status) {

		log.debug("------------------------------------------publish method" + status);

		if(storeDTO.getZoneId().equals(null)){
			
			storeDTO.setZoneId("Europe/Doublin");
		}
		
		
		System.out.println("ZONEEEEEEEEEEEEEEEEEEEEEEEEEE"+storeDTO.getZoneId());
		
		com.diviso.graeshoppe.store.avro.Store message = storeAvroMapper.toAvro(storeDTO);
		message.setStatus(status);
		
		System.out.println("avro mapped*******************************************" + message);

		return messageChannel.storeOut().send(MessageBuilder.withPayload(message).build());

	}

	/**
	 * Get all the stores.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<StoreDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Stores");
		return storeRepository.findAll(pageable).map(storeMapper::toDto);
	}

	/**
	 * Get one store by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<StoreDTO> findOne(Long id) {
		log.debug("Request to get Store : {}", id);
		return storeRepository.findById(id).map(storeMapper::toDto);
	}

	/**
	 * Delete the store by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Store : {}", id);
		storeRepository.deleteById(id);
	}
}
