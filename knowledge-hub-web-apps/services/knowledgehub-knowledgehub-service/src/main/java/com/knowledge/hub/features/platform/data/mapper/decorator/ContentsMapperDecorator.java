/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.mapper.decorator;

import com.knowledge.hub.features.platform.data.mapper.ContentsMapper;
import com.knowledge.hub.features.platform.data.mapper.UsersMapper;
import com.knowledge.hub.features.platform.data.model.experience.contents.Contents;
import com.knowledge.hub.features.platform.data.model.experience.contents.CreateContentsRequest;
import com.knowledge.hub.features.platform.data.model.experience.contents.UpdateContentsRequest;
import com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link ContentsEntity to {@link Contents and vice-versa.
 *
 * @author Jagannath Mishra
 */
@Slf4j
public abstract class ContentsMapperDecorator implements ContentsMapper {

    /** Mapper implementation of type {@link UsersMapper}. */
    @Autowired private UsersMapper usersMapper;

    /** Mapper implementation of type {@link ContentsMapper}. */
    @Autowired private ContentsMapper contentsMapper;

    @Override
    public ContentsEntity transform(final CreateContentsRequest source) {
        // 1. Transform the CreateContentsRequest to ContentsEntity object.
        final ContentsEntity contents = contentsMapper.transform(source);

        if (Objects.nonNull(source.getCreatedBy())) {
            contents.setCreatedBy(usersMapper.transform(source.getCreatedBy()));
        }

        // Return the transformed object.
        return contents;
    }

    @Override
    public Contents transform(final ContentsEntity source) {
        // 1. Transform the ContentsEntity to Contents object.
        final Contents contents = contentsMapper.transform(source);

        if (Objects.nonNull(source.getCreatedBy())) {
            contents.setCreatedBy(usersMapper.transform(source.getCreatedBy()));
        }

        // Return the transformed object.
        return contents;
    }

    @Override
    public void transform(
            final UpdateContentsRequest source, final @MappingTarget ContentsEntity target) {

        // Transform from source to the target.
        contentsMapper.transform(source, target);

        if (Objects.nonNull(source.getCreatedBy())) {
            usersMapper.transform(source.getCreatedBy(), target.getCreatedBy());
        }
    }

    @Override
    public ContentsEntity transform(final UpdateContentsRequest source) {

        // Transform from source to the target.
        final ContentsEntity contents = contentsMapper.transform(source);

        if (Objects.nonNull(source.getCreatedBy())) {
            contents.setCreatedBy(usersMapper.transform(source.getCreatedBy()));
        }
        // Return the response.
        return contents;
    }
}
