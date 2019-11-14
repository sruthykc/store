package com.diviso.graeshoppe.web.rest;

import com.diviso.graeshoppe.StoreApp;

import com.diviso.graeshoppe.domain.StoreDeliveryInfo;
import com.diviso.graeshoppe.repository.StoreDeliveryInfoRepository;
import com.diviso.graeshoppe.service.StoreDeliveryInfoService;
import com.diviso.graeshoppe.service.dto.StoreDeliveryInfoDTO;
import com.diviso.graeshoppe.service.mapper.StoreDeliveryInfoMapper;
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
 * Test class for the StoreDeliveryInfoResource REST controller.
 *
 * @see StoreDeliveryInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StoreApp.class)
public class StoreDeliveryInfoResourceIntTest {

    private static final ZonedDateTime DEFAULT_STARTING_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STARTING_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private StoreDeliveryInfoRepository storeDeliveryInfoRepository;

    @Autowired
    private StoreDeliveryInfoMapper storeDeliveryInfoMapper;

    @Autowired
    private StoreDeliveryInfoService storeDeliveryInfoService;

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

    private MockMvc restStoreDeliveryInfoMockMvc;

    private StoreDeliveryInfo storeDeliveryInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StoreDeliveryInfoResource storeDeliveryInfoResource = new StoreDeliveryInfoResource(storeDeliveryInfoService);
        this.restStoreDeliveryInfoMockMvc = MockMvcBuilders.standaloneSetup(storeDeliveryInfoResource)
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
    public static StoreDeliveryInfo createEntity(EntityManager em) {
        StoreDeliveryInfo storeDeliveryInfo = new StoreDeliveryInfo()
            .startingTime(DEFAULT_STARTING_TIME)
            .endTime(DEFAULT_END_TIME);
        return storeDeliveryInfo;
    }

    @Before
    public void initTest() {
        storeDeliveryInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createStoreDeliveryInfo() throws Exception {
        int databaseSizeBeforeCreate = storeDeliveryInfoRepository.findAll().size();

        // Create the StoreDeliveryInfo
        StoreDeliveryInfoDTO storeDeliveryInfoDTO = storeDeliveryInfoMapper.toDto(storeDeliveryInfo);
        restStoreDeliveryInfoMockMvc.perform(post("/api/store-delivery-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDeliveryInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreDeliveryInfo in the database
        List<StoreDeliveryInfo> storeDeliveryInfoList = storeDeliveryInfoRepository.findAll();
        assertThat(storeDeliveryInfoList).hasSize(databaseSizeBeforeCreate + 1);
        StoreDeliveryInfo testStoreDeliveryInfo = storeDeliveryInfoList.get(storeDeliveryInfoList.size() - 1);
        assertThat(testStoreDeliveryInfo.getStartingTime()).isEqualTo(DEFAULT_STARTING_TIME);
        assertThat(testStoreDeliveryInfo.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createStoreDeliveryInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeDeliveryInfoRepository.findAll().size();

        // Create the StoreDeliveryInfo with an existing ID
        storeDeliveryInfo.setId(1L);
        StoreDeliveryInfoDTO storeDeliveryInfoDTO = storeDeliveryInfoMapper.toDto(storeDeliveryInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreDeliveryInfoMockMvc.perform(post("/api/store-delivery-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDeliveryInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreDeliveryInfo in the database
        List<StoreDeliveryInfo> storeDeliveryInfoList = storeDeliveryInfoRepository.findAll();
        assertThat(storeDeliveryInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStoreDeliveryInfos() throws Exception {
        // Initialize the database
        storeDeliveryInfoRepository.saveAndFlush(storeDeliveryInfo);

        // Get all the storeDeliveryInfoList
        restStoreDeliveryInfoMockMvc.perform(get("/api/store-delivery-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeDeliveryInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].startingTime").value(hasItem(sameInstant(DEFAULT_STARTING_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))));
    }
    
    @Test
    @Transactional
    public void getStoreDeliveryInfo() throws Exception {
        // Initialize the database
        storeDeliveryInfoRepository.saveAndFlush(storeDeliveryInfo);

        // Get the storeDeliveryInfo
        restStoreDeliveryInfoMockMvc.perform(get("/api/store-delivery-infos/{id}", storeDeliveryInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storeDeliveryInfo.getId().intValue()))
            .andExpect(jsonPath("$.startingTime").value(sameInstant(DEFAULT_STARTING_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingStoreDeliveryInfo() throws Exception {
        // Get the storeDeliveryInfo
        restStoreDeliveryInfoMockMvc.perform(get("/api/store-delivery-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStoreDeliveryInfo() throws Exception {
        // Initialize the database
        storeDeliveryInfoRepository.saveAndFlush(storeDeliveryInfo);

        int databaseSizeBeforeUpdate = storeDeliveryInfoRepository.findAll().size();

        // Update the storeDeliveryInfo
        StoreDeliveryInfo updatedStoreDeliveryInfo = storeDeliveryInfoRepository.findById(storeDeliveryInfo.getId()).get();
        // Disconnect from session so that the updates on updatedStoreDeliveryInfo are not directly saved in db
        em.detach(updatedStoreDeliveryInfo);
        updatedStoreDeliveryInfo
            .startingTime(UPDATED_STARTING_TIME)
            .endTime(UPDATED_END_TIME);
        StoreDeliveryInfoDTO storeDeliveryInfoDTO = storeDeliveryInfoMapper.toDto(updatedStoreDeliveryInfo);

        restStoreDeliveryInfoMockMvc.perform(put("/api/store-delivery-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDeliveryInfoDTO)))
            .andExpect(status().isOk());

        // Validate the StoreDeliveryInfo in the database
        List<StoreDeliveryInfo> storeDeliveryInfoList = storeDeliveryInfoRepository.findAll();
        assertThat(storeDeliveryInfoList).hasSize(databaseSizeBeforeUpdate);
        StoreDeliveryInfo testStoreDeliveryInfo = storeDeliveryInfoList.get(storeDeliveryInfoList.size() - 1);
        assertThat(testStoreDeliveryInfo.getStartingTime()).isEqualTo(UPDATED_STARTING_TIME);
        assertThat(testStoreDeliveryInfo.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingStoreDeliveryInfo() throws Exception {
        int databaseSizeBeforeUpdate = storeDeliveryInfoRepository.findAll().size();

        // Create the StoreDeliveryInfo
        StoreDeliveryInfoDTO storeDeliveryInfoDTO = storeDeliveryInfoMapper.toDto(storeDeliveryInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreDeliveryInfoMockMvc.perform(put("/api/store-delivery-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDeliveryInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StoreDeliveryInfo in the database
        List<StoreDeliveryInfo> storeDeliveryInfoList = storeDeliveryInfoRepository.findAll();
        assertThat(storeDeliveryInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStoreDeliveryInfo() throws Exception {
        // Initialize the database
        storeDeliveryInfoRepository.saveAndFlush(storeDeliveryInfo);

        int databaseSizeBeforeDelete = storeDeliveryInfoRepository.findAll().size();

        // Delete the storeDeliveryInfo
        restStoreDeliveryInfoMockMvc.perform(delete("/api/store-delivery-infos/{id}", storeDeliveryInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StoreDeliveryInfo> storeDeliveryInfoList = storeDeliveryInfoRepository.findAll();
        assertThat(storeDeliveryInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreDeliveryInfo.class);
        StoreDeliveryInfo storeDeliveryInfo1 = new StoreDeliveryInfo();
        storeDeliveryInfo1.setId(1L);
        StoreDeliveryInfo storeDeliveryInfo2 = new StoreDeliveryInfo();
        storeDeliveryInfo2.setId(storeDeliveryInfo1.getId());
        assertThat(storeDeliveryInfo1).isEqualTo(storeDeliveryInfo2);
        storeDeliveryInfo2.setId(2L);
        assertThat(storeDeliveryInfo1).isNotEqualTo(storeDeliveryInfo2);
        storeDeliveryInfo1.setId(null);
        assertThat(storeDeliveryInfo1).isNotEqualTo(storeDeliveryInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreDeliveryInfoDTO.class);
        StoreDeliveryInfoDTO storeDeliveryInfoDTO1 = new StoreDeliveryInfoDTO();
        storeDeliveryInfoDTO1.setId(1L);
        StoreDeliveryInfoDTO storeDeliveryInfoDTO2 = new StoreDeliveryInfoDTO();
        assertThat(storeDeliveryInfoDTO1).isNotEqualTo(storeDeliveryInfoDTO2);
        storeDeliveryInfoDTO2.setId(storeDeliveryInfoDTO1.getId());
        assertThat(storeDeliveryInfoDTO1).isEqualTo(storeDeliveryInfoDTO2);
        storeDeliveryInfoDTO2.setId(2L);
        assertThat(storeDeliveryInfoDTO1).isNotEqualTo(storeDeliveryInfoDTO2);
        storeDeliveryInfoDTO1.setId(null);
        assertThat(storeDeliveryInfoDTO1).isNotEqualTo(storeDeliveryInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(storeDeliveryInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(storeDeliveryInfoMapper.fromId(null)).isNull();
    }
}
