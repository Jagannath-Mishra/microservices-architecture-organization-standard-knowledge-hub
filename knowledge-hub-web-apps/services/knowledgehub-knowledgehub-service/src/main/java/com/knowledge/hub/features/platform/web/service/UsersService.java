/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.web.service;

import com.knowledge.hub.commons.exception.ServiceException;
import com.knowledge.hub.commons.instrumentation.Instrument;
import com.knowledge.hub.error.Errors;
import com.knowledge.hub.features.platform.data.mapper.UsersMapper;
import com.knowledge.hub.features.platform.data.model.experience.users.CreateUsersRequest;
import com.knowledge.hub.features.platform.data.model.experience.users.UpdateUsersRequest;
import com.knowledge.hub.features.platform.data.model.experience.users.Users;
import com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity;
import com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity;
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
 * entities of type {@link UsersEntity}.
 *
 * @author Jagannath Mishra
 */
@Slf4j
@Validated
@Service
public class UsersService {

    /** Mapper implementation of type {@link UsersMapper} to transform between different types. */
    private final UsersMapper usersMapper;

    /** Repository implementation of type {@link CoursesRepository}. */
    private final CoursesRepository coursesRepository;
    /** Repository implementation of type {@link ContentsRepository}. */
    private final ContentsRepository contentsRepository;

    /**
     * Constructor.
     *
     * @param usersMapper Mapper instance of type {@link UsersMapper}.
     * @param coursesRepository Repository instance of type {@link CoursesRepository}.
     * @param contentsRepository Repository instance of type {@link ContentsRepository}.
     */
    public UsersService(
            final UsersMapper usersMapper,
            final CoursesRepository coursesRepository,
            final ContentsRepository contentsRepository) {
        this.usersMapper = usersMapper;
        this.coursesRepository = coursesRepository;
        this.contentsRepository = contentsRepository;
    }
    /**
     * This method attempts to create an instance of type {@link UsersEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     UsersEntity}.
     * @return An experience model of type {@link Users} that represents the newly created entity of
     *     type {@link UsersEntity}.
     */
    @Instrument
    @Transactional
    public Users createUsers(
            final String coursesId,
            final String contentsId,
            @Valid final CreateUsersRequest payload) {
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
        // 1. Transform the experience model to a persistence model.
        final UsersEntity usersEntity = usersMapper.transform(payload);

        // 2. Add the mapped model to parent model.
        matchingContents.setCreatedBy(usersEntity);

        // 3. Save the entity.
        final ContentsEntity updatedInstance = contentsRepository.save(matchingContents);

        // 4. Transform and return.
        return usersMapper.transform(matchingContents.getCreatedBy());
    }

    /**
     * This method attempts to update an existing instance of type {@link UsersEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateUsersRequest}.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @param usersId Unique identifier of Users in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Users, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Users} containing the updated details.
     */
    @Instrument
    @Transactional
    public Users updateUsers(
            final String coursesId,
            final String contentsId,
            final String usersId,
            @Valid final UpdateUsersRequest payload) {

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

        // 1. Verify that the entity being updated truly exists.
        final UsersEntity matchingInstance = matchingContents.getCreatedBy();

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        usersMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        UsersService.LOGGER.debug("Saving the updated entity - CoursesEntity");
        final ContentsEntity updatedInstance = contentsRepository.save(matchingContents);

        // 4. Transform updated entity to output object
        return usersMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find a {@link UsersEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @param usersId Unique identifier of Users in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Users} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Users findUsers(final String coursesId, final String contentsId, final String usersId) {

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

        // 1. Verify that the entity being updated truly exists.
        final UsersEntity matchingInstance = matchingContents.getCreatedBy();

        // 2. Transform updated entity to output object
        return usersMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to delete an existing instance of type {@link UsersEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @param usersId Unique identifier of Users in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type UsersEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteUsers(
            final String coursesId, final String contentsId, final String usersId) {

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

        // 1. Verify that the entity being deleted truly exists.
        final UsersEntity matchingInstance = matchingContents.getCreatedBy();
        if (Objects.isNull(matchingInstance)) {
            throw ServiceException.instance(Errors.RESOURCE_NOT_FOUND);
        }

        // 2. Remove the matchingInstance form its parent.
        matchingContents.setCreatedBy(null);

        // 3. Persist the parentInstance.
        contentsRepository.save(matchingContents);

        // 4. Return the unique identifier of deleted instance.
        return usersId;
    }
}
