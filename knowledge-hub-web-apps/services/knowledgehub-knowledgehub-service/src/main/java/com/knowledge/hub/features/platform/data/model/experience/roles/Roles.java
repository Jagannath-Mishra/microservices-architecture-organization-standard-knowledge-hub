/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.model.experience.roles;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Implementation of an experience model that is meant to be used by the API Layer for communication
 * either with the front-end or to the service-layer.
 *
 * @author Jagannath Mishra
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class Roles {
    /** Reference to the id. */
    private String id;

    /** Reference to the name. */
    private String name;

    /** Reference to the created. */
    private Date created;
}
