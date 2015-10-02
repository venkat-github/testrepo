package com.test.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.app.domain.Doctor;
import com.test.app.repository.DoctorRepository;
import com.test.app.web.rest.util.HeaderUtil;
import com.test.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing DoctorDTO.
 */
@RestController
@RequestMapping("/api")
public class DoctorResource {

    private final Logger log = LoggerFactory.getLogger(DoctorResource.class);

    @Inject
    private DoctorRepository doctorDTORepository;

    /**
     * POST  /doctorDTOs -> Create a new doctorDTO.
     */
    @RequestMapping(value = "/doctorDTOs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Doctor> create(@Valid @RequestBody Doctor doctorDTO) throws URISyntaxException {
        log.debug("REST request to save DoctorDTO : {}", doctorDTO);
        if (doctorDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new doctorDTO cannot already have an ID").body(null);
        }
        Doctor result = doctorDTORepository.save(doctorDTO);
        return ResponseEntity.created(new URI("/api/doctorDTOs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("doctorDTO", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /doctorDTOs -> Updates an existing doctorDTO.
     */
    @RequestMapping(value = "/doctorDTOs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Doctor> update(@Valid @RequestBody Doctor doctorDTO) throws URISyntaxException {
        log.debug("REST request to update DoctorDTO : {}", doctorDTO);
        if (doctorDTO.getId() == null) {
            return create(doctorDTO);
        }
        Doctor result = doctorDTORepository.save(doctorDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("doctorDTO", doctorDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /doctorDTOs -> get all the doctorDTOs.
     */
    @RequestMapping(value = "/doctorDTOs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Doctor>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Doctor> page = doctorDTORepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctorDTOs", offset, limit);
        return new ResponseEntity<List<Doctor>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /doctorDTOs/:id -> get the "id" doctorDTO.
     */
    @RequestMapping(value = "/doctorDTOs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Doctor> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get DoctorDTO : {}", id);
        Doctor doctorDTO = doctorDTORepository.findOne(id);
        if (doctorDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }

    /**
     * DELETE  /doctorDTOs/:id -> delete the "id" doctorDTO.
     */
    @RequestMapping(value = "/doctorDTOs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete DoctorDTO : {}", id);
        doctorDTORepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("doctorDTO", id.toString())).build();
    }
}
