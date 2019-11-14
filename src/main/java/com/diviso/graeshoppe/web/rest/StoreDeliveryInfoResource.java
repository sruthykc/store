package com.diviso.graeshoppe.web.rest;
import com.diviso.graeshoppe.service.StoreDeliveryInfoService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.service.dto.StoreDeliveryInfoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StoreDeliveryInfo.
 */
@RestController
@RequestMapping("/api")
public class StoreDeliveryInfoResource {

    private final Logger log = LoggerFactory.getLogger(StoreDeliveryInfoResource.class);

    private static final String ENTITY_NAME = "storeStoreDeliveryInfo";

    private final StoreDeliveryInfoService storeDeliveryInfoService;

    public StoreDeliveryInfoResource(StoreDeliveryInfoService storeDeliveryInfoService) {
        this.storeDeliveryInfoService = storeDeliveryInfoService;
    }

    /**
     * POST  /store-delivery-infos : Create a new storeDeliveryInfo.
     *
     * @param storeDeliveryInfoDTO the storeDeliveryInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeDeliveryInfoDTO, or with status 400 (Bad Request) if the storeDeliveryInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/store-delivery-infos")
    public ResponseEntity<StoreDeliveryInfoDTO> createStoreDeliveryInfo(@RequestBody StoreDeliveryInfoDTO storeDeliveryInfoDTO) throws URISyntaxException {
        log.debug("REST request to save StoreDeliveryInfo : {}", storeDeliveryInfoDTO);
        if (storeDeliveryInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new storeDeliveryInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreDeliveryInfoDTO result = storeDeliveryInfoService.save(storeDeliveryInfoDTO);
        return ResponseEntity.created(new URI("/api/store-delivery-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /store-delivery-infos : Updates an existing storeDeliveryInfo.
     *
     * @param storeDeliveryInfoDTO the storeDeliveryInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeDeliveryInfoDTO,
     * or with status 400 (Bad Request) if the storeDeliveryInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeDeliveryInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/store-delivery-infos")
    public ResponseEntity<StoreDeliveryInfoDTO> updateStoreDeliveryInfo(@RequestBody StoreDeliveryInfoDTO storeDeliveryInfoDTO) throws URISyntaxException {
        log.debug("REST request to update StoreDeliveryInfo : {}", storeDeliveryInfoDTO);
        if (storeDeliveryInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StoreDeliveryInfoDTO result = storeDeliveryInfoService.save(storeDeliveryInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, storeDeliveryInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /store-delivery-infos : get all the storeDeliveryInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of storeDeliveryInfos in body
     */
    @GetMapping("/store-delivery-infos")
    public ResponseEntity<List<StoreDeliveryInfoDTO>> getAllStoreDeliveryInfos(Pageable pageable) {
        log.debug("REST request to get a page of StoreDeliveryInfos");
        Page<StoreDeliveryInfoDTO> page = storeDeliveryInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/store-delivery-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /store-delivery-infos/:id : get the "id" storeDeliveryInfo.
     *
     * @param id the id of the storeDeliveryInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeDeliveryInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/store-delivery-infos/{id}")
    public ResponseEntity<StoreDeliveryInfoDTO> getStoreDeliveryInfo(@PathVariable Long id) {
        log.debug("REST request to get StoreDeliveryInfo : {}", id);
        Optional<StoreDeliveryInfoDTO> storeDeliveryInfoDTO = storeDeliveryInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeDeliveryInfoDTO);
    }

    /**
     * DELETE  /store-delivery-infos/:id : delete the "id" storeDeliveryInfo.
     *
     * @param id the id of the storeDeliveryInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/store-delivery-infos/{id}")
    public ResponseEntity<Void> deleteStoreDeliveryInfo(@PathVariable Long id) {
        log.debug("REST request to delete StoreDeliveryInfo : {}", id);
        storeDeliveryInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
