package com.diviso.graeshoppe.web.rest;
import com.diviso.graeshoppe.service.DeliveryTypeService;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.service.dto.DeliveryTypeDTO;
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
 * REST controller for managing DeliveryType.
 */
@RestController
@RequestMapping("/api")
public class DeliveryTypeResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryTypeResource.class);

    private static final String ENTITY_NAME = "storeDeliveryType";

    private final DeliveryTypeService deliveryTypeService;

    public DeliveryTypeResource(DeliveryTypeService deliveryTypeService) {
        this.deliveryTypeService = deliveryTypeService;
    }

    /**
     * POST  /delivery-types : Create a new deliveryType.
     *
     * @param deliveryTypeDTO the deliveryTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliveryTypeDTO, or with status 400 (Bad Request) if the deliveryType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/delivery-types")
    public ResponseEntity<DeliveryTypeDTO> createDeliveryType(@RequestBody DeliveryTypeDTO deliveryTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DeliveryType : {}", deliveryTypeDTO);
        if (deliveryTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryTypeDTO result = deliveryTypeService.save(deliveryTypeDTO);
        return ResponseEntity.created(new URI("/api/delivery-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /delivery-types : Updates an existing deliveryType.
     *
     * @param deliveryTypeDTO the deliveryTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliveryTypeDTO,
     * or with status 400 (Bad Request) if the deliveryTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the deliveryTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/delivery-types")
    public ResponseEntity<DeliveryTypeDTO> updateDeliveryType(@RequestBody DeliveryTypeDTO deliveryTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DeliveryType : {}", deliveryTypeDTO);
        if (deliveryTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryTypeDTO result = deliveryTypeService.save(deliveryTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliveryTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /delivery-types : get all the deliveryTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deliveryTypes in body
     */
    @GetMapping("/delivery-types")
    public ResponseEntity<List<DeliveryTypeDTO>> getAllDeliveryTypes(Pageable pageable) {
        log.debug("REST request to get a page of DeliveryTypes");
        Page<DeliveryTypeDTO> page = deliveryTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/delivery-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /delivery-types/:id : get the "id" deliveryType.
     *
     * @param id the id of the deliveryTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliveryTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/delivery-types/{id}")
    public ResponseEntity<DeliveryTypeDTO> getDeliveryType(@PathVariable Long id) {
        log.debug("REST request to get DeliveryType : {}", id);
        Optional<DeliveryTypeDTO> deliveryTypeDTO = deliveryTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deliveryTypeDTO);
    }

    /**
     * DELETE  /delivery-types/:id : delete the "id" deliveryType.
     *
     * @param id the id of the deliveryTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delivery-types/{id}")
    public ResponseEntity<Void> deleteDeliveryType(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryType : {}", id);
        deliveryTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
