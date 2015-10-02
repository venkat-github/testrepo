package com.test.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.app.domain.DoctorVisit;
import com.test.app.domain.User;
import com.test.app.domain.UserDoctorVisitRecord;
import com.test.app.repository.UserRecordRepository;
import com.test.app.service.UserService;
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

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * REST controller for managing UserRecordDTO.
 */
@RestController
@RequestMapping("/api")
public class UserDoctorVisitRecordResource {

    private final Logger log = LoggerFactory.getLogger(UserDoctorVisitRecordResource.class);

    @Inject
    private UserRecordRepository userRecordDTORepository;

    @Inject
    UserService userService;

    
    /**
     * POST  /userRecordDTOs -> Create a new userRecordDTO.
     */
    @RequestMapping(value = "/userRecordDTOs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDoctorVisitRecord> create(@RequestBody UserDoctorVisitRecord userRecordDTO) throws URISyntaxException {
        log.debug("REST request to save UserRecordDTO : {}", userRecordDTO);
        if (userRecordDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userRecordDTO cannot already have an ID").body(null);
        }
        UserDoctorVisitRecord result = userRecordDTORepository.save(userRecordDTO);
        return ResponseEntity.created(new URI("/api/userRecordDTOs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("userRecordDTO", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /userRecordDTOs -> Updates an existing userRecordDTO.
     */
    @RequestMapping(value = "/userRecordDTOs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDoctorVisitRecord> update(@RequestBody UserDoctorVisitRecord userRecordDTO) throws URISyntaxException {
        log.debug("REST request to update UserRecordDTO : {}", userRecordDTO);
        if (userRecordDTO.getId() == null) {
            return create(userRecordDTO);
        }
        UserDoctorVisitRecord result = userRecordDTORepository.save(userRecordDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("userRecordDTO", userRecordDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /userRecordDTOs -> get all the userRecordDTOs.
     */
    @RequestMapping(value = "/userRecordDTOs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UserDoctorVisitRecord>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	User user = userService.getUserWithAuthorities();
    	
        Page<UserDoctorVisitRecord> page = userRecordDTORepository.findByUserId(user.getId(), PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userRecordDTOs", offset, limit);
        return new ResponseEntity<List<UserDoctorVisitRecord>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /userRecordDTOs/:id -> get the "id" userRecordDTO.
     */
    @RequestMapping(value = "/userRecordDTOs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDoctorVisitRecord> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get UserRecordDTO : {}", id);
        UserDoctorVisitRecord userRecordDTO = userRecordDTORepository.findOne(id);
        if (userRecordDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userRecordDTO, HttpStatus.OK);
    }

    /**
     * DELETE  /userRecordDTOs/:id -> delete the "id" userRecordDTO.
     */
    @RequestMapping(value = "/userRecordDTOs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete UserRecordDTO : {}", id);
        userRecordDTORepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userRecordDTO", id.toString())).build();
    }
}
