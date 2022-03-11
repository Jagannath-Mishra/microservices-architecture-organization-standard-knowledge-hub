/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.mapper;

import com.knowledge.hub.features.platform.data.mapper.decorator.ContentsMapperDecorator;
import com.knowledge.hub.features.platform.data.model.experience.contents.Contents;
import com.knowledge.hub.features.platform.data.model.experience.contents.CreateContentsRequest;
import com.knowledge.hub.features.platform.data.model.experience.contents.UpdateContentsRequest;
import com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link ContentsEntity to {@link Contents and vice-versa.
 *
 * @author Jagannath Mishra
 */
@DecoratedWith(value = ContentsMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ContentsMapper {

    /**
     * This method transforms an instance of type {@link CreateContentsRequest} to an instance of
     * type {@link ContentsEntity}.
     *
     * @param source Instance of type {@link CreateContentsRequest} that needs to be transformed to
     *     {@link ContentsEntity}.
     * @return Instance of type {@link ContentsEntity}.
     */
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    ContentsEntity transform(CreateContentsRequest source);

    /**
     * This method transforms an instance of type {@link ContentsEntity} to an instance of type
     * {@link Contents}.
     *
     * @param source Instance of type {@link ContentsEntity} that needs to be transformed to {@link
     *     Contents}.
     * @return Instance of type {@link Contents}.
     */
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    Contents transform(ContentsEntity source);

    /**
     * This method converts / transforms the provided collection of {@link ContentsEntity} instances
     * to a collection of instances of type {@link Contents}.
     *
     * @param source Instance of type {@link ContentsEntity} that needs to be transformed to {@link
     *     Contents}.
     * @return Collection of instance of type {@link Contents}.
     */
    default Collection<Contents> transformListTo(Collection<ContentsEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateContentsRequest} to an instance of
     * type {@link ContentsEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateContentsRequest} that needs to be transformed to
     *     {@link ContentsEntity}.
     * @param target Instance of type {@link ContentsEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    void transform(UpdateContentsRequest source, @MappingTarget ContentsEntity target);

    /**
     * This method transforms an instance of type {@link UpdateContentsRequest} to an instance of
     * type {@link ContentsEntity}.
     *
     * @param source Instance of type {@link UpdateContentsRequest} that needs to be transformed to
     *     {@link ContentsEntity}.
     * @return Instance of type {@link ContentsEntity}.
     */
    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    ContentsEntity transform(UpdateContentsRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateContentsRequest}
     * instances to a collection of instances of type {@link ContentsEntity}.
     *
     * @param source Instance of type {@link UpdateContentsRequest} that needs to be transformed to
     *     {@link ContentsEntity}.
     * @return Instance of type {@link ContentsEntity}.
     */
    default Collection<ContentsEntity> transformList(Collection<UpdateContentsRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
