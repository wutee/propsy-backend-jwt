package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.ExtendedUser;
import com.propsy.backend.repository.ExtendedUserRepository;
import com.propsy.backend.web.rest.errors.BadRequestAlertException;
import com.propsy.backend.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExtendedUser.
 */
@RestController
@RequestMapping("/api")
public class ExtendedUserResource {

    private final Logger log = LoggerFactory.getLogger(ExtendedUserResource.class);

    private static final String ENTITY_NAME = "extendedUser";

    private final ExtendedUserRepository extendedUserRepository;

    public ExtendedUserResource(ExtendedUserRepository extendedUserRepository) {
        this.extendedUserRepository = extendedUserRepository;
    }

    /**
     * POST  /extended-users : Create a new extendedUser.
     *
     * @param extendedUser the extendedUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extendedUser, or with status 400 (Bad Request) if the extendedUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extended-users")
    @Timed
    public ResponseEntity<ExtendedUser> createExtendedUser(@Valid @RequestBody ExtendedUser extendedUser) throws URISyntaxException {
        log.debug("REST request to save ExtendedUser : {}", extendedUser);
        if (extendedUser.getId() != null) {
            throw new BadRequestAlertException("A new extendedUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExtendedUser result = extendedUserRepository.save(extendedUser);
        return ResponseEntity.created(new URI("/api/extended-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extended-users : Updates an existing extendedUser.
     *
     * @param extendedUser the extendedUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extendedUser,
     * or with status 400 (Bad Request) if the extendedUser is not valid,
     * or with status 500 (Internal Server Error) if the extendedUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extended-users")
    @Timed
    public ResponseEntity<ExtendedUser> updateExtendedUser(@Valid @RequestBody ExtendedUser extendedUser) throws URISyntaxException {
        log.debug("REST request to update ExtendedUser : {}", extendedUser);
        if (extendedUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExtendedUser result = extendedUserRepository.save(extendedUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, extendedUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extended-users : get all the extendedUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of extendedUsers in body
     */
    @GetMapping("/extended-users")
    @Timed
    public List<ExtendedUser> getAllExtendedUsers() {
        log.debug("REST request to get all ExtendedUsers");
        return extendedUserRepository.findAll();
    }

    /**
     * GET  /extended-users/:id : get the "id" extendedUser.
     *
     * @param id the id of the extendedUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extendedUser, or with status 404 (Not Found)
     */
    @GetMapping("/extended-users/{id}")
    @Timed
    public ResponseEntity<ExtendedUser> getExtendedUser(@PathVariable Long id) {
        log.debug("REST request to get ExtendedUser : {}", id);
        Optional<ExtendedUser> extendedUser = extendedUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(extendedUser);
    }

    /**
     * DELETE  /extended-users/:id : delete the "id" extendedUser.
     *
     * @param id the id of the extendedUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extended-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteExtendedUser(@PathVariable Long id) {
        log.debug("REST request to delete ExtendedUser : {}", id);

        extendedUserRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
