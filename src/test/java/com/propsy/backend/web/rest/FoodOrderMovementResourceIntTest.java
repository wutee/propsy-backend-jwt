package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyBackendJwtApp;

import com.propsy.backend.domain.FoodOrderMovement;
import com.propsy.backend.repository.FoodOrderMovementRepository;
import com.propsy.backend.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.propsy.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.propsy.backend.domain.enumeration.FoodOrderParticipant;
/**
 * Test class for the FoodOrderMovementResource REST controller.
 *
 * @see FoodOrderMovementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyBackendJwtApp.class)
public class FoodOrderMovementResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final FoodOrderParticipant DEFAULT_SENDER = FoodOrderParticipant.DELIVERYMAN;
    private static final FoodOrderParticipant UPDATED_SENDER = FoodOrderParticipant.PURCHASER;

    @Autowired
    private FoodOrderMovementRepository foodOrderMovementRepository;

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

    private MockMvc restFoodOrderMovementMockMvc;

    private FoodOrderMovement foodOrderMovement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodOrderMovementResource foodOrderMovementResource = new FoodOrderMovementResource(foodOrderMovementRepository);
        this.restFoodOrderMovementMockMvc = MockMvcBuilders.standaloneSetup(foodOrderMovementResource)
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
    public static FoodOrderMovement createEntity(EntityManager em) {
        FoodOrderMovement foodOrderMovement = new FoodOrderMovement()
            .date(DEFAULT_DATE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .sender(DEFAULT_SENDER);
        return foodOrderMovement;
    }

    @Before
    public void initTest() {
        foodOrderMovement = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodOrderMovement() throws Exception {
        int databaseSizeBeforeCreate = foodOrderMovementRepository.findAll().size();

        // Create the FoodOrderMovement
        restFoodOrderMovementMockMvc.perform(post("/api/food-order-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderMovement)))
            .andExpect(status().isCreated());

        // Validate the FoodOrderMovement in the database
        List<FoodOrderMovement> foodOrderMovementList = foodOrderMovementRepository.findAll();
        assertThat(foodOrderMovementList).hasSize(databaseSizeBeforeCreate + 1);
        FoodOrderMovement testFoodOrderMovement = foodOrderMovementList.get(foodOrderMovementList.size() - 1);
        assertThat(testFoodOrderMovement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFoodOrderMovement.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testFoodOrderMovement.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testFoodOrderMovement.getSender()).isEqualTo(DEFAULT_SENDER);
    }

    @Test
    @Transactional
    public void createFoodOrderMovementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodOrderMovementRepository.findAll().size();

        // Create the FoodOrderMovement with an existing ID
        foodOrderMovement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodOrderMovementMockMvc.perform(post("/api/food-order-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderMovement)))
            .andExpect(status().isBadRequest());

        // Validate the FoodOrderMovement in the database
        List<FoodOrderMovement> foodOrderMovementList = foodOrderMovementRepository.findAll();
        assertThat(foodOrderMovementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderMovementRepository.findAll().size();
        // set the field null
        foodOrderMovement.setDate(null);

        // Create the FoodOrderMovement, which fails.

        restFoodOrderMovementMockMvc.perform(post("/api/food-order-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderMovement)))
            .andExpect(status().isBadRequest());

        List<FoodOrderMovement> foodOrderMovementList = foodOrderMovementRepository.findAll();
        assertThat(foodOrderMovementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderMovementRepository.findAll().size();
        // set the field null
        foodOrderMovement.setLatitude(null);

        // Create the FoodOrderMovement, which fails.

        restFoodOrderMovementMockMvc.perform(post("/api/food-order-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderMovement)))
            .andExpect(status().isBadRequest());

        List<FoodOrderMovement> foodOrderMovementList = foodOrderMovementRepository.findAll();
        assertThat(foodOrderMovementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderMovementRepository.findAll().size();
        // set the field null
        foodOrderMovement.setLongitude(null);

        // Create the FoodOrderMovement, which fails.

        restFoodOrderMovementMockMvc.perform(post("/api/food-order-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderMovement)))
            .andExpect(status().isBadRequest());

        List<FoodOrderMovement> foodOrderMovementList = foodOrderMovementRepository.findAll();
        assertThat(foodOrderMovementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderMovementRepository.findAll().size();
        // set the field null
        foodOrderMovement.setSender(null);

        // Create the FoodOrderMovement, which fails.

        restFoodOrderMovementMockMvc.perform(post("/api/food-order-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderMovement)))
            .andExpect(status().isBadRequest());

        List<FoodOrderMovement> foodOrderMovementList = foodOrderMovementRepository.findAll();
        assertThat(foodOrderMovementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoodOrderMovements() throws Exception {
        // Initialize the database
        foodOrderMovementRepository.saveAndFlush(foodOrderMovement);

        // Get all the foodOrderMovementList
        restFoodOrderMovementMockMvc.perform(get("/api/food-order-movements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodOrderMovement.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER.toString())));
    }
    
    @Test
    @Transactional
    public void getFoodOrderMovement() throws Exception {
        // Initialize the database
        foodOrderMovementRepository.saveAndFlush(foodOrderMovement);

        // Get the foodOrderMovement
        restFoodOrderMovementMockMvc.perform(get("/api/food-order-movements/{id}", foodOrderMovement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodOrderMovement.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.sender").value(DEFAULT_SENDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFoodOrderMovement() throws Exception {
        // Get the foodOrderMovement
        restFoodOrderMovementMockMvc.perform(get("/api/food-order-movements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodOrderMovement() throws Exception {
        // Initialize the database
        foodOrderMovementRepository.saveAndFlush(foodOrderMovement);

        int databaseSizeBeforeUpdate = foodOrderMovementRepository.findAll().size();

        // Update the foodOrderMovement
        FoodOrderMovement updatedFoodOrderMovement = foodOrderMovementRepository.findById(foodOrderMovement.getId()).get();
        // Disconnect from session so that the updates on updatedFoodOrderMovement are not directly saved in db
        em.detach(updatedFoodOrderMovement);
        updatedFoodOrderMovement
            .date(UPDATED_DATE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .sender(UPDATED_SENDER);

        restFoodOrderMovementMockMvc.perform(put("/api/food-order-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoodOrderMovement)))
            .andExpect(status().isOk());

        // Validate the FoodOrderMovement in the database
        List<FoodOrderMovement> foodOrderMovementList = foodOrderMovementRepository.findAll();
        assertThat(foodOrderMovementList).hasSize(databaseSizeBeforeUpdate);
        FoodOrderMovement testFoodOrderMovement = foodOrderMovementList.get(foodOrderMovementList.size() - 1);
        assertThat(testFoodOrderMovement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFoodOrderMovement.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testFoodOrderMovement.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testFoodOrderMovement.getSender()).isEqualTo(UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodOrderMovement() throws Exception {
        int databaseSizeBeforeUpdate = foodOrderMovementRepository.findAll().size();

        // Create the FoodOrderMovement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodOrderMovementMockMvc.perform(put("/api/food-order-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrderMovement)))
            .andExpect(status().isBadRequest());

        // Validate the FoodOrderMovement in the database
        List<FoodOrderMovement> foodOrderMovementList = foodOrderMovementRepository.findAll();
        assertThat(foodOrderMovementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoodOrderMovement() throws Exception {
        // Initialize the database
        foodOrderMovementRepository.saveAndFlush(foodOrderMovement);

        int databaseSizeBeforeDelete = foodOrderMovementRepository.findAll().size();

        // Get the foodOrderMovement
        restFoodOrderMovementMockMvc.perform(delete("/api/food-order-movements/{id}", foodOrderMovement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FoodOrderMovement> foodOrderMovementList = foodOrderMovementRepository.findAll();
        assertThat(foodOrderMovementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodOrderMovement.class);
        FoodOrderMovement foodOrderMovement1 = new FoodOrderMovement();
        foodOrderMovement1.setId(1L);
        FoodOrderMovement foodOrderMovement2 = new FoodOrderMovement();
        foodOrderMovement2.setId(foodOrderMovement1.getId());
        assertThat(foodOrderMovement1).isEqualTo(foodOrderMovement2);
        foodOrderMovement2.setId(2L);
        assertThat(foodOrderMovement1).isNotEqualTo(foodOrderMovement2);
        foodOrderMovement1.setId(null);
        assertThat(foodOrderMovement1).isNotEqualTo(foodOrderMovement2);
    }
}
