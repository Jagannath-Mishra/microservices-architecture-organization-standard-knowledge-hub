/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.model.experience.users;

import com.knowledge.hub.features.platform.data.model.experience.roles.Roles;
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
public class Users {
    /** Reference to the id. */
    private String id;

    /** Reference to the first_name. */
    private String firstName;

    /** Reference to the last_name. */
    private String lastName;

    /** Reference to the username. */
    private String username;

    /** Reference to the password. */
    private String password;

    /** Reference to the email. */
    private String email;

    /** Reference to the phone. */
    private Integer phone;

    /** Reference to the created. */
    private Date created;

    /** Reference to the updated. */
    private Date updated;

    /** Reference to the locked. */
    private Boolean locked;

    /** Reference to the disabled. */
    private Boolean disabled;

    /** Reference to the expired. */
    private Boolean expired;

    /** Reference to the role_id. */
    private Roles roleId;
}
