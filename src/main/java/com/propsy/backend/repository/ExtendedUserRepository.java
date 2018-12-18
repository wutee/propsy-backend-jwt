package com.propsy.backend.repository;

import com.propsy.backend.domain.ExtendedUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExtendedUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtendedUserRepository extends JpaRepository<ExtendedUser, Long> {

}
