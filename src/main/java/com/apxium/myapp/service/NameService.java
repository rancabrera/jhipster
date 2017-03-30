package com.apxium.myapp.service;

import com.apxium.myapp.service.dto.NameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Name.
 */
public interface NameService {

    /**
     * Save a name.
     *
     * @param nameDTO the entity to save
     * @return the persisted entity
     */
    NameDTO save(NameDTO nameDTO);

    /**
     *  Get all the names.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NameDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" name.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NameDTO findOne(Long id);

    /**
     *  Delete the "id" name.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
