package com.test.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.test.app.domain.DoctorVisit;
import com.test.app.domain.User;
import com.test.app.repository.DoctorVisitRepository;
import com.test.app.service.UserService;
import com.test.app.web.rest.util.HeaderUtil;
import com.test.app.web.rest.util.PaginationUtil;

/**
 * REST controller for managing DoctorVisitDTO.
 */
@RestController
@RequestMapping("/api")
public class DoctorVisitResource {

    private final Logger log = LoggerFactory.getLogger(DoctorVisitResource.class);

    @Inject
    private DoctorVisitRepository doctorVisitDTORepository;

    @Inject
    DoctorVisitRepository repo;
    
    @RequestMapping(value = "/doctorVisitDTOs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DoctorVisit> create(@RequestBody DoctorVisit doctorVisitDTO) throws URISyntaxException {
        log.debug("REST request to save DoctorVisitDTO : {}", doctorVisitDTO);
        if (doctorVisitDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new doctorVisitDTO cannot already have an ID").body(null);
        }
        /*
        DoctorVisit dto =  repo.findOne(doctorVisitDTO.getConsultId());
        dto.getFreeSlots().remove(doctorVisitDTO.getSlot());
        dto.getOccupiedSlots().add(doctorVisitDTO.getSlot());
        repo.save(dto);
        */
        
        System.out.println("changed the occpied slots ");
        DoctorVisit result = doctorVisitDTORepository.save(doctorVisitDTO);
        return ResponseEntity.created(new URI("/api/doctorVisitDTOs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("doctorVisitDTO", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /doctorVisitDTOs -> Updates an existing doctorVisitDTO.
     */
    @RequestMapping(value = "/doctorVisitDTOs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DoctorVisit> update(@RequestBody DoctorVisit doctorVisitDTO) throws URISyntaxException {
        log.debug("REST request to update DoctorVisitDTO : {}", doctorVisitDTO);
        if (doctorVisitDTO.getId() == null) {
            return create(doctorVisitDTO);
        }
        DoctorVisit result = doctorVisitDTORepository.save(doctorVisitDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("doctorVisitDTO", doctorVisitDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /doctorVisitDTOs -> get all the doctorVisitDTOs.
     */
    @RequestMapping(value = "/doctorVisitDTOs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DoctorVisit>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
    	User user = userService.getUserWithAuthorities();
    	
        Page<DoctorVisit> page = doctorVisitDTORepository.findByDoctorId(user.getId(), PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctorVisitDTOs", offset, limit);
        return new ResponseEntity<List<DoctorVisit>>(page.getContent(), headers, HttpStatus.OK);
    }

    @Inject
    UserService userService;
    
    /**
     * GET  /doctorVisitDTOs/:id -> get the "id" doctorVisitDTO.
     */
    @RequestMapping(value = "/doctorVisitDTOs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DoctorVisit> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get DoctorVisitDTO : {}", id);
        DoctorVisit doctorVisitDTO = doctorVisitDTORepository.findOne(id);
        if (doctorVisitDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctorVisitDTO, HttpStatus.OK);
    }

    /**
     * DELETE  /doctorVisitDTOs/:id -> delete the "id" doctorVisitDTO.
     */
    @RequestMapping(value = "/doctorVisitDTOs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete DoctorVisitDTO : {}", id);
        doctorVisitDTORepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("doctorVisitDTO", id.toString())).build();
    }
}
