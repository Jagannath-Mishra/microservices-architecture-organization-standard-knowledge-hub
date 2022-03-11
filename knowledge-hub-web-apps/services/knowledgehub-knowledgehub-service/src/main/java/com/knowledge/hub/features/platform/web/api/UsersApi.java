/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.web.api;

import com.knowledge.hub.commons.web.api.AbstractApi;
import com.knowledge.hub.commons.web.configuration.properties.ApiDocumentationSettings;
import com.knowledge.hub.features.platform.data.model.experience.users.CreateUsersRequest;
import com.knowledge.hub.features.platform.data.model.experience.users.UpdateUsersRequest;
import com.knowledge.hub.features.platform.data.model.experience.users.Users;
import com.knowledge.hub.features.platform.web.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link
 * com.knowledge.hub.features.platform.data.model.persistence.UsersEntity}.
 *
 * @author Jagannath Mishra
 */
@Slf4j
@RestController
@RequestMapping(UsersApi.rootEndPoint)
public class UsersApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Userses";

    /** Root end point. */
    public static final String rootEndPoint = "/knowledgehub-knowledgehub";

    /** Service implementation of type {@link UsersService}. */
    private final UsersService usersService;

    /**
     * Constructor.
     *
     * @param usersService Service instance of type {@link UsersService}.
     */
    public UsersApi(final UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.UsersEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.knowledge.hub.features.platform.data.model.persistence.UsersEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Users}.
     */
    @Operation(
            method = "createUsers",
            summary = "Create a new Users.",
            description = "This API is used to create a new Users in the system.",
            tags = {UsersApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successfully created a new Users in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/courseses/{coursesId}/contentses/{contentsId}/userses")
    public ResponseEntity<Users> createUsers(
            @PathVariable(name = "coursesId") final String coursesId,
            @PathVariable(name = "contentsId") final String contentsId,
            @Valid @RequestBody final CreateUsersRequest payload) {
        // Delegate to the service layer.
        final Users newInstance = usersService.createUsers(coursesId, contentsId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.UsersEntity} in the system.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @param usersId Unique identifier of Users in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Users, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Users}.
     */
    @Operation(
            method = "updateUsers",
            summary = "Update an existing Users.",
            description = "This API is used to update an existing Users in the system.",
            tags = {UsersApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully updated an existing Users in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/courseses/{coursesId}/contentses/{contentsId}/userses/{usersId}")
    public ResponseEntity<Users> updateUsers(
            @PathVariable(name = "coursesId") final String coursesId,
            @PathVariable(name = "contentsId") final String contentsId,
            @PathVariable(name = "usersId") final String usersId,
            @Valid @RequestBody final UpdateUsersRequest payload) {
        // Delegate to the service layer.
        final Users updatedInstance =
                usersService.updateUsers(coursesId, contentsId, usersId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.knowledge.hub.features.platform.data.model.persistence.UsersEntity} in the system.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @param usersId Unique identifier of Users in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Users}.
     */
    @Operation(
            method = "findUsers",
            summary = "Find an existing Users.",
            description = "This API is used to find an existing Users in the system.",
            tags = {UsersApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the details of an existing Users in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/courseses/{coursesId}/contentses/{contentsId}/userses/{usersId}")
    public ResponseEntity<Users> findUsers(
            @PathVariable(name = "coursesId") final String coursesId,
            @PathVariable(name = "contentsId") final String contentsId,
            @PathVariable(name = "usersId") final String usersId) {
        // Delegate to the service layer.
        final Users matchingInstance = usersService.findUsers(coursesId, contentsId, usersId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.UsersEntity} in the system.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @param usersId Unique identifier of Users in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.knowledge.hub.features.platform.data.model.persistence.UsersEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteUsers",
            summary = "Delete an existing Users.",
            description = "This API is used to delete an existing Users in the system.",
            tags = {UsersApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully deleted an existing Users in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/courseses/{coursesId}/contentses/{contentsId}/userses/{usersId}")
    public ResponseEntity<String> deleteUsers(
            @PathVariable(name = "coursesId") final String coursesId,
            @PathVariable(name = "contentsId") final String contentsId,
            @PathVariable(name = "usersId") final String usersId) {
        // Delegate to the service layer.
        final String deletedInstance = usersService.deleteUsers(coursesId, contentsId, usersId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
