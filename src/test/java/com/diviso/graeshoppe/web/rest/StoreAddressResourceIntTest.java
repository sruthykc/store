package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.StoreApp;

import com.diviso.graeshoppe.domain.StoreAddress;
import com.diviso.graeshoppe.repository.StoreAddressRepository;
import com.diviso.graeshoppe.service.StoreAddressService;
import com.diviso.graeshoppe.service.dto.StoreAddressDTO;
import com.diviso.graeshoppe.service.mapper.StoreAddressMapper;
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
 * Test class for the StoreAddressResource REST controller.
 *
 * @see StoreAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreApp.class)
public class StoreAddressResourceIntTest {

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    @Autowired
    private StoreAddressRepository storeAddressRepository;

    @Autowired
    private StoreAddressMapper storeAddressMapper;

    @Autowired
    private StoreAddressService storeAddressService;

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

    private MockMvc restStoreAddressMockMvc;

    private StoreAddress storeAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StoreAddressResource storeAddressResource = new StoreAddressResource(storeAddressService);
        this.restStoreAddressMockMvc = MockMvcBuilders.standaloneSetup(storeAddressResource)
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
    public static StoreAddress createEntity(EntityManager em) {
        StoreAddress storeAddress = new StoreAddress()
            .postCode(DEFAULT_POST_CODE)
            .houseNumber(DEFAULT_HOUSE_NUMBER)
            .street(DEFAULT_STREET)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .landmark(DEFAULT_LANDMARK);
        return storeAddress;
    }

    @Before
    public void initTest() {
        storeAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createStoreAddress() throws Exception {
        int databaseSizeBeforeCreate = storeAddressRepository.findAll().size();

        // Create the StoreAddress
        StoreAddressDTO storeAddressDTO = storeAddressMapper.toDto(storeAddress);
        restStoreAddressMockMvc.perform(post("/api/store-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreAddress in the database
        List<StoreAddress> storeAddressList = storeAddressRepository.findAll();
        assertThat(storeAddressList).hasSize(databaseSizeBeforeCreate + 1);
        StoreAddress testStoreAddress = storeAddressList.get(storeAddressList.size() - 1);
        assertThat(testStoreAddress.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testStoreAddress.getHouseNumber()).isEqualTo(DEFAULT_HOUSE_NUMBER);
        assertThat(testStoreAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testStoreAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testStoreAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testStoreAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testStoreAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
    }

    @Test
    @Transactional
    public void createStoreAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeAddressRepository.findAll().size();

        // Create the StoreAddress with an existing ID
        storeAddress.setId(1L);
        StoreAddressDTO storeAddressDTO = storeAddressMapper.toDto(storeAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreAddressMockMvc.perform(post("/api/store-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreAddress in the database
        List<StoreAddress> storeAddressList = storeAddressRepository.findAll();
        assertThat(storeAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStoreAddresses() throws Exception {
        // Initialize the database
        storeAddressRepository.saveAndFlush(storeAddress);

        // Get all the storeAddressList
        restStoreAddressMockMvc.perform(get("/api/store-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK.toString())));
    }
    
    @Test
    @Transactional
    public void getStoreAddress() throws Exception {
        // Initialize the database
        storeAddressRepository.saveAndFlush(storeAddress);

        // Get the storeAddress
        restStoreAddressMockMvc.perform(get("/api/store-addresses/{id}", storeAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storeAddress.getId().intValue()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStoreAddress() throws Exception {
        // Get the storeAddress
        restStoreAddressMockMvc.perform(get("/api/store-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStoreAddress() throws Exception {
        // Initialize the database
        storeAddressRepository.saveAndFlush(storeAddress);

        int databaseSizeBeforeUpdate = storeAddressRepository.findAll().size();

        // Update the storeAddress
        StoreAddress updatedStoreAddress = storeAddressRepository.findById(storeAddress.getId()).get();
        // Disconnect from session so that the updates on updatedStoreAddress are not directly saved in db
        em.detach(updatedStoreAddress);
        updatedStoreAddress
            .postCode(UPDATED_POST_CODE)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .street(UPDATED_STREET)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .landmark(UPDATED_LANDMARK);
        StoreAddressDTO storeAddressDTO = storeAddressMapper.toDto(updatedStoreAddress);

        restStoreAddressMockMvc.perform(put("/api/store-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeAddressDTO)))
            .andExpect(status().isOk());

        // Validate the StoreAddress in the database
        List<StoreAddress> storeAddressList = storeAddressRepository.findAll();
        assertThat(storeAddressList).hasSize(databaseSizeBeforeUpdate);
        StoreAddress testStoreAddress = storeAddressList.get(storeAddressList.size() - 1);
        assertThat(testStoreAddress.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testStoreAddress.getHouseNumber()).isEqualTo(UPDATED_HOUSE_NUMBER);
        assertThat(testStoreAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testStoreAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testStoreAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testStoreAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testStoreAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingStoreAddress() throws Exception {
        int databaseSizeBeforeUpdate = storeAddressRepository.findAll().size();

        // Create the StoreAddress
        StoreAddressDTO storeAddressDTO = storeAddressMapper.toDto(storeAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreAddressMockMvc.perform(put("/api/store-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreAddress in the database
        List<StoreAddress> storeAddressList = storeAddressRepository.findAll();
        assertThat(storeAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStoreAddress() throws Exception {
        // Initialize the database
        storeAddressRepository.saveAndFlush(storeAddress);

        int databaseSizeBeforeDelete = storeAddressRepository.findAll().size();

        // Delete the storeAddress
        restStoreAddressMockMvc.perform(delete("/api/store-addresses/{id}", storeAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StoreAddress> storeAddressList = storeAddressRepository.findAll();
        assertThat(storeAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreAddress.class);
        StoreAddress storeAddress1 = new StoreAddress();
        storeAddress1.setId(1L);
        StoreAddress storeAddress2 = new StoreAddress();
        storeAddress2.setId(storeAddress1.getId());
        assertThat(storeAddress1).isEqualTo(storeAddress2);
        storeAddress2.setId(2L);
        assertThat(storeAddress1).isNotEqualTo(storeAddress2);
        storeAddress1.setId(null);
        assertThat(storeAddress1).isNotEqualTo(storeAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreAddressDTO.class);
        StoreAddressDTO storeAddressDTO1 = new StoreAddressDTO();
        storeAddressDTO1.setId(1L);
        StoreAddressDTO storeAddressDTO2 = new StoreAddressDTO();
        assertThat(storeAddressDTO1).isNotEqualTo(storeAddressDTO2);
        storeAddressDTO2.setId(storeAddressDTO1.getId());
        assertThat(storeAddressDTO1).isEqualTo(storeAddressDTO2);
        storeAddressDTO2.setId(2L);
        assertThat(storeAddressDTO1).isNotEqualTo(storeAddressDTO2);
        storeAddressDTO1.setId(null);
        assertThat(storeAddressDTO1).isNotEqualTo(storeAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(storeAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(storeAddressMapper.fromId(null)).isNull();
    }
}
