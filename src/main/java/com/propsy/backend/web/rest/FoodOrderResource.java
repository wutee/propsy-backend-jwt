package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.FoodOrder;
import com.propsy.backend.repository.FoodOrderRepository;
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
 * REST controller for managing FoodOrder.
 */
@RestController
@RequestMapping("/api")
public class FoodOrderResource {

    private final Logger log = LoggerFactory.getLogger(FoodOrderResource.class);

    private static final String ENTITY_NAME = "foodOrder";

    private final FoodOrderRepository foodOrderRepository;

    public FoodOrderResource(FoodOrderRepository foodOrderRepository) {
        this.foodOrderRepository = foodOrderRepository;
    }

    /**
     * POST  /food-orders : Create a new foodOrder.
     *
     * @param foodOrder the foodOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foodOrder, or with status 400 (Bad Request) if the foodOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/food-orders")
    @Timed
    public ResponseEntity<FoodOrder> createFoodOrder(@Valid @RequestBody FoodOrder foodOrder) throws URISyntaxException {
        log.debug("REST request to save FoodOrder : {}", foodOrder);
        if (foodOrder.getId() != null) {
            throw new BadRequestAlertException("A new foodOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodOrder result = foodOrderRepository.save(foodOrder);
        return ResponseEntity.created(new URI("/api/food-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /food-orders : Updates an existing foodOrder.
     *
     * @param foodOrder the foodOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foodOrder,
     * or with status 400 (Bad Request) if the foodOrder is not valid,
     * or with status 500 (Internal Server Error) if the foodOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/food-orders")
    @Timed
    public ResponseEntity<FoodOrder> updateFoodOrder(@Valid @RequestBody FoodOrder foodOrder) throws URISyntaxException {
        log.debug("REST request to update FoodOrder : {}", foodOrder);
        if (foodOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodOrder result = foodOrderRepository.save(foodOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, foodOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /food-orders : get all the foodOrders.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of foodOrders in body
     */
    @GetMapping("/food-orders")
    @Timed
    public List<FoodOrder> getAllFoodOrders(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all FoodOrders");
        return foodOrderRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /food-orders/:id : get the "id" foodOrder.
     *
     * @param id the id of the foodOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foodOrder, or with status 404 (Not Found)
     */
    @GetMapping("/food-orders/{id}")
    @Timed
    public ResponseEntity<FoodOrder> getFoodOrder(@PathVariable Long id) {
        log.debug("REST request to get FoodOrder : {}", id);
        Optional<FoodOrder> foodOrder = foodOrderRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(foodOrder);
    }

    /**
     * DELETE  /food-orders/:id : delete the "id" foodOrder.
     *
     * @param id the id of the foodOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/food-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoodOrder(@PathVariable Long id) {
        log.debug("REST request to delete FoodOrder : {}", id);

        foodOrderRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
