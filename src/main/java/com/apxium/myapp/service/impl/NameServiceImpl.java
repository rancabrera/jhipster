package com.apxium.myapp.service.impl;

import com.apxium.myapp.service.NameService;
import com.apxium.myapp.domain.Name;
import com.apxium.myapp.repository.NameRepository;
import com.apxium.myapp.service.dto.NameDTO;
import com.apxium.myapp.service.mapper.NameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Name.
 */
@Service
@Transactional
public class NameServiceImpl implements NameService{

    private final Logger log = LoggerFactory.getLogger(NameServiceImpl.class);
    
    private final NameRepository nameRepository;

    private final NameMapper nameMapper;

    public NameServiceImpl(NameRepository nameRepository, NameMapper nameMapper) {
        this.nameRepository = nameRepository;
        this.nameMapper = nameMapper;
    }

    /**
     * Save a name.
     *
     * @param nameDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NameDTO save(NameDTO nameDTO) {
        log.debug("Request to save Name : {}", nameDTO);
        Name name = nameMapper.nameDTOToName(nameDTO);
        name = nameRepository.save(name);
        NameDTO result = nameMapper.nameToNameDTO(name);
        return result;
    }

    /**
     *  Get all the names.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Names");
        Page<Name> result = nameRepository.findAll(pageable);
        return result.map(name -> nameMapper.nameToNameDTO(name));
    }

    /**
     *  Get one name by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NameDTO findOne(Long id) {
        log.debug("Request to get Name : {}", id);
        Name name = nameRepository.findOne(id);
        NameDTO nameDTO = nameMapper.nameToNameDTO(name);
        return nameDTO;
    }

    /**
     *  Delete the  name by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Name : {}", id);
        nameRepository.delete(id);
    }
}
