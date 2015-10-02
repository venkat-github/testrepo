package com.test.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.app.domain.AddressDTO;
import com.test.app.repository.AddressDTORepository;
import com.test.app.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing AddressDTO.
 */
@RestController
@RequestMapping("/api")
public class AddressDTOResource {

    private final Logger log = LoggerFactory.getLogger(AddressDTOResource.class);

    @Inject
    private AddressDTORepository addressDTORepository;

    /**
     * POST  /addressDTOs -> Create a new addressDTO.
     */
    @RequestMapping(value = "/addressDTOs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressDTO> create(@RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to save AddressDTO : {}", addressDTO);
        if (addressDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new addressDTO cannot already have an ID").body(null);
        }
        AddressDTO result = addressDTORepository.save(addressDTO);
        return ResponseEntity.created(new URI("/api/addressDTOs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("addressDTO", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /addressDTOs -> Updates an existing addressDTO.
     */
    @RequestMapping(value = "/addressDTOs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressDTO> update(@RequestBody AddressDTO addressDTO) throws URISyntaxException {
        log.debug("REST request to update AddressDTO : {}", addressDTO);
        if (addressDTO.getId() == null) {
            return create(addressDTO);
        }
        AddressDTO result = addressDTORepository.save(addressDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("addressDTO", addressDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /addressDTOs -> get all the addressDTOs.
     */
    @RequestMapping(value = "/addressDTOs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AddressDTO> getAll() {
        log.debug("REST request to get all AddressDTOs");
        return addressDTORepository.findAll();
    }

    /**
     * GET  /addressDTOs/:id -> get the "id" addressDTO.
     */
    @RequestMapping(value = "/addressDTOs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AddressDTO> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get AddressDTO : {}", id);
        AddressDTO addressDTO = addressDTORepository.findOne(id);
        if (addressDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    /**
     * DELETE  /addressDTOs/:id -> delete the "id" addressDTO.
     */
    @RequestMapping(value = "/addressDTOs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete AddressDTO : {}", id);
        addressDTORepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("addressDTO", id.toString())).build();
    }
}
