/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.mapper;

import com.knowledge.hub.features.platform.data.model.experience.roles.CreateRolesRequest;
import com.knowledge.hub.features.platform.data.model.experience.roles.Roles;
import com.knowledge.hub.features.platform.data.model.experience.roles.UpdateRolesRequest;
import com.knowledge.hub.features.platform.data.model.persistence.RolesEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link RolesEntity to {@link Roles and vice-versa.
 *
 * @author Jagannath Mishra
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RolesMapper {

    /**
     * This method transforms an instance of type {@link CreateRolesRequest} to an instance of type
     * {@link RolesEntity}.
     *
     * @param source Instance of type {@link CreateRolesRequest} that needs to be transformed to
     *     {@link RolesEntity}.
     * @return Instance of type {@link RolesEntity}.
     */
    RolesEntity transform(CreateRolesRequest source);

    /**
     * This method transforms an instance of type {@link RolesEntity} to an instance of type {@link
     * Roles}.
     *
     * @param source Instance of type {@link RolesEntity} that needs to be transformed to {@link
     *     Roles}.
     * @return Instance of type {@link Roles}.
     */
    Roles transform(RolesEntity source);

    /**
     * This method converts / transforms the provided collection of {@link RolesEntity} instances to
     * a collection of instances of type {@link Roles}.
     *
     * @param source Instance of type {@link RolesEntity} that needs to be transformed to {@link
     *     Roles}.
     * @return Collection of instance of type {@link Roles}.
     */
    default Collection<Roles> transformListTo(Collection<RolesEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateRolesRequest} to an instance of type
     * {@link RolesEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateRolesRequest} that needs to be transformed to
     *     {@link RolesEntity}.
     * @param target Instance of type {@link RolesEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    void transform(UpdateRolesRequest source, @MappingTarget RolesEntity target);

    /**
     * This method transforms an instance of type {@link UpdateRolesRequest} to an instance of type
     * {@link RolesEntity}.
     *
     * @param source Instance of type {@link UpdateRolesRequest} that needs to be transformed to
     *     {@link RolesEntity}.
     * @return Instance of type {@link RolesEntity}.
     */
    RolesEntity transform(UpdateRolesRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateRolesRequest}
     * instances to a collection of instances of type {@link RolesEntity}.
     *
     * @param source Instance of type {@link UpdateRolesRequest} that needs to be transformed to
     *     {@link RolesEntity}.
     * @return Instance of type {@link RolesEntity}.
     */
    default Collection<RolesEntity> transformList(Collection<UpdateRolesRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
