package com.test.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.app.domain.CheckEntity;
import com.test.app.repository.CheckEntityRepository;
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
 * REST controller for managing CheckEntity.
 */
@RestController
@RequestMapping("/api")
public class CheckEntityResource {

    private final Logger log = LoggerFactory.getLogger(CheckEntityResource.class);

    @Inject
    private CheckEntityRepository checkEntityRepository;

    /**
     * POST  /checkEntitys -> Create a new checkEntity.
     */
    @RequestMapping(value = "/checkEntitys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CheckEntity> create(@Valid @RequestBody CheckEntity checkEntity) throws URISyntaxException {
        log.debug("REST request to save CheckEntity : {}", checkEntity);
        if (checkEntity.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new checkEntity cannot already have an ID").body(null);
        }
        CheckEntity result = checkEntityRepository.save(checkEntity);
        return ResponseEntity.created(new URI("/api/checkEntitys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("checkEntity", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /checkEntitys -> Updates an existing checkEntity.
     */
    @RequestMapping(value = "/checkEntitys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CheckEntity> update(@Valid @RequestBody CheckEntity checkEntity) throws URISyntaxException {
        log.debug("REST request to update CheckEntity : {}", checkEntity);
        if (checkEntity.getId() == null) {
            return create(checkEntity);
        }
        CheckEntity result = checkEntityRepository.save(checkEntity);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("checkEntity", checkEntity.getId().toString()))
                .body(result);
    }

    /**
     * GET  /checkEntitys -> get all the checkEntitys.
     */
    @RequestMapping(value = "/checkEntitys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CheckEntity>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<CheckEntity> page = checkEntityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/checkEntitys", offset, limit);
        return new ResponseEntity<List<CheckEntity>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /checkEntitys/:id -> get the "id" checkEntity.
     */
    @RequestMapping(value = "/checkEntitys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CheckEntity> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get CheckEntity : {}", id);
        CheckEntity checkEntity = checkEntityRepository.findOne(id);
        if (checkEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(checkEntity, HttpStatus.OK);
    }

    /**
     * DELETE  /checkEntitys/:id -> delete the "id" checkEntity.
     */
    @RequestMapping(value = "/checkEntitys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete CheckEntity : {}", id);
        checkEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("checkEntity", id.toString())).build();
    }
}
