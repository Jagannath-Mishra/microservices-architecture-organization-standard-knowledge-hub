/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.web.service;

import com.knowledge.hub.commons.exception.ServiceException;
import com.knowledge.hub.commons.instrumentation.Instrument;
import com.knowledge.hub.error.Errors;
import com.knowledge.hub.features.platform.data.mapper.RolesMapper;
import com.knowledge.hub.features.platform.data.model.experience.roles.CreateRolesRequest;
import com.knowledge.hub.features.platform.data.model.experience.roles.Roles;
import com.knowledge.hub.features.platform.data.model.experience.roles.UpdateRolesRequest;
import com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity;
import com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity;
import com.knowledge.hub.features.platform.data.model.persistence.RolesEntity;
import com.knowledge.hub.features.platform.data.model.persistence.UsersEntity;
import com.knowledge.hub.features.platform.data.repository.ContentsRepository;
import com.knowledge.hub.features.platform.data.repository.CoursesRepository;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link RolesEntity}.
 *
 * @author Jagannath Mishra
 */
@Slf4j
@Validated
@Service
public class RolesService {

    /** Mapper implementation of type {@link RolesMapper} to transform between different types. */
    private final RolesMapper rolesMapper;

    /** Repository implementation of type {@link CoursesRepository}. */
    private final CoursesRepository coursesRepository;
    /** Repository implementation of type {@link ContentsRepository}. */
    private final ContentsRepository contentsRepository;

    /**
     * Constructor.
     *
     * @param rolesMapper Mapper instance of type {@link RolesMapper}.
     * @param coursesRepository Repository instance of type {@link CoursesRepository}.
     * @param contentsRepository Repository instance of type {@link ContentsRepository}.
     */
    public RolesService(
            final RolesMapper rolesMapper,
            final CoursesRepository coursesRepository,
            final ContentsRepository contentsRepository) {
        this.rolesMapper = rolesMapper;
        this.coursesRepository = coursesRepository;
        this.contentsRepository = contentsRepository;
    }
    /**
     * This method attempts to create an instance of type {@link RolesEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     RolesEntity}.
     * @return An experience model of type {@link Roles} that represents the newly created entity of
     *     type {@link RolesEntity}.
     */
    @Instrument
    @Transactional
    public Roles createRoles(
            final String coursesId,
            final String contentsId,
            final String usersId,
            @Valid final CreateRolesRequest payload) {
        // 0. Validate the dependencies.
        final CoursesEntity matchingCourses =
                coursesRepository
                        .findById(coursesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, coursesId));

        final Optional<ContentsEntity> contents = matchingCourses.findContentsById(contentsId);
        if (!contents.isPresent()) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }
        final ContentsEntity matchingContents = contents.get();
        final UsersEntity matchingUsers = matchingContents.getCreatedBy();
        if (Objects.isNull(matchingUsers)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }
        // 1. Transform the experience model to a persistence model.
        final RolesEntity rolesEntity = rolesMapper.transform(payload);

        // 2. Add the mapped model to parent model.
        matchingUsers.setRoleId(rolesEntity);

        // 3. Save the entity.
        final ContentsEntity updatedInstance = contentsRepository.save(matchingContents);

        // 4. Transform and return.
        return rolesMapper.transform(matchingUsers.getRoleId());
    }

    /**
     * This method attempts to update an existing instance of type {@link RolesEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateRolesRequest}.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @param usersId Unique identifier of Users in the system, whose details have to be retrieved.
     * @param rolesId Unique identifier of Roles in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Roles, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Roles} containing the updated details.
     */
    @Instrument
    @Transactional
    public Roles updateRoles(
            final String coursesId,
            final String contentsId,
            final String usersId,
            final String rolesId,
            @Valid final UpdateRolesRequest payload) {

        // 0. Validate the dependencies.
        final CoursesEntity matchingCourses =
                coursesRepository
                        .findById(coursesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, coursesId));

        final Optional<ContentsEntity> contents = matchingCourses.findContentsById(contentsId);
        if (!contents.isPresent()) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }
        final ContentsEntity matchingContents = contents.get();
        final UsersEntity matchingUsers = matchingContents.getCreatedBy();
        if (Objects.isNull(matchingUsers)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 1. Verify that the entity being updated truly exists.
        final RolesEntity matchingInstance = matchingUsers.getRoleId();

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        rolesMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        RolesService.LOGGER.debug("Saving the updated entity - CoursesEntity");
        final ContentsEntity updatedInstance = contentsRepository.save(matchingContents);

        // 4. Transform updated entity to output object
        return rolesMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find a {@link RolesEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @param usersId Unique identifier of Users in the system, whose details have to be retrieved.
     * @param rolesId Unique identifier of Roles in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Roles} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Roles findRoles(
            final String coursesId,
            final String contentsId,
            final String usersId,
            final String rolesId) {

        // 0. Validate the dependencies.
        final CoursesEntity matchingCourses =
                coursesRepository
                        .findById(coursesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, coursesId));

        final Optional<ContentsEntity> contents = matchingCourses.findContentsById(contentsId);
        if (!contents.isPresent()) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }
        final ContentsEntity matchingContents = contents.get();
        final UsersEntity matchingUsers = matchingContents.getCreatedBy();
        if (Objects.isNull(matchingUsers)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 1. Verify that the entity being updated truly exists.
        final RolesEntity matchingInstance = matchingUsers.getRoleId();

        // 2. Transform updated entity to output object
        return rolesMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to delete an existing instance of type {@link RolesEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @param usersId Unique identifier of Users in the system, whose details have to be retrieved.
     * @param rolesId Unique identifier of Roles in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type RolesEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteRoles(
            final String coursesId,
            final String contentsId,
            final String usersId,
            final String rolesId) {

        // 0. Validate the dependencies.
        final CoursesEntity matchingCourses =
                coursesRepository
                        .findById(coursesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, coursesId));

        final Optional<ContentsEntity> contents = matchingCourses.findContentsById(contentsId);
        if (!contents.isPresent()) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }
        final ContentsEntity matchingContents = contents.get();
        final UsersEntity matchingUsers = matchingContents.getCreatedBy();
        if (Objects.isNull(matchingUsers)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 1. Verify that the entity being deleted truly exists.
        final RolesEntity matchingInstance = matchingUsers.getRoleId();
        if (Objects.isNull(matchingInstance)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2. Remove the matchingInstance form its parent.
        matchingUsers.setRoleId(null);

        // 3. Persist the parentInstance.
        contentsRepository.save(matchingContents);

        // 4. Return the unique identifier of deleted instance.
        return rolesId;
    }
}
