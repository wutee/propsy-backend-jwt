package com.propsy.backend.web.rest;

import com.propsy.backend.PropsyBackendJwtApp;

import com.propsy.backend.domain.ExtendedUser;
import com.propsy.backend.repository.ExtendedUserRepository;
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
import java.util.List;


import static com.propsy.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExtendedUserResource REST controller.
 *
 * @see ExtendedUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PropsyBackendJwtApp.class)
public class ExtendedUserResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOYALTY_POINTS = 1;
    private static final Integer UPDATED_LOYALTY_POINTS = 2;

    @Autowired
    private ExtendedUserRepository extendedUserRepository;

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

    private MockMvc restExtendedUserMockMvc;

    private ExtendedUser extendedUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExtendedUserResource extendedUserResource = new ExtendedUserResource(extendedUserRepository);
        this.restExtendedUserMockMvc = MockMvcBuilders.standaloneSetup(extendedUserResource)
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
    public static ExtendedUser createEntity(EntityManager em) {
        ExtendedUser extendedUser = new ExtendedUser()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .phone(DEFAULT_PHONE)
            .loyaltyPoints(DEFAULT_LOYALTY_POINTS);
        return extendedUser;
    }

    @Before
    public void initTest() {
        extendedUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtendedUser() throws Exception {
        int databaseSizeBeforeCreate = extendedUserRepository.findAll().size();

        // Create the ExtendedUser
        restExtendedUserMockMvc.perform(post("/api/extended-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extendedUser)))
            .andExpect(status().isCreated());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeCreate + 1);
        ExtendedUser testExtendedUser = extendedUserList.get(extendedUserList.size() - 1);
        assertThat(testExtendedUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExtendedUser.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testExtendedUser.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testExtendedUser.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testExtendedUser.getLoyaltyPoints()).isEqualTo(DEFAULT_LOYALTY_POINTS);
    }

    @Test
    @Transactional
    public void createExtendedUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extendedUserRepository.findAll().size();

        // Create the ExtendedUser with an existing ID
        extendedUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtendedUserMockMvc.perform(post("/api/extended-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extendedUser)))
            .andExpect(status().isBadRequest());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExtendedUsers() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList
        restExtendedUserMockMvc.perform(get("/api/extended-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extendedUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].loyaltyPoints").value(hasItem(DEFAULT_LOYALTY_POINTS)));
    }
    
    @Test
    @Transactional
    public void getExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get the extendedUser
        restExtendedUserMockMvc.perform(get("/api/extended-users/{id}", extendedUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extendedUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.loyaltyPoints").value(DEFAULT_LOYALTY_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingExtendedUser() throws Exception {
        // Get the extendedUser
        restExtendedUserMockMvc.perform(get("/api/extended-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        int databaseSizeBeforeUpdate = extendedUserRepository.findAll().size();

        // Update the extendedUser
        ExtendedUser updatedExtendedUser = extendedUserRepository.findById(extendedUser.getId()).get();
        // Disconnect from session so that the updates on updatedExtendedUser are not directly saved in db
        em.detach(updatedExtendedUser);
        updatedExtendedUser
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .phone(UPDATED_PHONE)
            .loyaltyPoints(UPDATED_LOYALTY_POINTS);

        restExtendedUserMockMvc.perform(put("/api/extended-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExtendedUser)))
            .andExpect(status().isOk());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeUpdate);
        ExtendedUser testExtendedUser = extendedUserList.get(extendedUserList.size() - 1);
        assertThat(testExtendedUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExtendedUser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testExtendedUser.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testExtendedUser.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testExtendedUser.getLoyaltyPoints()).isEqualTo(UPDATED_LOYALTY_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingExtendedUser() throws Exception {
        int databaseSizeBeforeUpdate = extendedUserRepository.findAll().size();

        // Create the ExtendedUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtendedUserMockMvc.perform(put("/api/extended-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extendedUser)))
            .andExpect(status().isBadRequest());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        int databaseSizeBeforeDelete = extendedUserRepository.findAll().size();

        // Get the extendedUser
        restExtendedUserMockMvc.perform(delete("/api/extended-users/{id}", extendedUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtendedUser.class);
        ExtendedUser extendedUser1 = new ExtendedUser();
        extendedUser1.setId(1L);
        ExtendedUser extendedUser2 = new ExtendedUser();
        extendedUser2.setId(extendedUser1.getId());
        assertThat(extendedUser1).isEqualTo(extendedUser2);
        extendedUser2.setId(2L);
        assertThat(extendedUser1).isNotEqualTo(extendedUser2);
        extendedUser1.setId(null);
        assertThat(extendedUser1).isNotEqualTo(extendedUser2);
    }
}
