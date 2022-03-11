/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.repository;

import com.knowledge.hub.commons.data.jpa.repository.ExtendedJpaRepository;
import com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to handle the operations pertaining to domain models of type
 * "ContentsEntity".
 *
 * @author Jagannath Mishra
 */
@Repository
public interface ContentsRepository extends ExtendedJpaRepository<ContentsEntity, String> {
    // Any custom methods can be added here.
}
