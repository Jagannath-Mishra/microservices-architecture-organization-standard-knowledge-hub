/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of knowledge-hub.
 *
 * knowledge-hub project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.knowledge.hub.security.db.api;

import com.knowledge.hub.commons.web.utils.HttpUtils;
import com.knowledge.hub.security.db.service.TokenValidationService;
import com.knowledge.hub.security.token.TokenType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * A REST controller that exposes APIs to retrieve the user profile details.
 *
 * @author Jagannath Mishra
 */
@RestController
public class TokenValidationApi {
    /** Service implementation of type {@link TokenValidationService}. */
    private final TokenValidationService tokenValidationService;

    /**
     * Constructor.
     *
     * @param tokenValidationService
     *      Service instance of type {@link UserDetailsService}.
     */
    public TokenValidationApi(final TokenValidationService tokenValidationService) {
        this.tokenValidationService = tokenValidationService;
    }
    /**
     * This method returns the user details if the token is valid.
     *
     *
     * @return Response Entity containing the user details.
     */
    @ApiOperation(value = "Get user details if the token is valid.",
            nickname = "validateToken",
            tags = {"Users"},
            authorizations = {@Authorization(value = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the details of the user."),
            @ApiResponse(code = 403, message = "You do not have permissions to perform this operation.")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/validateToken")
    public ResponseEntity<UserDetails> validateToken(@NonNull final HttpServletRequest request) {
        final String accessToken = HttpUtils.extractTokenFromAuthorizationHeader(request, TokenType.BEARER);
        final UserDetails userDetails = tokenValidationService.validateToken(accessToken);
        return ResponseEntity.ok(userDetails);
    }
}