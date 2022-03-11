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
 * Implementation that maps the "contents" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "contents")
@Entity
public class ContentsEntity extends AbstractUUIDGeneratedEntity {

    /** Reference to the header. */
    @Column(name = "header", length = 20)
    private String header;

    /** Reference to the body. */
    @Column(name = "body", length = 20)
    private String body;

    /** Reference to the created. */
    @Column(name = "created")
    private Date created;

    /** Reference to the updated. */
    @Column(name = "updated")
    private Date updated;

    /** Reference to the hidden. */
    @Column(name = "hidden")
    private Boolean hidden;

    /** Reference to the deleted. */
    @Column(name = "deleted")
    private Boolean deleted;

    /** Reference to the approved. */
    @Column(name = "approved")
    private Boolean approved;

    /** Reference to the created_by. */
    @OneToOne(
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private UsersEntity createdBy;

    /** Reference to the column_1. */
    @Column(name = "column_1")
    private Integer column1;
}
