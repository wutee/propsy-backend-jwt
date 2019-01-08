package com.propsy.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.propsy.backend.domain.Authority;
import com.propsy.backend.domain.Menu;
import com.propsy.backend.domain.Restaurant;
import com.propsy.backend.domain.User;
import com.propsy.backend.repository.MenuRepository;
import com.propsy.backend.service.UserService;
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
import java.util.Set;

/**
 * REST controller for managing Menu.
 */
@RestController
@RequestMapping("/api")
public class MenuResource {

    private final Logger log = LoggerFactory.getLogger(MenuResource.class);

    private static final String ENTITY_NAME = "menu";

    private final MenuRepository menuRepository;

    private final UserService userService;

    public MenuResource(MenuRepository menuRepository, UserService userService) {
        this.menuRepository = menuRepository;
        this.userService = userService;
    }

    /**
     * POST  /menus : Create a new menu.
     *
     * @param menu the menu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menu, or with status 400 (Bad Request) if the menu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menus")
    @Timed
    public ResponseEntity<Menu> createMenu(@Valid @RequestBody Menu menu) throws URISyntaxException {
        log.debug("REST request to save Menu : {}", menu);

        Long menuId = menu.getId();
        if (menuId != null) {
            throw new BadRequestAlertException("A new menu cannot already have an ID", ENTITY_NAME, "idexists");
        }

        final Optional<User> isUser = userService.getUserWithAuthorities();

        if(!isUser.isPresent()) {
            log.error("User is not logged in");
            return ResponseEntity.badRequest().build();
        }

        final User user = isUser.get();
        String userRole = getRole(user.getAuthorities());

        Restaurant restaurant = menu.getRestaurant();
        Long menuOwnerId = null;

        if(restaurant != null)
            menuOwnerId = restaurant.getWorker().getId();

        if(userRole.equals("ROLE_ADMIN") || menuOwnerId == null || menuOwnerId.equals(user.getId())) {
            Menu result = menuRepository.save(menu);
            return ResponseEntity.created(new URI("/api/menus/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        }

        throw new BadRequestAlertException("Cannot create menu for a restaurant owned by a different user",
            ENTITY_NAME,
            "forbidden");
    }

    /**
     * PUT  /menus : Updates an existing menu.
     *
     * @param menu the menu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menu,
     * or with status 400 (Bad Request) if the menu is not valid,
     * or with status 500 (Internal Server Error) if the menu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menus")
    @Timed
    public ResponseEntity<Menu> updateMenu(@Valid @RequestBody Menu menu) throws URISyntaxException {
        log.debug("REST request to update Menu : {}", menu);

        Long menuId = menu.getId();
        if (menuId == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        final Optional<User> isUser = userService.getUserWithAuthorities();

        if(!isUser.isPresent()) {
            log.error("User is not logged in");
            return ResponseEntity.badRequest().build();
        }

        final User user = isUser.get();
        String userRole = getRole(user.getAuthorities());

        final Optional<Menu> isMenu = menuRepository.findById(menuId);
        if(!isMenu.isPresent()) {
            log.error("Menu doesn't exist");
            return ResponseEntity.badRequest().build();
        }

        final Menu dbMenu = isMenu.get();

        Restaurant restaurant = dbMenu.getRestaurant();
        Long menuOwnerId = null;

        if(restaurant != null)
            menuOwnerId = restaurant.getWorker().getId();

        if(userRole.equals("ROLE_ADMIN") || menuOwnerId == null || menuOwnerId.equals(user.getId())) {
            Menu result = menuRepository.save(menu);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menu.getId().toString()))
                .body(result);
        }

        throw new BadRequestAlertException("Cannot update menu from a restaurant owned by a different user",
            ENTITY_NAME,
            "forbidden");
    }

    /**
     * GET  /menus : get all the menus.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of menus in body
     */
    @GetMapping("/menus")
    @Timed
    public List<Menu> getAllMenus(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Menus");
        return menuRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /menus/:id : get the "id" menu.
     *
     * @param id the id of the menu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menu, or with status 404 (Not Found)
     */
    @GetMapping("/menus/{id}")
    @Timed
    public ResponseEntity<Menu> getMenu(@PathVariable Long id) {
        log.debug("REST request to get Menu : {}", id);
        Optional<Menu> menu = menuRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(menu);
    }

    /**
     * DELETE  /menus/:id : delete the "id" menu.
     *
     * @param id the id of the menu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        log.debug("REST request to delete Menu : {}", id);

        final Optional<User> isUser = userService.getUserWithAuthorities();

        if(!isUser.isPresent()) {
            log.error("User is not logged in");
            return ResponseEntity.badRequest().build();
        }

        final User user = isUser.get();
        String userRole = getRole(user.getAuthorities());

        final Optional<Menu> isMenu = menuRepository.findById(id);
        if(!isMenu.isPresent()) {
            log.error("Menu doesnt exist");
            return ResponseEntity.badRequest().build();
        }

        final Menu menu = isMenu.get();

        Restaurant restaurant = menu.getRestaurant();
        Long menuOwnerId = null;

        if(restaurant != null)
            menuOwnerId = restaurant.getWorker().getId();

        if(userRole.equals("ROLE_ADMIN") || menuOwnerId == null || menuOwnerId.equals(user.getId())) {
            menuRepository.deleteById(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        }

        throw new BadRequestAlertException("Cannot delete menu from a restaurant owned by a different user",
            ENTITY_NAME,
            "forbidden");
    }

    private String getRole(Set<Authority> authorities) {
        String userRole = "";
        for(Authority authority : authorities) {
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
