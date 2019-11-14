package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.StoreApp;

import com.diviso.graeshoppe.domain.DeliveryType;
import com.diviso.graeshoppe.repository.DeliveryTypeRepository;
import com.diviso.graeshoppe.service.DeliveryTypeService;
import com.diviso.graeshoppe.service.dto.DeliveryTypeDTO;
import com.diviso.graeshoppe.service.mapper.DeliveryTypeMapper;
import com.diviso.graeshoppe.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.diviso.graeshoppe.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeliveryTypeResource REST controller.
 *
 * @see DeliveryTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreApp.class)
public class DeliveryTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DeliveryTypeRepository deliveryTypeRepository;

    @Autowired
    private DeliveryTypeMapper deliveryTypeMapper;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDeliveryTypeMockMvc;

    private DeliveryType deliveryType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryTypeResource deliveryTypeResource = new DeliveryTypeResource(deliveryTypeService);
        this.restDeliveryTypeMockMvc = MockMvcBuilders.standaloneSetup(deliveryTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryType createEntity(EntityManager em) {
        DeliveryType deliveryType = new DeliveryType()
            .name(DEFAULT_NAME);
        return deliveryType;
    }

    @Before
    public void initTest() {
        deliveryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryType() throws Exception {
        int databaseSizeBeforeCreate = deliveryTypeRepository.findAll().size();

        // Create the DeliveryType
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeMapper.toDto(deliveryType);
        restDeliveryTypeMockMvc.perform(post("/api/delivery-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryType testDeliveryType = deliveryTypeList.get(deliveryTypeList.size() - 1);
        assertThat(testDeliveryType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDeliveryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryTypeRepository.findAll().size();

        // Create the DeliveryType with an existing ID
        deliveryType.setId(1L);
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeMapper.toDto(deliveryType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryTypeMockMvc.perform(post("/api/delivery-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeliveryTypes() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        // Get all the deliveryTypeList
        restDeliveryTypeMockMvc.perform(get("/api/delivery-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDeliveryType() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        // Get the deliveryType
        restDeliveryTypeMockMvc.perform(get("/api/delivery-types/{id}", deliveryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryType() throws Exception {
        // Get the deliveryType
        restDeliveryTypeMockMvc.perform(get("/api/delivery-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryType() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        int databaseSizeBeforeUpdate = deliveryTypeRepository.findAll().size();

        // Update the deliveryType
        DeliveryType updatedDeliveryType = deliveryTypeRepository.findById(deliveryType.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryType are not directly saved in db
        em.detach(updatedDeliveryType);
        updatedDeliveryType
            .name(UPDATED_NAME);
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeMapper.toDto(updatedDeliveryType);

        restDeliveryTypeMockMvc.perform(put("/api/delivery-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeUpdate);
        DeliveryType testDeliveryType = deliveryTypeList.get(deliveryTypeList.size() - 1);
        assertThat(testDeliveryType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryType() throws Exception {
        int databaseSizeBeforeUpdate = deliveryTypeRepository.findAll().size();

        // Create the DeliveryType
        DeliveryTypeDTO deliveryTypeDTO = deliveryTypeMapper.toDto(deliveryType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryTypeMockMvc.perform(put("/api/delivery-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryType in the database
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryType() throws Exception {
        // Initialize the database
        deliveryTypeRepository.saveAndFlush(deliveryType);

        int databaseSizeBeforeDelete = deliveryTypeRepository.findAll().size();

        // Delete the deliveryType
        restDeliveryTypeMockMvc.perform(delete("/api/delivery-types/{id}", deliveryType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeliveryType> deliveryTypeList = deliveryTypeRepository.findAll();
        assertThat(deliveryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryType.class);
        DeliveryType deliveryType1 = new DeliveryType();
        deliveryType1.setId(1L);
        DeliveryType deliveryType2 = new DeliveryType();
        deliveryType2.setId(deliveryType1.getId());
        assertThat(deliveryType1).isEqualTo(deliveryType2);
        deliveryType2.setId(2L);
        assertThat(deliveryType1).isNotEqualTo(deliveryType2);
        deliveryType1.setId(null);
        assertThat(deliveryType1).isNotEqualTo(deliveryType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryTypeDTO.class);
        DeliveryTypeDTO deliveryTypeDTO1 = new DeliveryTypeDTO();
        deliveryTypeDTO1.setId(1L);
        DeliveryTypeDTO deliveryTypeDTO2 = new DeliveryTypeDTO();
        assertThat(deliveryTypeDTO1).isNotEqualTo(deliveryTypeDTO2);
        deliveryTypeDTO2.setId(deliveryTypeDTO1.getId());
        assertThat(deliveryTypeDTO1).isEqualTo(deliveryTypeDTO2);
        deliveryTypeDTO2.setId(2L);
        assertThat(deliveryTypeDTO1).isNotEqualTo(deliveryTypeDTO2);
        deliveryTypeDTO1.setId(null);
        assertThat(deliveryTypeDTO1).isNotEqualTo(deliveryTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deliveryTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deliveryTypeMapper.fromId(null)).isNull();
    }
}
