/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.mapper.decorator;

import com.knowledge.hub.features.platform.data.mapper.RolesMapper;
import com.knowledge.hub.features.platform.data.mapper.UsersMapper;
import com.knowledge.hub.features.platform.data.model.experience.users.CreateUsersRequest;
import com.knowledge.hub.features.platform.data.model.experience.users.UpdateUsersRequest;
import com.knowledge.hub.features.platform.data.model.experience.users.Users;
import com.knowledge.hub.features.platform.data.model.persistence.UsersEntity;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link UsersEntity to {@link Users and vice-versa.
 *
 * @author Jagannath Mishra
 */
@Slf4j
public abstract class UsersMapperDecorator implements UsersMapper {

    @Autowired private PasswordEncoder passwordEncoder;

    /** Mapper implementation of type {@link RolesMapper}. */
    @Autowired private RolesMapper rolesMapper;

    /** Mapper implementation of type {@link UsersMapper}. */
    @Autowired private UsersMapper usersMapper;

    @Override
    public UsersEntity transform(final CreateUsersRequest source) {
        // 1. Transform the CreateUsersRequest to UsersEntity object.
        final UsersEntity users = usersMapper.transform(source);
        if (Objects.nonNull(source.getPassword()) && !source.getPassword().isEmpty()) {
            source.setPassword(passwordEncoder.encode(source.getPassword()));
        }

        if (Objects.nonNull(source.getRoleId())) {
            users.setRoleId(rolesMapper.transform(source.getRoleId()));
        }

        // Return the transformed object.
        return users;
    }

    @Override
    public Users transform(final UsersEntity source) {
        // 1. Transform the UsersEntity to Users object.
        final Users users = usersMapper.transform(source);
        if (Objects.nonNull(source.getPassword()) && !source.getPassword().isEmpty()) {
            source.setPassword(passwordEncoder.encode(source.getPassword()));
        }

        if (Objects.nonNull(source.getRoleId())) {
            users.setRoleId(rolesMapper.transform(source.getRoleId()));
        }

        // Return the transformed object.
        return users;
    }

    @Override
    public void transform(
            final UpdateUsersRequest source, final @MappingTarget UsersEntity target) {

        // Transform from source to the target.
        usersMapper.transform(source, target);

        if (Objects.nonNull(source.getRoleId())) {
            rolesMapper.transform(source.getRoleId(), target.getRoleId());
        }
    }

    @Override
    public UsersEntity transform(final UpdateUsersRequest source) {

        // Transform from source to the target.
        final UsersEntity users = usersMapper.transform(source);

        if (Objects.nonNull(source.getRoleId())) {
            users.setRoleId(rolesMapper.transform(source.getRoleId()));
        }
        // Return the response.
        return users;
    }
}
