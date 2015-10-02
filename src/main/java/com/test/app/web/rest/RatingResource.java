package com.test.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.app.domain.Rating;
import com.test.app.repository.RatingRepository;
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
 * REST controller for managing RatingDTO.
 */
@RestController
@RequestMapping("/api")
public class RatingResource {

    private final Logger log = LoggerFactory.getLogger(RatingResource.class);

    @Inject
    private RatingRepository ratingDTORepository;

    /**
     * POST  /ratingDTOs -> Create a new ratingDTO.
     */
    @RequestMapping(value = "/ratingDTOs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rating> create(@RequestBody Rating ratingDTO) throws URISyntaxException {
        log.debug("REST request to save RatingDTO : {}", ratingDTO);
        if (ratingDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new ratingDTO cannot already have an ID").body(null);
        }
        Rating result = ratingDTORepository.save(ratingDTO);
        return ResponseEntity.created(new URI("/api/ratingDTOs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("ratingDTO", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /ratingDTOs -> Updates an existing ratingDTO.
     */
    @RequestMapping(value = "/ratingDTOs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rating> update(@RequestBody Rating ratingDTO) throws URISyntaxException {
        log.debug("REST request to update RatingDTO : {}", ratingDTO);
        if (ratingDTO.getId() == null) {
            return create(ratingDTO);
        }
        Rating result = ratingDTORepository.save(ratingDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("ratingDTO", ratingDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /ratingDTOs -> get all the ratingDTOs.
     */
    @RequestMapping(value = "/ratingDTOs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Rating>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Rating> page = ratingDTORepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ratingDTOs", offset, limit);
        return new ResponseEntity<List<Rating>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ratingDTOs/:id -> get the "id" ratingDTO.
     */
    @RequestMapping(value = "/ratingDTOs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rating> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get RatingDTO : {}", id);
        Rating ratingDTO = ratingDTORepository.findOne(id);
        if (ratingDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
    }

    /**
     * DELETE  /ratingDTOs/:id -> delete the "id" ratingDTO.
     */
    @RequestMapping(value = "/ratingDTOs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete RatingDTO : {}", id);
        ratingDTORepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ratingDTO", id.toString())).build();
    }
}
