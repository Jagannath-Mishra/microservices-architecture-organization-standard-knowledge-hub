/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.knowledge.hub.features.platform.data.model.persistence;

import com.knowledge.hub.commons.data.jpa.persistence.AbstractUUIDGeneratedEntity;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "courses" table in the database to an entity in the ORM world.
 *
 * @author Jagannath Mishra
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "courses")
@Entity
public class CoursesEntity extends AbstractUUIDGeneratedEntity {

    /** Reference to the name. */
    @Column(name = "name", length = 20)
    private String name;

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

    /** Reference to the content_id. */
    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JoinColumn(name = "courses_id", referencedColumnName = "id")
    private Collection<ContentsEntity> contentId;

    /**
     * This entity method is used to add an existing Contents in the system. {@link
     * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity} into the system.
     *
     * @param contents containing the details required to create an instance of type {@link
     *     com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity}.
     */
    public void addContents(final ContentsEntity contents) {
        if (Objects.nonNull(contents)) {
            this.contentId.add(contents);
        }
    }

    /**
     * This entity method is used to delete an existing Contents in the system. instance of {@link
     * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity}}.
     *
     * @param contentsId Unique identifier of Contents.
     */
    public void deleteContentsById(final String contentsId) {

        final Optional<ContentsEntity> matchingRecord = findContentsById(contentsId);
        if (matchingRecord.isPresent()) {
            getContentId().remove(matchingRecord.get());
        }
    }

    /**
     * This method is used to find an existing Contents in the system. instance of {@link
     * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity}}.
     *
     * @param contentsId Unique identifier of Contents.
     * @return Return an instance of type {@link
     *     com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity}.
     */
    public Optional<ContentsEntity> findContentsById(final String contentsId) {

        if (Objects.isNull(contentId) || contentId.isEmpty()) {
            return Optional.empty();
        }

        return contentId.stream()
                .filter(contents -> contents.getId().equals(contentsId))
                .findFirst();
    }

    /**
     * This entity method is used to retrieve the latest Contents of type {@link
     * com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity}.
     *
     * @return Return an instance of type {@link
     *     com.knowledge.hub.features.platform.data.model.persistence.ContentsEntity}.
     */
    public ContentsEntity getLatestContents() {
        final List<ContentsEntity> contentId = (List<ContentsEntity>) this.contentId;
        return contentId.get(contentId.size() - 1);
    }
}
