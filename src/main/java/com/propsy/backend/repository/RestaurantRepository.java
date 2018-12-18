package com.propsy.backend.repository;

import com.propsy.backend.domain.Restaurant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Restaurant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select restaurant from Restaurant restaurant where restaurant.worker.login = ?#{principal.username}")
    List<Restaurant> findByWorkerIsCurrentUser();

}
