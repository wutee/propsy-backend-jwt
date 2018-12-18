package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyBackendJwtApp;

import com.propsy.backend.domain.Food;
import com.propsy.backend.repository.FoodRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.propsy.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FoodResource REST controller.
 *
 * @see FoodResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyBackendJwtApp.class)
public class FoodResourceIntTest {

    private static final String DEFAULT_NAME_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_NAME_SLUG = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 0F;
    private static final Float UPDATED_PRICE = 1F;

    private static final String DEFAULT_FOOD_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FOOD_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CALORIES = 1;
    private static final Integer UPDATED_CALORIES = 2;

    private static final Boolean DEFAULT_IS_SPICY = false;
    private static final Boolean UPDATED_IS_SPICY = true;

    private static final Boolean DEFAULT_IS_VEGETARIAN = false;
    private static final Boolean UPDATED_IS_VEGETARIAN = true;

    private static final Boolean DEFAULT_IS_GLUTEN_FREE = false;
    private static final Boolean UPDATED_IS_GLUTEN_FREE = true;

    private static final byte[] DEFAULT_PHOTO_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_BLOB_CONTENT_TYPE = "image/png";

    @Autowired
    private FoodRepository foodRepository;

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

    private MockMvc restFoodMockMvc;

    private Food food;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FoodResource foodResource = new FoodResource(foodRepository);
        this.restFoodMockMvc = MockMvcBuilders.standaloneSetup(foodResource)
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
    public static Food createEntity(EntityManager em) {
        Food food = new Food()
            .nameSlug(DEFAULT_NAME_SLUG)
            .price(DEFAULT_PRICE)
            .foodDescription(DEFAULT_FOOD_DESCRIPTION)
            .calories(DEFAULT_CALORIES)
            .isSpicy(DEFAULT_IS_SPICY)
            .isVegetarian(DEFAULT_IS_VEGETARIAN)
            .isGlutenFree(DEFAULT_IS_GLUTEN_FREE)
            .photoBlob(DEFAULT_PHOTO_BLOB)
            .photoBlobContentType(DEFAULT_PHOTO_BLOB_CONTENT_TYPE);
        return food;
    }

    @Before
    public void initTest() {
        food = createEntity(em);
    }

    @Test
    @Transactional
    public void createFood() throws Exception {
        int databaseSizeBeforeCreate = foodRepository.findAll().size();

        // Create the Food
        restFoodMockMvc.perform(post("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(food)))
            .andExpect(status().isCreated());

        // Validate the Food in the database
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeCreate + 1);
        Food testFood = foodList.get(foodList.size() - 1);
        assertThat(testFood.getNameSlug()).isEqualTo(DEFAULT_NAME_SLUG);
        assertThat(testFood.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFood.getFoodDescription()).isEqualTo(DEFAULT_FOOD_DESCRIPTION);
        assertThat(testFood.getCalories()).isEqualTo(DEFAULT_CALORIES);
        assertThat(testFood.isIsSpicy()).isEqualTo(DEFAULT_IS_SPICY);
        assertThat(testFood.isIsVegetarian()).isEqualTo(DEFAULT_IS_VEGETARIAN);
        assertThat(testFood.isIsGlutenFree()).isEqualTo(DEFAULT_IS_GLUTEN_FREE);
        assertThat(testFood.getPhotoBlob()).isEqualTo(DEFAULT_PHOTO_BLOB);
        assertThat(testFood.getPhotoBlobContentType()).isEqualTo(DEFAULT_PHOTO_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFoodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodRepository.findAll().size();

        // Create the Food with an existing ID
        food.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodMockMvc.perform(post("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(food)))
            .andExpect(status().isBadRequest());

        // Validate the Food in the database
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodRepository.findAll().size();
        // set the field null
        food.setNameSlug(null);

        // Create the Food, which fails.

        restFoodMockMvc.perform(post("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(food)))
            .andExpect(status().isBadRequest());

        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodRepository.findAll().size();
        // set the field null
        food.setPrice(null);

        // Create the Food, which fails.

        restFoodMockMvc.perform(post("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(food)))
            .andExpect(status().isBadRequest());

        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoods() throws Exception {
        // Initialize the database
        foodRepository.saveAndFlush(food);

        // Get all the foodList
        restFoodMockMvc.perform(get("/api/foods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(food.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameSlug").value(hasItem(DEFAULT_NAME_SLUG.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].foodDescription").value(hasItem(DEFAULT_FOOD_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].calories").value(hasItem(DEFAULT_CALORIES)))
            .andExpect(jsonPath("$.[*].isSpicy").value(hasItem(DEFAULT_IS_SPICY.booleanValue())))
            .andExpect(jsonPath("$.[*].isVegetarian").value(hasItem(DEFAULT_IS_VEGETARIAN.booleanValue())))
            .andExpect(jsonPath("$.[*].isGlutenFree").value(hasItem(DEFAULT_IS_GLUTEN_FREE.booleanValue())))
            .andExpect(jsonPath("$.[*].photoBlobContentType").value(hasItem(DEFAULT_PHOTO_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_BLOB))));
    }
    
    @Test
    @Transactional
    public void getFood() throws Exception {
        // Initialize the database
        foodRepository.saveAndFlush(food);

        // Get the food
        restFoodMockMvc.perform(get("/api/foods/{id}", food.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(food.getId().intValue()))
            .andExpect(jsonPath("$.nameSlug").value(DEFAULT_NAME_SLUG.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.foodDescription").value(DEFAULT_FOOD_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.calories").value(DEFAULT_CALORIES))
            .andExpect(jsonPath("$.isSpicy").value(DEFAULT_IS_SPICY.booleanValue()))
            .andExpect(jsonPath("$.isVegetarian").value(DEFAULT_IS_VEGETARIAN.booleanValue()))
            .andExpect(jsonPath("$.isGlutenFree").value(DEFAULT_IS_GLUTEN_FREE.booleanValue()))
            .andExpect(jsonPath("$.photoBlobContentType").value(DEFAULT_PHOTO_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.photoBlob").value(Base64Utils.encodeToString(DEFAULT_PHOTO_BLOB)));
    }

    @Test
    @Transactional
    public void getNonExistingFood() throws Exception {
        // Get the food
        restFoodMockMvc.perform(get("/api/foods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFood() throws Exception {
        // Initialize the database
        foodRepository.saveAndFlush(food);

        int databaseSizeBeforeUpdate = foodRepository.findAll().size();

        // Update the food
        Food updatedFood = foodRepository.findById(food.getId()).get();
        // Disconnect from session so that the updates on updatedFood are not directly saved in db
        em.detach(updatedFood);
        updatedFood
            .nameSlug(UPDATED_NAME_SLUG)
            .price(UPDATED_PRICE)
            .foodDescription(UPDATED_FOOD_DESCRIPTION)
            .calories(UPDATED_CALORIES)
            .isSpicy(UPDATED_IS_SPICY)
            .isVegetarian(UPDATED_IS_VEGETARIAN)
            .isGlutenFree(UPDATED_IS_GLUTEN_FREE)
            .photoBlob(UPDATED_PHOTO_BLOB)
            .photoBlobContentType(UPDATED_PHOTO_BLOB_CONTENT_TYPE);

        restFoodMockMvc.perform(put("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFood)))
            .andExpect(status().isOk());

        // Validate the Food in the database
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeUpdate);
        Food testFood = foodList.get(foodList.size() - 1);
        assertThat(testFood.getNameSlug()).isEqualTo(UPDATED_NAME_SLUG);
        assertThat(testFood.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFood.getFoodDescription()).isEqualTo(UPDATED_FOOD_DESCRIPTION);
        assertThat(testFood.getCalories()).isEqualTo(UPDATED_CALORIES);
        assertThat(testFood.isIsSpicy()).isEqualTo(UPDATED_IS_SPICY);
        assertThat(testFood.isIsVegetarian()).isEqualTo(UPDATED_IS_VEGETARIAN);
        assertThat(testFood.isIsGlutenFree()).isEqualTo(UPDATED_IS_GLUTEN_FREE);
        assertThat(testFood.getPhotoBlob()).isEqualTo(UPDATED_PHOTO_BLOB);
        assertThat(testFood.getPhotoBlobContentType()).isEqualTo(UPDATED_PHOTO_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFood() throws Exception {
        int databaseSizeBeforeUpdate = foodRepository.findAll().size();

        // Create the Food

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodMockMvc.perform(put("/api/foods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(food)))
            .andExpect(status().isBadRequest());

        // Validate the Food in the database
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFood() throws Exception {
        // Initialize the database
        foodRepository.saveAndFlush(food);

        int databaseSizeBeforeDelete = foodRepository.findAll().size();

        // Get the food
        restFoodMockMvc.perform(delete("/api/foods/{id}", food.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Food> foodList = foodRepository.findAll();
        assertThat(foodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Food.class);
        Food food1 = new Food();
        food1.setId(1L);
        Food food2 = new Food();
        food2.setId(food1.getId());
        assertThat(food1).isEqualTo(food2);
        food2.setId(2L);
        assertThat(food1).isNotEqualTo(food2);
        food1.setId(null);
        assertThat(food1).isNotEqualTo(food2);
    }
}
