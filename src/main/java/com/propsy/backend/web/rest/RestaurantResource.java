package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.Authority;
import com.propsy.backend.domain.Restaurant;
import com.propsy.backend.domain.User;
import com.propsy.backend.repository.RestaurantRepository;
import com.propsy.backend.service.UserService;
import com.propsy.backend.web.rest.errors.BadRequestAlertException;
import com.propsy.backend.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Restaurant.
 */
@RestController
@RequestMapping("/api")
public class RestaurantResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantResource.class);

    private static final String ENTITY_NAME = "restaurant";

    private final RestaurantRepository restaurantRepository;

    private final UserService userService;

    public RestaurantResource(RestaurantRepository restaurantRepository, UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
    }

    /**
     * POST  /restaurants : Create a new restaurant.
     *
     * @param restaurant the restaurant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restaurant, or with status 400 (Bad Request) if the restaurant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restaurants")
    @Timed
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to save Restaurant : {}", restaurant);
        if (restaurant.getId() != null) {
            throw new BadRequestAlertException("A new restaurant cannot already have an ID", ENTITY_NAME, "idexists");
        }

        final Optional<User> isUser = userService.getUserWithAuthorities();

        if(!isUser.isPresent()) {
            log.error("User is not logged in");
            return ResponseEntity.badRequest().build();
        }

        final User user = isUser.get();
        Set<Authority> userAuthorities = user.getAuthorities();

        String userRole = getRole(userAuthorities);

        if(userRole.equals("ROLE_ADMIN") || restaurant.getWorker().getId() == null || restaurant.getWorker().getId().equals(user.getId())) {
            Restaurant result = restaurantRepository.save(restaurant);
            return ResponseEntity.created(new URI("/api/restaurants/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }

        throw new BadRequestAlertException("Cannot create a restaurant owned by a different user", ENTITY_NAME, "forbidden");
    }

    /**
     * PUT  /restaurants : Updates an existing restaurant.
     *
     * @param restaurant the restaurant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restaurant,
     * or with status 400 (Bad Request) if the restaurant is not valid,
     * or with status 500 (Internal Server Error) if the restaurant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restaurants")
    @Timed
    public ResponseEntity<Restaurant> updateRestaurant(@Valid @RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to update Restaurant : {}", restaurant);

        Long restaurantId = restaurant.getId();

        if (restaurantId == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        final Optional<User> isUser = userService.getUserWithAuthorities();

        if(!isUser.isPresent()) {
            log.error("User is not logged in");
            return ResponseEntity.badRequest().build();
        }

        final User user = isUser.get();
        Set<Authority> userAuthorities = user.getAuthorities();

        String userRole = getRole(userAuthorities);

        final Optional<Restaurant> isRestaurant = restaurantRepository.findById(restaurantId);

        if(!isRestaurant.isPresent()) {
            log.error("Restaurant does not exist");
            return ResponseEntity.badRequest().build();
        }

        final Restaurant dbRestaurant = isRestaurant.get();
        Long restaurantOwnerId = dbRestaurant.getWorker().getId();

        if(userRole.equals("ROLE_ADMIN") || restaurantOwnerId.equals(user.getId())) {
            Restaurant result = restaurantRepository.save(restaurant);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restaurant.getId().toString()))
                .body(result);
        }

        throw new BadRequestAlertException("Cannot edit a restaurant owned by a different user", ENTITY_NAME, "forbidden");
    }

    /**
     * GET  /restaurants : get all the restaurants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of restaurants in body
     */
    @GetMapping("/restaurants")
    @Timed
    public List<Restaurant> getAllRestaurants() {
        log.debug("REST request to get all Restaurants");
        return restaurantRepository.findAll();
    }

    /**
     * GET  /restaurants/:id : get the "id" restaurant.
     *
     * @param id the id of the restaurant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restaurant, or with status 404 (Not Found)
     */
    @GetMapping("/restaurants/{id}")
    @Timed
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        log.debug("REST request to get Restaurant : {}", id);
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(restaurant);
    }

    /**
     * DELETE  /restaurants/:id : delete the "id" restaurant.
     *
     * @param id the id of the restaurant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restaurants/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        log.debug("REST request to delete Restaurant : {}", id);

        final Optional<User> isUser = userService.getUserWithAuthorities();

        if(!isUser.isPresent()) {
            log.error("User is not logged in");
            return ResponseEntity.badRequest().build();
        }

        final User user = isUser.get();
        Set<Authority> userAuthorities = user.getAuthorities();

        String userRole = getRole(userAuthorities);

        final Optional<Restaurant> isRestaurant = restaurantRepository.findById(id);

        if(!isRestaurant.isPresent()) {
            log.error("Restaurant does not exist");
            return ResponseEntity.badRequest().build();
        }

        final Restaurant restaurant = isRestaurant.get();
        Long restaurantOwnerId = restaurant.getWorker().getId();

        if(userRole.equals("ROLE_ADMIN") || user.getId().equals(restaurantOwnerId)) {
            restaurantRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                    .build();
        }

        throw new BadRequestAlertException("Cannot delete a restaurant owned by a different user", ENTITY_NAME, "forbidden");
    }


    private String getRole(Set<Authority> userAuthorities) {
        String userRole = "";
        for(Authority authority : userAuthorities) {
            String role = authority.getName();
            if(role.equals("ROLE_ADMIN"))
                userRole = role;
            else if((role.equals("ROLE_CHEF") || role.equals("ROLE_MANAGER")) && !userRole.equals("ROLE_ADMIN"))
                userRole = role;
            else if(role.equals("ROLE_USER") && userRole.equals(""))
                userRole = role;
        }

        return userRole;
    }
}
