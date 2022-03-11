/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.mapper;

import com.knowledge.hub.features.platform.data.mapper.decorator.UsersMapperDecorator;
import com.knowledge.hub.features.platform.data.model.experience.users.CreateUsersRequest;
import com.knowledge.hub.features.platform.data.model.experience.users.UpdateUsersRequest;
import com.knowledge.hub.features.platform.data.model.experience.users.Users;
import com.knowledge.hub.features.platform.data.model.persistence.UsersEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link UsersEntity to {@link Users and vice-versa.
 *
 * @author Jagannath Mishra
 */
@DecoratedWith(value = UsersMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UsersMapper {

    /**
     * This method transforms an instance of type {@link CreateUsersRequest} to an instance of type
     * {@link UsersEntity}.
     *
     * @param source Instance of type {@link CreateUsersRequest} that needs to be transformed to
     *     {@link UsersEntity}.
     * @return Instance of type {@link UsersEntity}.
     */
    @Mapping(source = "roleId", target = "roleId", ignore = true)
    UsersEntity transform(CreateUsersRequest source);

    /**
     * This method transforms an instance of type {@link UsersEntity} to an instance of type {@link
     * Users}.
     *
     * @param source Instance of type {@link UsersEntity} that needs to be transformed to {@link
     *     Users}.
     * @return Instance of type {@link Users}.
     */
    @Mapping(source = "roleId", target = "roleId", ignore = true)
    Users transform(UsersEntity source);

    /**
     * This method converts / transforms the provided collection of {@link UsersEntity} instances to
     * a collection of instances of type {@link Users}.
     *
     * @param source Instance of type {@link UsersEntity} that needs to be transformed to {@link
     *     Users}.
     * @return Collection of instance of type {@link Users}.
     */
    default Collection<Users> transformListTo(Collection<UsersEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateUsersRequest} to an instance of type
     * {@link UsersEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateUsersRequest} that needs to be transformed to
     *     {@link UsersEntity}.
     * @param target Instance of type {@link UsersEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    @Mapping(source = "roleId", target = "roleId", ignore = true)
    void transform(UpdateUsersRequest source, @MappingTarget UsersEntity target);

    /**
     * This method transforms an instance of type {@link UpdateUsersRequest} to an instance of type
     * {@link UsersEntity}.
     *
     * @param source Instance of type {@link UpdateUsersRequest} that needs to be transformed to
     *     {@link UsersEntity}.
     * @return Instance of type {@link UsersEntity}.
     */
    @Mapping(source = "roleId", target = "roleId", ignore = true)
    UsersEntity transform(UpdateUsersRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateUsersRequest}
     * instances to a collection of instances of type {@link UsersEntity}.
     *
     * @param source Instance of type {@link UpdateUsersRequest} that needs to be transformed to
     *     {@link UsersEntity}.
     * @return Instance of type {@link UsersEntity}.
     */
    default Collection<UsersEntity> transformList(Collection<UpdateUsersRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}
