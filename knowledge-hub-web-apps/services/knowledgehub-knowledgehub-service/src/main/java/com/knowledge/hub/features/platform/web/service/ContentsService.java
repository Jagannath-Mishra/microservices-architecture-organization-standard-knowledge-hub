/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.web.service;

import com.knowledge.hub.commons.data.utils.PageUtils;
import com.knowledge.hub.commons.exception.ServiceException;
import com.knowledge.hub.commons.instrumentation.Instrument;
import com.knowledge.hub.error.Errors;
import com.knowledge.hub.features.platform.data.mapper.ContentsMapper;
import com.knowledge.hub.features.platform.data.model.experience.contents.Contents;
import com.knowledge.hub.features.platform.data.model.experience.contents.CreateContentsRequest;
import com.knowledge.hub.features.platform.data.model.experience.contents.UpdateContentsRequest;
import com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity;
import com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity;
import com.knowledge.hub.features.platform.data.repository.CoursesRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link ContentsEntity}.
 *
 * @author Jagannath Mishra
 */
@Slf4j
@Validated
@Service
public class ContentsService {

    /** Repository implementation of type {@link CoursesRepository}. */
    private final CoursesRepository coursesRepository;

    /**
     * Mapper implementation of type {@link ContentsMapper} to transform between different types.
     */
    private final ContentsMapper contentsMapper;

    /**
     * Constructor.
     *
     * @param coursesRepository Repository instance of type {@link CoursesRepository}.
     * @param contentsMapper Mapper instance of type {@link ContentsMapper}.
     */
    public ContentsService(
            final CoursesRepository coursesRepository, final ContentsMapper contentsMapper) {
        this.coursesRepository = coursesRepository;
        this.contentsMapper = contentsMapper;
    }

    /**
     * This method attempts to create an instance of type {@link ContentsEntity} in the system based
     * on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     ContentsEntity}.
     * @return An experience model of type {@link Contents} that represents the newly created entity
     *     of type {@link ContentsEntity}.
     */
    @Instrument
    @Transactional
    public Contents createContents(
            final String coursesId, @Valid final CreateContentsRequest payload) {
        // 0. Validate the dependencies.

        final CoursesEntity matchingCourses =
                coursesRepository
                        .findById(coursesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, coursesId));
        // 1. Transform the experience model to a persistence model.
        final ContentsEntity contentsEntity = contentsMapper.transform(payload);

        // 2. Add the mapped model to parent model.
        matchingCourses.addContents(contentsEntity);

        // 3. Save the entity.
        ContentsService.LOGGER.debug("Saving added instance of type - ContentsEntity");
        final CoursesEntity updatedInstance = coursesRepository.save(matchingCourses);

        // 4. Transform and return.
        return contentsMapper.transform(matchingCourses.getLatestContents());
    }

    /**
     * This method attempts to update an existing instance of type {@link ContentsEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateContentsRequest}.
     *
     * @param contentsId Unique identifier of Contents in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Contents, which needs to
     *     be updated in the system.
     * @return A instance of type {@link Contents} containing the updated details.
     */
    @Instrument
    @Transactional
    public Contents updateContents(
            final String coursesId,
            final String contentsId,
            @Valid final UpdateContentsRequest payload) {

        // 0. Validate the dependencies.

        final CoursesEntity matchingCourses =
                coursesRepository
                        .findById(coursesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, coursesId));

        // 1. Verify that the entity being updated truly exists.
        final ContentsEntity matchingInstance =
                matchingCourses
                        .findContentsById(contentsId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, contentsId));

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        contentsMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        ContentsService.LOGGER.debug("Saving the updated entity - CoursesEntity");
        final CoursesEntity updatedInstance = coursesRepository.save(matchingCourses);

        // 4. Transform updated entity to output object
        return contentsMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find a {@link ContentsEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param contentsId Unique identifier of Contents in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link Contents} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Contents findContents(final String coursesId, final String contentsId) {

        // 0. Validate the dependencies.

        final CoursesEntity matchingCourses =
                coursesRepository
                        .findById(coursesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, coursesId));

        // 1. Verify that the entity being updated truly exists.
        final ContentsEntity matchingContents =
                matchingCourses
                        .findContentsById(contentsId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, contentsId));

        // 2. Transform updated entity to output object
        return contentsMapper.transform(matchingContents);
    }

    /**
     * This method attempts to find instances of type ContentsEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Contents}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Contents> findAllContentses(final String coursesId, final Pageable page) {
        // 0. Validate the dependencies.

        final CoursesEntity matchingCourses =
                coursesRepository
                        .findById(coursesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, coursesId));

        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        ContentsService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<ContentsEntity> pageData =
                new PageImpl(
                        (List) matchingCourses.getContentId(),
                        pageSettings,
                        matchingCourses.getContentId().size());

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Contents> dataToReturn =
                    pageData.getContent().stream()
                            .map(contentsMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }
        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link ContentsEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param contentsId Unique identifier of Contents in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type ContentsEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteContents(final String coursesId, final String contentsId) {

        // 0. Validate the dependencies.

        final CoursesEntity matchingCourses =
                coursesRepository
                        .findById(coursesId)
                        .orElseThrow(
                                () ->
                                        ServiceException.instance(
                                                Errors.RESOURCE_NOT_FOUND_WITH_ID, coursesId));

        // 1. Remove the matchingInstance form its parent.
        matchingCourses.deleteContentsById(contentsId);

        // 2. Persist the parentInstance.
        coursesRepository.save(matchingCourses);

        // 3. Return the deleted identifier.
        return contentsId;
    }
}
