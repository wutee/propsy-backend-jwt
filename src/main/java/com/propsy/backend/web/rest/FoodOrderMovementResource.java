package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.FoodOrderMovement;
import com.propsy.backend.repository.FoodOrderMovementRepository;
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
 * REST controller for managing FoodOrderMovement.
 */
@RestController
@RequestMapping("/api")
public class FoodOrderMovementResource {

    private final Logger log = LoggerFactory.getLogger(FoodOrderMovementResource.class);

    private static final String ENTITY_NAME = "foodOrderMovement";

    private final FoodOrderMovementRepository foodOrderMovementRepository;

    public FoodOrderMovementResource(FoodOrderMovementRepository foodOrderMovementRepository) {
        this.foodOrderMovementRepository = foodOrderMovementRepository;
    }

    /**
     * POST  /food-order-movements : Create a new foodOrderMovement.
     *
     * @param foodOrderMovement the foodOrderMovement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foodOrderMovement, or with status 400 (Bad Request) if the foodOrderMovement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/food-order-movements")
    @Timed
    public ResponseEntity<FoodOrderMovement> createFoodOrderMovement(@Valid @RequestBody FoodOrderMovement foodOrderMovement) throws URISyntaxException {
        log.debug("REST request to save FoodOrderMovement : {}", foodOrderMovement);
        if (foodOrderMovement.getId() != null) {
            throw new BadRequestAlertException("A new foodOrderMovement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodOrderMovement result = foodOrderMovementRepository.save(foodOrderMovement);
        return ResponseEntity.created(new URI("/api/food-order-movements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /food-order-movements : Updates an existing foodOrderMovement.
     *
     * @param foodOrderMovement the foodOrderMovement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foodOrderMovement,
     * or with status 400 (Bad Request) if the foodOrderMovement is not valid,
     * or with status 500 (Internal Server Error) if the foodOrderMovement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/food-order-movements")
    @Timed
    public ResponseEntity<FoodOrderMovement> updateFoodOrderMovement(@Valid @RequestBody FoodOrderMovement foodOrderMovement) throws URISyntaxException {
        log.debug("REST request to update FoodOrderMovement : {}", foodOrderMovement);
        if (foodOrderMovement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodOrderMovement result = foodOrderMovementRepository.save(foodOrderMovement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, foodOrderMovement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /food-order-movements : get all the foodOrderMovements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of foodOrderMovements in body
     */
    @GetMapping("/food-order-movements")
    @Timed
    public List<FoodOrderMovement> getAllFoodOrderMovements() {
        log.debug("REST request to get all FoodOrderMovements");
        return foodOrderMovementRepository.findAll();
    }

    /**
     * GET  /food-order-movements/:id : get the "id" foodOrderMovement.
     *
     * @param id the id of the foodOrderMovement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foodOrderMovement, or with status 404 (Not Found)
     */
    @GetMapping("/food-order-movements/{id}")
    @Timed
    public ResponseEntity<FoodOrderMovement> getFoodOrderMovement(@PathVariable Long id) {
        log.debug("REST request to get FoodOrderMovement : {}", id);
        Optional<FoodOrderMovement> foodOrderMovement = foodOrderMovementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(foodOrderMovement);
    }

    /**
     * DELETE  /food-order-movements/:id : delete the "id" foodOrderMovement.
     *
     * @param id the id of the foodOrderMovement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/food-order-movements/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoodOrderMovement(@PathVariable Long id) {
        log.debug("REST request to delete FoodOrderMovement : {}", id);

        foodOrderMovementRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
