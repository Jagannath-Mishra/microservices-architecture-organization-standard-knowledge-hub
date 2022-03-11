/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.model.persistence;

import com.knowledge.hub.commons.data.jpa.persistence.AbstractUUIDGeneratedEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "roles" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
@Entity
public class RolesEntity extends AbstractUUIDGeneratedEntity {

    /** Reference to the name. */
    @Column(name = "name", length = 20)
    private String name;

    /** Reference to the created. */
    @Column(name = "created")
    private Date created;
}
