package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.StoreApp;

import com.diviso.graeshoppe.domain.Store;
import com.diviso.graeshoppe.repository.StoreRepository;
import com.diviso.graeshoppe.service.StoreService;
import com.diviso.graeshoppe.service.dto.StoreDTO;
import com.diviso.graeshoppe.service.mapper.StoreMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.diviso.graeshoppe.web.rest.TestUtil.sameInstant;
import static com.diviso.graeshoppe.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StoreResource REST controller.
 *
 * @see StoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreApp.class)
public class StoreResourceIntTest {

    private static final String DEFAULT_IDP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IDP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_UNIQUE_ID = "AAAAAAAAAA";
    private static final String UPDATED_STORE_UNIQUE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_LINK = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_RATING = 1D;
    private static final Double UPDATED_TOTAL_RATING = 2D;

    private static final String DEFAULT_LAT_LON = "AAAAAAAAAA";
    private static final String UPDATED_LAT_LON = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_NUMBER = 1L;
    private static final Long UPDATED_CONTACT_NUMBER = 2L;

    private static final ZonedDateTime DEFAULT_OPENING_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OPENING_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CLOSING_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CLOSING_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final Double DEFAULT_MIN_AMOUNT = 1D;
    private static final Double UPDATED_MIN_AMOUNT = 2D;

    private static final ZonedDateTime DEFAULT_MAX_DELIVERY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MAX_DELIVERY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreService storeService;

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

    private MockMvc restStoreMockMvc;

    private Store store;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StoreResource storeResource = new StoreResource(storeService);
        this.restStoreMockMvc = MockMvcBuilders.standaloneSetup(storeResource)
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
    public static Store createEntity(EntityManager em) {
        Store store = new Store()
            .idpCode(DEFAULT_IDP_CODE)
            .storeUniqueId(DEFAULT_STORE_UNIQUE_ID)
            .name(DEFAULT_NAME)
            .imageLink(DEFAULT_IMAGE_LINK)
            .totalRating(DEFAULT_TOTAL_RATING)
            .latLon(DEFAULT_LAT_LON)
            .locationName(DEFAULT_LOCATION_NAME)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .openingTime(DEFAULT_OPENING_TIME)
            .email(DEFAULT_EMAIL)
            .closingTime(DEFAULT_CLOSING_TIME)
            .info(DEFAULT_INFO)
            .minAmount(DEFAULT_MIN_AMOUNT)
            .maxDeliveryTime(DEFAULT_MAX_DELIVERY_TIME);
        return store;
    }

    @Before
    public void initTest() {
        store = createEntity(em);
    }

    @Test
    @Transactional
    public void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);
        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isCreated());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate + 1);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getIdpCode()).isEqualTo(DEFAULT_IDP_CODE);
        assertThat(testStore.getStoreUniqueId()).isEqualTo(DEFAULT_STORE_UNIQUE_ID);
        assertThat(testStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStore.getImageLink()).isEqualTo(DEFAULT_IMAGE_LINK);
        assertThat(testStore.getTotalRating()).isEqualTo(DEFAULT_TOTAL_RATING);
        assertThat(testStore.getLatLon()).isEqualTo(DEFAULT_LAT_LON);
        assertThat(testStore.getLocationName()).isEqualTo(DEFAULT_LOCATION_NAME);
        assertThat(testStore.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testStore.getOpeningTime()).isEqualTo(DEFAULT_OPENING_TIME);
        assertThat(testStore.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStore.getClosingTime()).isEqualTo(DEFAULT_CLOSING_TIME);
        assertThat(testStore.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testStore.getMinAmount()).isEqualTo(DEFAULT_MIN_AMOUNT);
        assertThat(testStore.getMaxDeliveryTime()).isEqualTo(DEFAULT_MAX_DELIVERY_TIME);
    }

    @Test
    @Transactional
    public void createStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the Store with an existing ID
        store.setId(1L);
        StoreDTO storeDTO = storeMapper.toDto(store);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdpCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setIdpCode(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStoreUniqueIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setStoreUniqueId(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setName(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setImageLink(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatLonIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setLatLon(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setEmail(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStores() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList
        restStoreMockMvc.perform(get("/api/stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
            .andExpect(jsonPath("$.[*].idpCode").value(hasItem(DEFAULT_IDP_CODE.toString())))
            .andExpect(jsonPath("$.[*].storeUniqueId").value(hasItem(DEFAULT_STORE_UNIQUE_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageLink").value(hasItem(DEFAULT_IMAGE_LINK.toString())))
            .andExpect(jsonPath("$.[*].totalRating").value(hasItem(DEFAULT_TOTAL_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].latLon").value(hasItem(DEFAULT_LAT_LON.toString())))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].openingTime").value(hasItem(sameInstant(DEFAULT_OPENING_TIME))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].closingTime").value(hasItem(sameInstant(DEFAULT_CLOSING_TIME))))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())))
            .andExpect(jsonPath("$.[*].minAmount").value(hasItem(DEFAULT_MIN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxDeliveryTime").value(hasItem(sameInstant(DEFAULT_MAX_DELIVERY_TIME))));
    }
    
    @Test
    @Transactional
    public void getStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(store.getId().intValue()))
            .andExpect(jsonPath("$.idpCode").value(DEFAULT_IDP_CODE.toString()))
            .andExpect(jsonPath("$.storeUniqueId").value(DEFAULT_STORE_UNIQUE_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageLink").value(DEFAULT_IMAGE_LINK.toString()))
            .andExpect(jsonPath("$.totalRating").value(DEFAULT_TOTAL_RATING.doubleValue()))
            .andExpect(jsonPath("$.latLon").value(DEFAULT_LAT_LON.toString()))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.intValue()))
            .andExpect(jsonPath("$.openingTime").value(sameInstant(DEFAULT_OPENING_TIME)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.closingTime").value(sameInstant(DEFAULT_CLOSING_TIME)))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO.toString()))
            .andExpect(jsonPath("$.minAmount").value(DEFAULT_MIN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.maxDeliveryTime").value(sameInstant(DEFAULT_MAX_DELIVERY_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingStore() throws Exception {
        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store
        Store updatedStore = storeRepository.findById(store.getId()).get();
        // Disconnect from session so that the updates on updatedStore are not directly saved in db
        em.detach(updatedStore);
        updatedStore
            .idpCode(UPDATED_IDP_CODE)
            .storeUniqueId(UPDATED_STORE_UNIQUE_ID)
            .name(UPDATED_NAME)
            .imageLink(UPDATED_IMAGE_LINK)
            .totalRating(UPDATED_TOTAL_RATING)
            .latLon(UPDATED_LAT_LON)
            .locationName(UPDATED_LOCATION_NAME)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .openingTime(UPDATED_OPENING_TIME)
            .email(UPDATED_EMAIL)
            .closingTime(UPDATED_CLOSING_TIME)
            .info(UPDATED_INFO)
            .minAmount(UPDATED_MIN_AMOUNT)
            .maxDeliveryTime(UPDATED_MAX_DELIVERY_TIME);
        StoreDTO storeDTO = storeMapper.toDto(updatedStore);

        restStoreMockMvc.perform(put("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getIdpCode()).isEqualTo(UPDATED_IDP_CODE);
        assertThat(testStore.getStoreUniqueId()).isEqualTo(UPDATED_STORE_UNIQUE_ID);
        assertThat(testStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStore.getImageLink()).isEqualTo(UPDATED_IMAGE_LINK);
        assertThat(testStore.getTotalRating()).isEqualTo(UPDATED_TOTAL_RATING);
        assertThat(testStore.getLatLon()).isEqualTo(UPDATED_LAT_LON);
        assertThat(testStore.getLocationName()).isEqualTo(UPDATED_LOCATION_NAME);
        assertThat(testStore.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testStore.getOpeningTime()).isEqualTo(UPDATED_OPENING_TIME);
        assertThat(testStore.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStore.getClosingTime()).isEqualTo(UPDATED_CLOSING_TIME);
        assertThat(testStore.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testStore.getMinAmount()).isEqualTo(UPDATED_MIN_AMOUNT);
        assertThat(testStore.getMaxDeliveryTime()).isEqualTo(UPDATED_MAX_DELIVERY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreMockMvc.perform(put("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Delete the store
        restStoreMockMvc.perform(delete("/api/stores/{id}", store.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Store.class);
        Store store1 = new Store();
        store1.setId(1L);
        Store store2 = new Store();
        store2.setId(store1.getId());
        assertThat(store1).isEqualTo(store2);
        store2.setId(2L);
        assertThat(store1).isNotEqualTo(store2);
        store1.setId(null);
        assertThat(store1).isNotEqualTo(store2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreDTO.class);
        StoreDTO storeDTO1 = new StoreDTO();
        storeDTO1.setId(1L);
        StoreDTO storeDTO2 = new StoreDTO();
        assertThat(storeDTO1).isNotEqualTo(storeDTO2);
        storeDTO2.setId(storeDTO1.getId());
        assertThat(storeDTO1).isEqualTo(storeDTO2);
        storeDTO2.setId(2L);
        assertThat(storeDTO1).isNotEqualTo(storeDTO2);
        storeDTO1.setId(null);
        assertThat(storeDTO1).isNotEqualTo(storeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(storeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(storeMapper.fromId(null)).isNull();
    }
}
