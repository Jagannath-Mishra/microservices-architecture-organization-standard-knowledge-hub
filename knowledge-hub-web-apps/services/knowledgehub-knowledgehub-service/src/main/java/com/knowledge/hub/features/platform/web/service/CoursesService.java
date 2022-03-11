/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.web.service;

import com.knowledge.hub.commons.data.utils.PageUtils;
import com.knowledge.hub.commons.instrumentation.Instrument;
import com.knowledge.hub.features.platform.data.mapper.CoursesMapper;
import com.knowledge.hub.features.platform.data.model.experience.courses.Courses;
import com.knowledge.hub.features.platform.data.model.experience.courses.CreateCoursesRequest;
import com.knowledge.hub.features.platform.data.model.experience.courses.UpdateCoursesRequest;
import com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity;
import com.knowledge.hub.features.platform.data.repository.CoursesRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link CoursesEntity}.
 *
 * @author Jagannath Mishra
 */
@Slf4j
@Validated
@Service
public class CoursesService {
    /** Repository implementation of type {@link CoursesRepository}. */
    private final CoursesRepository coursesRepository;

    /** Mapper implementation of type {@link CoursesMapper} to transform between different types. */
    private final CoursesMapper coursesMapper;

    /**
     * Constructor.
     *
     * @param coursesRepository Repository instance of type {@link CoursesRepository}.
     * @param coursesMapper Mapper instance of type {@link CoursesMapper}.
     */
    public CoursesService(
            final CoursesRepository coursesRepository, final CoursesMapper coursesMapper) {
        this.coursesRepository = coursesRepository;
        this.coursesMapper = coursesMapper;
    }

    /**
     * This method attempts to create an instance of type {@link CoursesEntity} in the system based
     * on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     CoursesEntity}.
     * @return An experience model of type {@link Courses} that represents the newly created entity
     *     of type {@link CoursesEntity}.
     */
    @Instrument
    @Transactional
    public Courses createCourses(@Valid final CreateCoursesRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final CoursesEntity coursesEntity = coursesMapper.transform(payload);

        // 2. Save the entity.
        CoursesService.LOGGER.debug("Saving a new instance of type - CoursesEntity");
        final CoursesEntity newInstance = coursesRepository.save(coursesEntity);

        // 3. Transform the created entity to an experience model and return it.
        return coursesMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link CoursesEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateCoursesRequest}.
     *
     * @param coursesId Unique identifier of Courses in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Courses, which needs to
     *     be updated in the system.
     * @return A instance of type {@link Courses} containing the updated details.
     */
    @Instrument
    @Transactional
    public Courses updateCourses(
            final String coursesId, @Valid final UpdateCoursesRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final CoursesEntity matchingInstance = coursesRepository.findByIdOrThrow(coursesId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        coursesMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        CoursesService.LOGGER.debug("Saving the updated entity - CoursesEntity");
        final CoursesEntity updatedInstance = coursesRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return coursesMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link CoursesEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param coursesId Unique identifier of Courses in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link Courses} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Courses findCourses(final String coursesId) {
        // 1. Find a matching entity and throw an exception if not found.
        final CoursesEntity matchingInstance = coursesRepository.findByIdOrThrow(coursesId);

        // 2. Transform the matching entity to the desired output.
        return coursesMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type CoursesEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Courses}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Courses> findAllCourseses(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        CoursesService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<CoursesEntity> pageData = coursesRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Courses> dataToReturn =
                    pageData.getContent().stream()
                            .map(coursesMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link CoursesEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param coursesId Unique identifier of Courses in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type CoursesEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteCourses(final String coursesId) {
        // 1. Delegate to our repository method to handle the deletion.
        return coursesRepository.deleteOne(coursesId);
    }
}
