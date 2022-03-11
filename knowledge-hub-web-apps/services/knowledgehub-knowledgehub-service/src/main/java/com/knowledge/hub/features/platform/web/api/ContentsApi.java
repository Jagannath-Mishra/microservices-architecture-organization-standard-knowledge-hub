/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.web.api;

import com.knowledge.hub.commons.data.utils.PageUtils;
import com.knowledge.hub.commons.web.api.AbstractApi;
import com.knowledge.hub.commons.web.configuration.properties.ApiDocumentationSettings;
import com.knowledge.hub.features.platform.data.model.experience.contents.Contents;
import com.knowledge.hub.features.platform.data.model.experience.contents.CreateContentsRequest;
import com.knowledge.hub.features.platform.data.model.experience.contents.UpdateContentsRequest;
import com.knowledge.hub.features.platform.web.service.ContentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link
 * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity}.
 *
 * @author Jagannath Mishra
 */
@Slf4j
@RestController
@RequestMapping(ContentsApi.rootEndPoint)
public class ContentsApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Contentses";

    /** Root end point. */
    public static final String rootEndPoint = "/knowledgehub-knowledgehub";

    /** Service implementation of type {@link ContentsService}. */
    private final ContentsService contentsService;

    /**
     * Constructor.
     *
     * @param contentsService Service instance of type {@link ContentsService}.
     */
    public ContentsApi(final ContentsService contentsService) {
        this.contentsService = contentsService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Contents}.
     */
    @Operation(
            method = "createContents",
            summary = "Create a new Contents.",
            description = "This API is used to create a new Contents in the system.",
            tags = {ContentsApi.API_TAG},
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
                        description = "Successfully created a new Contents in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/courseses/{coursesId}/contentses")
    public ResponseEntity<Contents> createContents(
            @PathVariable(name = "coursesId") final String coursesId,
            @Valid @RequestBody final CreateContentsRequest payload) {
        // Delegate to the service layer.
        final Contents newInstance = contentsService.createContents(coursesId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity} in the system.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Contents, which needs to
     *     be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Contents}.
     */
    @Operation(
            method = "updateContents",
            summary = "Update an existing Contents.",
            description = "This API is used to update an existing Contents in the system.",
            tags = {ContentsApi.API_TAG},
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
                        description = "Successfully updated an existing Contents in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/courseses/{coursesId}/contentses/{contentsId}")
    public ResponseEntity<Contents> updateContents(
            @PathVariable(name = "coursesId") final String coursesId,
            @PathVariable(name = "contentsId") final String contentsId,
            @Valid @RequestBody final UpdateContentsRequest payload) {
        // Delegate to the service layer.
        final Contents updatedInstance =
                contentsService.updateContents(coursesId, contentsId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity} in the system.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Contents}.
     */
    @Operation(
            method = "findContents",
            summary = "Find an existing Contents.",
            description = "This API is used to find an existing Contents in the system.",
            tags = {ContentsApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Contents in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/courseses/{coursesId}/contentses/{contentsId}")
    public ResponseEntity<Contents> findContents(
            @PathVariable(name = "coursesId") final String coursesId,
            @PathVariable(name = "contentsId") final String contentsId) {
        // Delegate to the service layer.
        final Contents matchingInstance = contentsService.findContents(coursesId, contentsId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity} in the system in a
     * paginated manner.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Contents based on the provided pagination settings.
     */
    @Operation(
            method = "findAllContentses",
            summary = "Find all Contentses.",
            description = "This API is used to find all Contentses in the system.",
            tags = {ContentsApi.API_TAG},
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
                                "Successfully retrieved the Contentses in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/courseses/{coursesId}/contentses")
    public ResponseEntity<Page<Contents>> findAllContentses(
            @PathVariable(name = "coursesId") final String coursesId,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Contents> matchingInstances =
                contentsService.findAllContentses(coursesId, pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity} in the system.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity} that
     *     was deleted from the system.
     */
    @Operation(
            method = "deleteContents",
            summary = "Delete an existing Contents.",
            description = "This API is used to delete an existing Contents in the system.",
            tags = {ContentsApi.API_TAG},
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
                        description = "Successfully deleted an existing Contents in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/courseses/{coursesId}/contentses/{contentsId}")
    public ResponseEntity<String> deleteContents(
            @PathVariable(name = "coursesId") final String coursesId,
            @PathVariable(name = "contentsId") final String contentsId) {
        // Delegate to the service layer.
        final String deletedInstance = contentsService.deleteContents(coursesId, contentsId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
