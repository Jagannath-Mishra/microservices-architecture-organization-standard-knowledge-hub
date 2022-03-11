/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.mapper;

import com.knowledge.hub.features.platform.data.mapper.decorator.CoursesMapperDecorator;
import com.knowledge.hub.features.platform.data.model.experience.courses.Courses;
import com.knowledge.hub.features.platform.data.model.experience.courses.CreateCoursesRequest;
import com.knowledge.hub.features.platform.data.model.experience.courses.UpdateCoursesRequest;
import com.knowledge.hub.features.platform.data.model.persistence.CoursesEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link CoursesEntity to {@link Courses and vice-versa.
 *
 * @author Jagannath Mishra
 */
@DecoratedWith(value = CoursesMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CoursesMapper {

    /**
     * This method transforms an instance of type {@link CreateCoursesRequest} to an instance of
     * type {@link CoursesEntity}.
     *
     * @param source Instance of type {@link CreateCoursesRequest} that needs to be transformed to
     *     {@link CoursesEntity}.
     * @return Instance of type {@link CoursesEntity}.
     */
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    @Mapping(source = "contentId", target = "contentId", ignore = true)
    CoursesEntity transform(CreateCoursesRequest source);

    /**
     * This method transforms an instance of type {@link CoursesEntity} to an instance of type
     * {@link Courses}.
     *
     * @param source Instance of type {@link CoursesEntity} that needs to be transformed to {@link
     *     Courses}.
     * @return Instance of type {@link Courses}.
     */
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    @Mapping(source = "contentId", target = "contentId", ignore = true)
    Courses transform(CoursesEntity source);

    /**
     * This method converts / transforms the provided collection of {@link CoursesEntity} instances
     * to a collection of instances of type {@link Courses}.
     *
     * @param source Instance of type {@link CoursesEntity} that needs to be transformed to {@link
     *     Courses}.
     * @return Collection of instance of type {@link Courses}.
     */
    default Collection<Courses> transformListTo(Collection<CoursesEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateCoursesRequest} to an instance of
     * type {@link CoursesEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateCoursesRequest} that needs to be transformed to
     *     {@link CoursesEntity}.
     * @param target Instance of type {@link CoursesEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    @Mapping(source = "contentId", target = "contentId", ignore = true)
    void transform(UpdateCoursesRequest source, @MappingTarget CoursesEntity target);

    /**
     * This method transforms an instance of type {@link UpdateCoursesRequest} to an instance of
     * type {@link CoursesEntity}.
     *
     * @param source Instance of type {@link UpdateCoursesRequest} that needs to be transformed to
     *     {@link CoursesEntity}.
     * @return Instance of type {@link CoursesEntity}.
     */
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    @Mapping(source = "contentId", target = "contentId", ignore = true)
    CoursesEntity transform(UpdateCoursesRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateCoursesRequest}
     * instances to a collection of instances of type {@link CoursesEntity}.
     *
     * @param source Instance of type {@link UpdateCoursesRequest} that needs to be transformed to
     *     {@link CoursesEntity}.
     * @return Instance of type {@link CoursesEntity}.
     */
    default Collection<CoursesEntity> transformList(Collection<UpdateCoursesRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
