package com.diviso.graeshoppe.web.rest;
import com.diviso.graeshoppe.service.StoreService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.service.dto.StoreDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Store.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);

    private static final String ENTITY_NAME = "storeStore";

    private final StoreService storeService;

    public StoreResource(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * POST  /stores : Create a new store.
     *
     * @param storeDTO the storeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeDTO, or with status 400 (Bad Request) if the store has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stores")
    public ResponseEntity<StoreDTO> createStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException {
     //   log.debug("REST request to save Store : {}", storeDTO);
    	
    	//StoreDTO storeDTO = new StoreDTO();
    /*	storeDTO.setIdpCode("idpCode");
    	storeDTO.setImageLink("imageLink");
    	storeDTO.setInfo("info");
    	storeDTO.setLatLon("10.833,10.855");
    	storeDTO.setLocationName("locationName");
    	storeDTO.setMinAmount(20.0);
    	storeDTO.setName("name");
    	storeDTO.setTotalRating(20.0);
    	storeDTO.setContactNumber(2l);
    	storeDTO.setStoreUniqueId("storeUniqueId");
    	storeDTO.setEmail("email@lxi.com");
    	*/
    	
    	/*ZoneId zone = ZoneId.of("Asia/Tokyo");
    	ZonedDateTime closingTime = ZonedDateTime.of(2019, 5, 5, 2, 30, 30 ,3, zone);
    	ZonedDateTime openingTime = ZonedDateTime.of(2019, 5, 5, 2, 30, 30 ,3, zone);
    	ZonedDateTime maxDeliveryTime = ZonedDateTime.of(2019, 5, 5, 2, 30, 30 ,3, zone);
    	storeDTO.setClosingTime(closingTime);*/
    	
    	/*storeDTO.setOpeningTime(openingTime);
    	storeDTO.setMaxDeliveryTime(maxDeliveryTime);*/
    	log.debug("REST request to save Store : {}", storeDTO);
    	if (storeDTO.getId() != null) {
            throw new BadRequestAlertException("A new store cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreDTO result = storeService.save(storeDTO);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stores : Updates an existing store.
     *
     * @param storeDTO the storeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeDTO,
     * or with status 400 (Bad Request) if the storeDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stores")
    public ResponseEntity<StoreDTO> updateStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to update Store : {}", storeDTO);
        if (storeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StoreDTO result = storeService.save(storeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, storeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stores : get all the stores.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stores in body
     */
    @GetMapping("/stores")
    public ResponseEntity<List<StoreDTO>> getAllStores(Pageable pageable) {
        log.debug("REST request to get a page of Stores");
        Page<StoreDTO> page = storeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stores");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /stores/:id : get the "id" store.
     *
     * @param id the id of the storeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
        log.debug("REST request to get Store : {}", id);
        Optional<StoreDTO> storeDTO = storeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeDTO);
    }

    /**
     * DELETE  /stores/:id : delete the "id" store.
     *
     * @param id the id of the storeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete Store : {}", id);
        storeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
