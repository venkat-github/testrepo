package com.test.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.app.domain.TestBlob;
import com.test.app.repository.TestBlobRepository;
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
 * REST controller for managing TestBlob.
 */
@RestController
@RequestMapping("/api")
public class TestBlobResource {

    private final Logger log = LoggerFactory.getLogger(TestBlobResource.class);

    @Inject
    private TestBlobRepository testBlobRepository;

    /**
     * POST  /testBlobs -> Create a new testBlob.
     */
    @RequestMapping(value = "/testBlobs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestBlob> create(@RequestBody TestBlob testBlob) throws URISyntaxException {
        log.debug("REST request to save TestBlob : {}", testBlob);
        if (testBlob.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new testBlob cannot already have an ID").body(null);
        }
        TestBlob result = testBlobRepository.save(testBlob);
        return ResponseEntity.created(new URI("/api/testBlobs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("testBlob", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /testBlobs -> Updates an existing testBlob.
     */
    @RequestMapping(value = "/testBlobs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestBlob> update(@RequestBody TestBlob testBlob) throws URISyntaxException {
        log.debug("REST request to update TestBlob : {}", testBlob);
        if (testBlob.getId() == null) {
            return create(testBlob);
        }
        TestBlob result = testBlobRepository.save(testBlob);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("testBlob", testBlob.getId().toString()))
                .body(result);
    }

    /**
     * GET  /testBlobs -> get all the testBlobs.
     */
    @RequestMapping(value = "/testBlobs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TestBlob>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<TestBlob> page = testBlobRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/testBlobs", offset, limit);
        return new ResponseEntity<List<TestBlob>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /testBlobs/:id -> get the "id" testBlob.
     */
    @RequestMapping(value = "/testBlobs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestBlob> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get TestBlob : {}", id);
        TestBlob testBlob = testBlobRepository.findOne(id);
        if (testBlob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(testBlob, HttpStatus.OK);
    }

    /**
     * DELETE  /testBlobs/:id -> delete the "id" testBlob.
     */
    @RequestMapping(value = "/testBlobs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete TestBlob : {}", id);
        testBlobRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testBlob", id.toString())).build();
    }
}
