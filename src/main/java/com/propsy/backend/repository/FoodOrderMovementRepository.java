package com.propsy.backend.repository;

import com.propsy.backend.domain.FoodOrderMovement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FoodOrderMovement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodOrderMovementRepository extends JpaRepository<FoodOrderMovement, Long> {

}
