package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.StoreAddressService;
import com.diviso.graeshoppe.config.MessageBinderConfiguration;
import com.diviso.graeshoppe.domain.StoreAddress;
import com.diviso.graeshoppe.repository.StoreAddressRepository;
import com.diviso.graeshoppe.service.dto.StoreAddressDTO;
import com.diviso.graeshoppe.service.dto.StoreDTO;
import com.diviso.graeshoppe.service.mapper.StoreAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing StoreAddress.
 */
@Service
@Transactional
public class StoreAddressServiceImpl implements StoreAddressService {
	@Autowired
	private MessageBinderConfiguration messageChannel;
    private final Logger log = LoggerFactory.getLogger(StoreAddressServiceImpl.class);

    private final StoreAddressRepository storeAddressRepository;

    private final StoreAddressMapper storeAddressMapper;

    public StoreAddressServiceImpl(StoreAddressRepository storeAddressRepository, StoreAddressMapper storeAddressMapper) {
        this.storeAddressRepository = storeAddressRepository;
        this.storeAddressMapper = storeAddressMapper;
    }

    /**
     * Save a storeAddress.
     *
     * @param storeAddressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StoreAddressDTO save(StoreAddressDTO storeAddressDTO,String status) {
        log.debug("Request to save StoreAddress : {}", storeAddressDTO);
        StoreAddress storeAddress = storeAddressMapper.toEntity(storeAddressDTO);
        storeAddress = storeAddressRepository.save(storeAddress);
        StoreAddressDTO   result = storeAddressMapper.toDto(storeAddress);
    //    boolean publishstatus = createPublishMesssage(result, status);

		//log.debug("--------------STOREADDRESS----------published" + publishstatus);

		// t.getZone();

		return result;
       
    }

    /**
     * Get all the storeAddresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StoreAddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StoreAddresses");
        return storeAddressRepository.findAll(pageable)
            .map(storeAddressMapper::toDto);
    }


    /**
     * Get one storeAddress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StoreAddressDTO> findOne(Long id) {
        log.debug("Request to get StoreAddress : {}", id);
        return storeAddressRepository.findById(id)
            .map(storeAddressMapper::toDto);
    }

    /**
     * Delete the storeAddress by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id,String status) {
        log.debug("Request to delete StoreAddress : {}", id);        
        storeAddressRepository.deleteById(id);
    }
    
    /*  public boolean createPublishMesssage(StoreDTO storeDTO, String status) {

		/*log.debug(""Request to delete StoreAddress : {}"," +storeDTO);

		if(storeDTO.getZoneId()==null){
			
			storeDTO.setZoneId("Europe/Dublin");
			
		}
		
		
	//	System.out.println("ZONEEEEEEEEEEEEEEEEEEEEEEEEEE"+storeDTO.getZoneId());
		
		com.diviso.graeshoppe.store.avro.Store message = storeAvroMapper.toAvro(storeDTO);
		message.setStatus(status);
		
		System.out.println("avro mapped*******************************************" + message);

		return messageChannel.storeOut().send(MessageBuilder.withPayload(message).build());
    

	}
    
    
    */
    
    
    
}
