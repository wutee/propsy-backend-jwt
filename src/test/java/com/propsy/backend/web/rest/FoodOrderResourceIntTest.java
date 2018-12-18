package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyBackendJwtApp;

import com.propsy.backend.domain.FoodOrder;
import com.propsy.backend.repository.FoodOrderRepository;
import com.propsy.backend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;


import static com.propsy.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.propsy.backend.domain.enumeration.OrderStatus;
/**
 * Test class for the FoodOrderResource REST controller.
 *
 * @see FoodOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyBackendJwtApp.class)
public class FoodOrderResourceIntTest {

    private static final Integer DEFAULT_TIME_RATING = 1;
    private static final Integer UPDATED_TIME_RATING = 2;

    private static final Integer DEFAULT_PRICE_RATING = 1;
    private static final Integer UPDATED_PRICE_RATING = 2;

    private static final Integer DEFAULT_QUALITY_RATING = 1;
    private static final Integer UPDATED_QUALITY_RATING = 2;

    private static final Integer DEFAULT_LOYALTY_POINTS = 1;
    private static final Integer UPDATED_LOYALTY_POINTS = 2;

    private static final Integer DEFAULT_ADDRESS_RATING = 1;
    private static final Integer UPDATED_ADDRESS_RATING = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_PRICE = 0F;
    private static final Float UPDATED_PRICE = 1F;

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.NEW;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.PAYMENT;

    private static final String DEFAULT_PURCHASER_OPINION = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASER_OPINION = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASER_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASER_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Mock
    private FoodOrderRepository foodOrderRepositoryMock;

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

    private MockMvc restFoodOrderMockMvc;

    private FoodOrder foodOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodOrderResource foodOrderResource = new FoodOrderResource(foodOrderRepository);
        this.restFoodOrderMockMvc = MockMvcBuilders.standaloneSetup(foodOrderResource)
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
    public static FoodOrder createEntity(EntityManager em) {
        FoodOrder foodOrder = new FoodOrder()
            .timeRating(DEFAULT_TIME_RATING)
            .priceRating(DEFAULT_PRICE_RATING)
            .qualityRating(DEFAULT_QUALITY_RATING)
            .loyaltyPoints(DEFAULT_LOYALTY_POINTS)
            .addressRating(DEFAULT_ADDRESS_RATING)
            .date(DEFAULT_DATE)
            .price(DEFAULT_PRICE)
            .status(DEFAULT_STATUS)
            .purchaserOpinion(DEFAULT_PURCHASER_OPINION)
            .purchaserComment(DEFAULT_PURCHASER_COMMENT)
            .city(DEFAULT_CITY)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS);
        return foodOrder;
    }

    @Before
    public void initTest() {
        foodOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodOrder() throws Exception {
        int databaseSizeBeforeCreate = foodOrderRepository.findAll().size();

        // Create the FoodOrder
        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isCreated());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeCreate + 1);
        FoodOrder testFoodOrder = foodOrderList.get(foodOrderList.size() - 1);
        assertThat(testFoodOrder.getTimeRating()).isEqualTo(DEFAULT_TIME_RATING);
        assertThat(testFoodOrder.getPriceRating()).isEqualTo(DEFAULT_PRICE_RATING);
        assertThat(testFoodOrder.getQualityRating()).isEqualTo(DEFAULT_QUALITY_RATING);
        assertThat(testFoodOrder.getLoyaltyPoints()).isEqualTo(DEFAULT_LOYALTY_POINTS);
        assertThat(testFoodOrder.getAddressRating()).isEqualTo(DEFAULT_ADDRESS_RATING);
        assertThat(testFoodOrder.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFoodOrder.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFoodOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFoodOrder.getPurchaserOpinion()).isEqualTo(DEFAULT_PURCHASER_OPINION);
        assertThat(testFoodOrder.getPurchaserComment()).isEqualTo(DEFAULT_PURCHASER_COMMENT);
        assertThat(testFoodOrder.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testFoodOrder.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testFoodOrder.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createFoodOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodOrderRepository.findAll().size();

        // Create the FoodOrder with an existing ID
        foodOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setDate(null);

        // Create the FoodOrder, which fails.

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setPrice(null);

        // Create the FoodOrder, which fails.

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodOrderRepository.findAll().size();
        // set the field null
        foodOrder.setStatus(null);

        // Create the FoodOrder, which fails.

        restFoodOrderMockMvc.perform(post("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoodOrders() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        // Get all the foodOrderList
        restFoodOrderMockMvc.perform(get("/api/food-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeRating").value(hasItem(DEFAULT_TIME_RATING)))
            .andExpect(jsonPath("$.[*].priceRating").value(hasItem(DEFAULT_PRICE_RATING)))
            .andExpect(jsonPath("$.[*].qualityRating").value(hasItem(DEFAULT_QUALITY_RATING)))
            .andExpect(jsonPath("$.[*].loyaltyPoints").value(hasItem(DEFAULT_LOYALTY_POINTS)))
            .andExpect(jsonPath("$.[*].addressRating").value(hasItem(DEFAULT_ADDRESS_RATING)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].purchaserOpinion").value(hasItem(DEFAULT_PURCHASER_OPINION.toString())))
            .andExpect(jsonPath("$.[*].purchaserComment").value(hasItem(DEFAULT_PURCHASER_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFoodOrdersWithEagerRelationshipsIsEnabled() throws Exception {
        FoodOrderResource foodOrderResource = new FoodOrderResource(foodOrderRepositoryMock);
        when(foodOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFoodOrderMockMvc = MockMvcBuilders.standaloneSetup(foodOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFoodOrderMockMvc.perform(get("/api/food-orders?eagerload=true"))
        .andExpect(status().isOk());

        verify(foodOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFoodOrdersWithEagerRelationshipsIsNotEnabled() throws Exception {
        FoodOrderResource foodOrderResource = new FoodOrderResource(foodOrderRepositoryMock);
            when(foodOrderRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFoodOrderMockMvc = MockMvcBuilders.standaloneSetup(foodOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFoodOrderMockMvc.perform(get("/api/food-orders?eagerload=true"))
        .andExpect(status().isOk());

            verify(foodOrderRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFoodOrder() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        // Get the foodOrder
        restFoodOrderMockMvc.perform(get("/api/food-orders/{id}", foodOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(foodOrder.getId().intValue()))
            .andExpect(jsonPath("$.timeRating").value(DEFAULT_TIME_RATING))
            .andExpect(jsonPath("$.priceRating").value(DEFAULT_PRICE_RATING))
            .andExpect(jsonPath("$.qualityRating").value(DEFAULT_QUALITY_RATING))
            .andExpect(jsonPath("$.loyaltyPoints").value(DEFAULT_LOYALTY_POINTS))
            .andExpect(jsonPath("$.addressRating").value(DEFAULT_ADDRESS_RATING))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.purchaserOpinion").value(DEFAULT_PURCHASER_OPINION.toString()))
            .andExpect(jsonPath("$.purchaserComment").value(DEFAULT_PURCHASER_COMMENT.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFoodOrder() throws Exception {
        // Get the foodOrder
        restFoodOrderMockMvc.perform(get("/api/food-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodOrder() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        int databaseSizeBeforeUpdate = foodOrderRepository.findAll().size();

        // Update the foodOrder
        FoodOrder updatedFoodOrder = foodOrderRepository.findById(foodOrder.getId()).get();
        // Disconnect from session so that the updates on updatedFoodOrder are not directly saved in db
        em.detach(updatedFoodOrder);
        updatedFoodOrder
            .timeRating(UPDATED_TIME_RATING)
            .priceRating(UPDATED_PRICE_RATING)
            .qualityRating(UPDATED_QUALITY_RATING)
            .loyaltyPoints(UPDATED_LOYALTY_POINTS)
            .addressRating(UPDATED_ADDRESS_RATING)
            .date(UPDATED_DATE)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .purchaserOpinion(UPDATED_PURCHASER_OPINION)
            .purchaserComment(UPDATED_PURCHASER_COMMENT)
            .city(UPDATED_CITY)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS);

        restFoodOrderMockMvc.perform(put("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFoodOrder)))
            .andExpect(status().isOk());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeUpdate);
        FoodOrder testFoodOrder = foodOrderList.get(foodOrderList.size() - 1);
        assertThat(testFoodOrder.getTimeRating()).isEqualTo(UPDATED_TIME_RATING);
        assertThat(testFoodOrder.getPriceRating()).isEqualTo(UPDATED_PRICE_RATING);
        assertThat(testFoodOrder.getQualityRating()).isEqualTo(UPDATED_QUALITY_RATING);
        assertThat(testFoodOrder.getLoyaltyPoints()).isEqualTo(UPDATED_LOYALTY_POINTS);
        assertThat(testFoodOrder.getAddressRating()).isEqualTo(UPDATED_ADDRESS_RATING);
        assertThat(testFoodOrder.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFoodOrder.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFoodOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFoodOrder.getPurchaserOpinion()).isEqualTo(UPDATED_PURCHASER_OPINION);
        assertThat(testFoodOrder.getPurchaserComment()).isEqualTo(UPDATED_PURCHASER_COMMENT);
        assertThat(testFoodOrder.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testFoodOrder.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testFoodOrder.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodOrder() throws Exception {
        int databaseSizeBeforeUpdate = foodOrderRepository.findAll().size();

        // Create the FoodOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodOrderMockMvc.perform(put("/api/food-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(foodOrder)))
            .andExpect(status().isBadRequest());

        // Validate the FoodOrder in the database
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFoodOrder() throws Exception {
        // Initialize the database
        foodOrderRepository.saveAndFlush(foodOrder);

        int databaseSizeBeforeDelete = foodOrderRepository.findAll().size();

        // Get the foodOrder
        restFoodOrderMockMvc.perform(delete("/api/food-orders/{id}", foodOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FoodOrder> foodOrderList = foodOrderRepository.findAll();
        assertThat(foodOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodOrder.class);
        FoodOrder foodOrder1 = new FoodOrder();
        foodOrder1.setId(1L);
        FoodOrder foodOrder2 = new FoodOrder();
        foodOrder2.setId(foodOrder1.getId());
        assertThat(foodOrder1).isEqualTo(foodOrder2);
        foodOrder2.setId(2L);
        assertThat(foodOrder1).isNotEqualTo(foodOrder2);
        foodOrder1.setId(null);
        assertThat(foodOrder1).isNotEqualTo(foodOrder2);
    }
}
