/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.web.api;

import com.knowledge.hub.commons.data.utils.PageUtils;
import com.knowledge.hub.commons.web.api.AbstractApi;
import com.knowledge.hub.commons.web.configuration.properties.ApiDocumentationSettings;
import com.knowledge.hub.features.platform.data.model.experience.courses.Courses;
import com.knowledge.hub.features.platform.data.model.experience.courses.CreateCoursesRequest;
import com.knowledge.hub.features.platform.data.model.experience.courses.UpdateCoursesRequest;
import com.knowledge.hub.features.platform.web.service.CoursesService;
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
 * com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity}.
 *
 * @author Jagannath Mishra
 */
@Slf4j
@RestController
@RequestMapping(CoursesApi.rootEndPoint)
public class CoursesApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Courseses";

    /** Root end point. */
    public static final String rootEndPoint = "/knowledgehub-knowledgehub";

    /** Service implementation of type {@link CoursesService}. */
    private final CoursesService coursesService;

    /**
     * Constructor.
     *
     * @param coursesService Service instance of type {@link CoursesService}.
     */
    public CoursesApi(final CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Courses}.
     */
    @Operation(
            method = "createCourses",
            summary = "Create a new Courses.",
            description = "This API is used to create a new Courses in the system.",
            tags = {CoursesApi.API_TAG},
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
                        description = "Successfully created a new Courses in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/courseses")
    public ResponseEntity<Courses> createCourses(
            @Valid @RequestBody final CreateCoursesRequest payload) {
        // Delegate to the service layer.
        final Courses newInstance = coursesService.createCourses(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity} in the system.
     *
     * @param coursesId Unique identifier of Courses in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Courses, which needs to
     *     be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Courses}.
     */
    @Operation(
            method = "updateCourses",
            summary = "Update an existing Courses.",
            description = "This API is used to update an existing Courses in the system.",
            tags = {CoursesApi.API_TAG},
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
                        description = "Successfully updated an existing Courses in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/courseses/{coursesId}")
    public ResponseEntity<Courses> updateCourses(
            @PathVariable(name = "coursesId") final String coursesId,
            @Valid @RequestBody final UpdateCoursesRequest payload) {
        // Delegate to the service layer.
        final Courses updatedInstance = coursesService.updateCourses(coursesId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity} in the system.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Courses}.
     */
    @Operation(
            method = "findCourses",
            summary = "Find an existing Courses.",
            description = "This API is used to find an existing Courses in the system.",
            tags = {CoursesApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Courses in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/courseses/{coursesId}")
    public ResponseEntity<Courses> findCourses(
            @PathVariable(name = "coursesId") final String coursesId) {
        // Delegate to the service layer.
        final Courses matchingInstance = coursesService.findCourses(coursesId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Courses based on the provided pagination settings.
     */
    @Operation(
            method = "findAllCourseses",
            summary = "Find all Courseses.",
            description = "This API is used to find all Courseses in the system.",
            tags = {CoursesApi.API_TAG},
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
                                "Successfully retrieved the Courseses in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/courseses")
    public ResponseEntity<Page<Courses>> findAllCourseses(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Courses> matchingInstances = coursesService.findAllCourseses(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity} in the system.
     *
     * @param coursesId Unique identifier of Courses in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteCourses",
            summary = "Delete an existing Courses.",
            description = "This API is used to delete an existing Courses in the system.",
            tags = {CoursesApi.API_TAG},
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
                        description = "Successfully deleted an existing Courses in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/courseses/{coursesId}")
    public ResponseEntity<String> deleteCourses(
            @PathVariable(name = "coursesId") final String coursesId) {
        // Delegate to the service layer.
        final String deletedInstance = coursesService.deleteCourses(coursesId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}
