/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of knowledge-hub.
 *
 * knowledge-hub project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.knowledge.hub.security.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.knowledge.hub.security.db.model.persistence.UserPrincipalEntity;

import java.util.Optional;

/**
 * Repository contract for {@link UserPrincipalEntity} entity and provides CRUD functionality for the same.
 *
 * @author Jagannath Mishra
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserPrincipalEntity, String> {

    /**
     * This method attempts to find a user based on the provided {@code userName} parameter.
     *
     * @param userName
     *         User name to find.
     *
     * @return Matching user of type {@link UserPrincipalEntity}, else returns null.
     */
    Optional<UserPrincipalEntity> findByUsername(String userName);
}
