/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.mapper.decorator;

import com.knowledge.hub.features.platform.data.mapper.ContentsMapper;
import com.knowledge.hub.features.platform.data.mapper.CoursesMapper;
import com.knowledge.hub.features.platform.data.mapper.UsersMapper;
import com.knowledge.hub.features.platform.data.model.experience.courses.Courses;
import com.knowledge.hub.features.platform.data.model.experience.courses.CreateCoursesRequest;
import com.knowledge.hub.features.platform.data.model.experience.courses.UpdateCoursesRequest;
import com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity;
import com.knowledge.hub.features.platform.data.repository.ContentsRepository;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link CoursesEntity to {@link Courses and vice-versa.
 *
 * @author Jagannath Mishra
 */
@Slf4j
public abstract class CoursesMapperDecorator implements CoursesMapper {

    /** Repository implementation of type {@link ContentsRepository}. */
    @Autowired private ContentsRepository contentsRepository;

    /** Mapper implementation of type {@link CoursesMapper}. */
    @Autowired private CoursesMapper coursesMapper;

    /** Mapper implementation of type {@link UsersMapper}. */
    @Autowired private UsersMapper usersMapper;

    /** Mapper implementation of type {@link ContentsMapper}. */
    @Autowired private ContentsMapper contentsMapper;

    @Override
    public CoursesEntity transform(final CreateCoursesRequest source) {
        // 1. Transform the CreateCoursesRequest to CoursesEntity object.
        final CoursesEntity courses = coursesMapper.transform(source);

        if (!CollectionUtils.isEmpty(source.getContentId())) {
            courses.setContentId(
                    source.getContentId().stream()
                            .map(contents -> contentsMapper.transform(contents))
                            .collect(Collectors.toList()));
        }
        if (Objects.nonNull(source.getCreatedBy())) {
            courses.setCreatedBy(usersMapper.transform(source.getCreatedBy()));
        }

        // Return the transformed object.
        return courses;
    }

    @Override
    public Courses transform(final CoursesEntity source) {
        // 1. Transform the CoursesEntity to Courses object.
        final Courses courses = coursesMapper.transform(source);

        if (!CollectionUtils.isEmpty(source.getContentId())) {
            courses.setContentId(
                    source.getContentId().stream()
                            .map(contents -> contentsMapper.transform(contents))
                            .collect(Collectors.toList()));
        }
        if (Objects.nonNull(source.getCreatedBy())) {
            courses.setCreatedBy(usersMapper.transform(source.getCreatedBy()));
        }

        // Return the transformed object.
        return courses;
    }

    @Override
    public void transform(
            final UpdateCoursesRequest source, final @MappingTarget CoursesEntity target) {

        // Transform from source to the target.
        coursesMapper.transform(source, target);

        if (!CollectionUtils.isEmpty(source.getContentId())) {
            // As Hibernate does allow to override the value in case of composition, we need to
            // clear the collection first then add updated collection.
            target.getContentId().clear();
            target.getContentId().addAll(contentsMapper.transformList(source.getContentId()));
        }
        if (Objects.nonNull(source.getCreatedBy())) {
            usersMapper.transform(source.getCreatedBy(), target.getCreatedBy());
        }
    }

    @Override
    public CoursesEntity transform(final UpdateCoursesRequest source) {

        // Transform from source to the target.
        final CoursesEntity courses = coursesMapper.transform(source);

        if (!CollectionUtils.isEmpty(source.getContentId())) {
            courses.setContentId(contentsMapper.transformList(source.getContentId()));
        }
        if (Objects.nonNull(source.getCreatedBy())) {
            courses.setCreatedBy(usersMapper.transform(source.getCreatedBy()));
        }
        // Return the response.
        return courses;
    }
}
