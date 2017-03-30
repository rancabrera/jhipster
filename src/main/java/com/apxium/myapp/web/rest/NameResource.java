package com.apxium.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.apxium.myapp.service.NameService;
import com.apxium.myapp.web.rest.util.HeaderUtil;
import com.apxium.myapp.web.rest.util.PaginationUtil;
import com.apxium.myapp.service.dto.NameDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Name.
 */
@RestController
@RequestMapping("/api")
public class NameResource {

    private final Logger log = LoggerFactory.getLogger(NameResource.class);

    private static final String ENTITY_NAME = "name";
        
    private final NameService nameService;

    public NameResource(NameService nameService) {
        this.nameService = nameService;
    }

    /**
     * POST  /names : Create a new name.
     *
     * @param nameDTO the nameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nameDTO, or with status 400 (Bad Request) if the name has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/names")
    @Timed
    public ResponseEntity<NameDTO> createName(@Valid @RequestBody NameDTO nameDTO) throws URISyntaxException {
        log.debug("REST request to save Name : {}", nameDTO);
        if (nameDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new name cannot already have an ID")).body(null);
        }
        NameDTO result = nameService.save(nameDTO);
        return ResponseEntity.created(new URI("/api/names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /names : Updates an existing name.
     *
     * @param nameDTO the nameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nameDTO,
     * or with status 400 (Bad Request) if the nameDTO is not valid,
     * or with status 500 (Internal Server Error) if the nameDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/names")
    @Timed
    public ResponseEntity<NameDTO> updateName(@Valid @RequestBody NameDTO nameDTO) throws URISyntaxException {
        log.debug("REST request to update Name : {}", nameDTO);
        if (nameDTO.getId() == null) {
            return createName(nameDTO);
        }
        NameDTO result = nameService.save(nameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /names : get all the names.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of names in body
     */
    @GetMapping("/names")
    @Timed
    public ResponseEntity<List<NameDTO>> getAllNames(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Names");
        Page<NameDTO> page = nameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/names");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /names/:id : get the "id" name.
     *
     * @param id the id of the nameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/names/{id}")
    @Timed
    public ResponseEntity<NameDTO> getName(@PathVariable Long id) {
        log.debug("REST request to get Name : {}", id);
        NameDTO nameDTO = nameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nameDTO));
    }

    /**
     * DELETE  /names/:id : delete the "id" name.
     *
     * @param id the id of the nameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/names/{id}")
    @Timed
    public ResponseEntity<Void> deleteName(@PathVariable Long id) {
        log.debug("REST request to delete Name : {}", id);
        nameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
