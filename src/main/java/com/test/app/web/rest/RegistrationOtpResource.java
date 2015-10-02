package com.test.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.app.domain.RegistrationOtp;
import com.test.app.repository.RegistrationOtpRepository;
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
 * REST controller for managing RegistrationOtp.
 */
@RestController
@RequestMapping("/api")
public class RegistrationOtpResource {

    private final Logger log = LoggerFactory.getLogger(RegistrationOtpResource.class);

    @Inject
    private RegistrationOtpRepository registrationOtpRepository;

    /**
     * POST  /registrationOtps -> Create a new registrationOtp.
     */
    @RequestMapping(value = "/registrationOtps",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegistrationOtp> create(@RequestBody RegistrationOtp registrationOtp) throws URISyntaxException {
        log.debug("REST request to save RegistrationOtp : {}", registrationOtp);
        if (registrationOtp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new registrationOtp cannot already have an ID").body(null);
        }
        RegistrationOtp result = registrationOtpRepository.save(registrationOtp);
        return ResponseEntity.created(new URI("/api/registrationOtps/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("registrationOtp", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /registrationOtps -> Updates an existing registrationOtp.
     */
    @RequestMapping(value = "/registrationOtps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegistrationOtp> update(@RequestBody RegistrationOtp registrationOtp) throws URISyntaxException {
        log.debug("REST request to update RegistrationOtp : {}", registrationOtp);
        if (registrationOtp.getId() == null) {
            return create(registrationOtp);
        }
        RegistrationOtp result = registrationOtpRepository.save(registrationOtp);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("registrationOtp", registrationOtp.getId().toString()))
                .body(result);
    }

    /**
     * GET  /registrationOtps -> get all the registrationOtps.
     */
    @RequestMapping(value = "/registrationOtps",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RegistrationOtp>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<RegistrationOtp> page = registrationOtpRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/registrationOtps", offset, limit);
        return new ResponseEntity<List<RegistrationOtp>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /registrationOtps/:id -> get the "id" registrationOtp.
     */
    @RequestMapping(value = "/registrationOtps/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegistrationOtp> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get RegistrationOtp : {}", id);
        RegistrationOtp registrationOtp = registrationOtpRepository.findOne(id);
        if (registrationOtp == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(registrationOtp, HttpStatus.OK);
    }

    /**
     * DELETE  /registrationOtps/:id -> delete the "id" registrationOtp.
     */
    @RequestMapping(value = "/registrationOtps/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete RegistrationOtp : {}", id);
        registrationOtpRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("registrationOtp", id.toString())).build();
    }
}
