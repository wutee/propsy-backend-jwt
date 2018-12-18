package com.propsy.backend.repository;

import com.propsy.backend.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Menu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(value = "select distinct menu from Menu menu left join fetch menu.foodItems",
        countQuery = "select count(distinct menu) from Menu menu")
    Page<Menu> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct menu from Menu menu left join fetch menu.foodItems")
    List<Menu> findAllWithEagerRelationships();

    @Query("select menu from Menu menu left join fetch menu.foodItems where menu.id =:id")
    Optional<Menu> findOneWithEagerRelationships(@Param("id") Long id);

}
