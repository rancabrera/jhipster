package com.apxium.myapp.web.rest;

import com.apxium.myapp.MyApp;

import com.apxium.myapp.domain.Name;
import com.apxium.myapp.repository.NameRepository;
import com.apxium.myapp.service.NameService;
import com.apxium.myapp.service.dto.NameDTO;
import com.apxium.myapp.service.mapper.NameMapper;
import com.apxium.myapp.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NameResource REST controller.
 *
 * @see NameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class NameResourceIntTest {

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private NameMapper nameMapper;

    @Autowired
    private NameService nameService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNameMockMvc;

    private Name name;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NameResource nameResource = new NameResource(nameService);
        this.restNameMockMvc = MockMvcBuilders.standaloneSetup(nameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Name createEntity(EntityManager em) {
        Name name = new Name()
            .surname(DEFAULT_SURNAME)
            .firstName(DEFAULT_FIRST_NAME)
            .startDate(DEFAULT_START_DATE);
        return name;
    }

    @Before
    public void initTest() {
        name = createEntity(em);
    }

    @Test
    @Transactional
    public void createName() throws Exception {
        int databaseSizeBeforeCreate = nameRepository.findAll().size();

        // Create the Name
        NameDTO nameDTO = nameMapper.nameToNameDTO(name);
        restNameMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameDTO)))
            .andExpect(status().isCreated());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeCreate + 1);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testName.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testName.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    public void createNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nameRepository.findAll().size();

        // Create the Name with an existing ID
        name.setId(1L);
        NameDTO nameDTO = nameMapper.nameToNameDTO(name);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNameMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nameRepository.findAll().size();
        // set the field null
        name.setSurname(null);

        // Create the Name, which fails.
        NameDTO nameDTO = nameMapper.nameToNameDTO(name);

        restNameMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameDTO)))
            .andExpect(status().isBadRequest());

        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nameRepository.findAll().size();
        // set the field null
        name.setFirstName(null);

        // Create the Name, which fails.
        NameDTO nameDTO = nameMapper.nameToNameDTO(name);

        restNameMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameDTO)))
            .andExpect(status().isBadRequest());

        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = nameRepository.findAll().size();
        // set the field null
        name.setStartDate(null);

        // Create the Name, which fails.
        NameDTO nameDTO = nameMapper.nameToNameDTO(name);

        restNameMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameDTO)))
            .andExpect(status().isBadRequest());

        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNames() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get all the nameList
        restNameMockMvc.perform(get("/api/names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(name.getId().intValue())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())));
    }

    @Test
    @Transactional
    public void getName() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get the name
        restNameMockMvc.perform(get("/api/names/{id}", name.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(name.getId().intValue()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingName() throws Exception {
        // Get the name
        restNameMockMvc.perform(get("/api/names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateName() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();

        // Update the name
        Name updatedName = nameRepository.findOne(name.getId());
        updatedName
            .surname(UPDATED_SURNAME)
            .firstName(UPDATED_FIRST_NAME)
            .startDate(UPDATED_START_DATE);
        NameDTO nameDTO = nameMapper.nameToNameDTO(updatedName);

        restNameMockMvc.perform(put("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameDTO)))
            .andExpect(status().isOk());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testName.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testName.getStartDate()).isEqualTo(UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingName() throws Exception {
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();

        // Create the Name
        NameDTO nameDTO = nameMapper.nameToNameDTO(name);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNameMockMvc.perform(put("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameDTO)))
            .andExpect(status().isCreated());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteName() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);
        int databaseSizeBeforeDelete = nameRepository.findAll().size();

        // Get the name
        restNameMockMvc.perform(delete("/api/names/{id}", name.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Name.class);
    }
}
