/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.model.persistence;

import com.knowledge.hub.commons.data.jpa.persistence.AbstractUUIDGeneratedEntity;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "users" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class UsersEntity extends AbstractUUIDGeneratedEntity {

    /** Reference to the first_name. */
    @Column(name = "first_name", length = 20)
    private String firstName;

    /** Reference to the last_name. */
    @Column(name = "last_name", length = 20)
    private String lastName;

    /** Reference to the username. */
    @Column(name = "username", length = 20)
    private String username;

    /** Reference to the password. */
    @Column(name = "password", length = 20)
    private String password;

    /** Reference to the email. */
    @Column(name = "email", length = 20)
    private String email;

    /** Reference to the phone. */
    @Column(name = "phone")
    private Integer phone;

    /** Reference to the created. */
    @Column(name = "created")
    private Date created;

    /** Reference to the updated. */
    @Column(name = "updated")
    private Date updated;

    /** Reference to the locked. */
    @Column(name = "locked")
    private Boolean locked;

    /** Reference to the disabled. */
    @Column(name = "disabled")
    private Boolean disabled;

    /** Reference to the expired. */
    @Column(name = "expired")
    private Boolean expired;

    /** Reference to the role_id. */
    @OneToOne(
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RolesEntity roleId;
}
