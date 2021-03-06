/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of knowledge-hub.
 *
 * knowledge-hub project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.knowledge.hub.security.db.model.experience;

import java.util.Collection;
import java.util.HashSet;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knowledge.hub.security.db.model.persistence.UserPrincipalEntity;
import com.knowledge.hub.security.userdetails.UserPrincipal;

/**
 * A data transfer object that returns the details of the user.
 *
 * @author Jagannath Mishra
 */
@ToString(of = {"username"})
@EqualsAndHashCode(of = {"id","username"})
@Builder
@Data
public class UserProfile extends AbstractUserProfile<String>{
    /** Unique identifier of the user. */
    private String id;

    /** User name. */
    private String username;

    /** Email address of the user. */
    private String email;

    /** User display name. */
    private String displayName;

    /** Collection of role names for the user. */
    private Collection<String> roles = new HashSet<>();

    /**
     * This method transforms the provided user entity object of type {@link UserPrincipalEntity} to an instance of type
     * {@link UserProfile}.
     *
     * @param user
     *         User entity of type {@link UserPrincipalEntity} that needs to be transformed to {@link UserProfile}.
     *
     * @return Instance of type {@link UserProfile}.
     */
    @JsonIgnore
    public static UserProfile from(final UserPrincipalEntity user) {
        // @formatter:off
        return UserProfile.builder()
                          .id(user.getId())
                          .displayName(user.getDisplayName())
                          .username(user.getUsername())
                          .roles(user.getAssignedRoleNames())
                          .build();
        // @formatter:on
    }

    /**
     * This method transforms the provided user principal object of type {@link UserPrincipal} to an instance of type
     * {@link UserProfile}.
     *
     * @param user
     *         User principal of type {@link UserPrincipal} that needs to be transformed to {@link UserProfile}.
     *
     * @return Instance of type {@link UserProfile}.
     */
    @JsonIgnore
    public static UserProfile from(final UserPrincipal user) {
        // @formatter:off
        return UserProfile.builder()
                          .id(user.getId())
                          .displayName(user.getDisplayName())
                          .username(user.getUsername())
                          .roles(user.getAssignedRoles())
                          .build();
        // @formatter:on
    }
}
