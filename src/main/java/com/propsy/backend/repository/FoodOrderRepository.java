package com.propsy.backend.repository;

import com.propsy.backend.domain.FoodOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the FoodOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    @Query("select food_order from FoodOrder food_order where food_order.deliveryman.login = ?#{principal.username}")
    List<FoodOrder> findByDeliverymanIsCurrentUser();

    @Query("select food_order from FoodOrder food_order where food_order.purchaser.login = ?#{principal.username}")
    List<FoodOrder> findByPurchaserIsCurrentUser();

    @Query(value = "select distinct food_order from FoodOrder food_order left join fetch food_order.foodItems",
        countQuery = "select count(distinct food_order) from FoodOrder food_order")
    Page<FoodOrder> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct food_order from FoodOrder food_order left join fetch food_order.foodItems")
    List<FoodOrder> findAllWithEagerRelationships();

    @Query("select food_order from FoodOrder food_order left join fetch food_order.foodItems where food_order.id =:id")
    Optional<FoodOrder> findOneWithEagerRelationships(@Param("id") Long id);

}
