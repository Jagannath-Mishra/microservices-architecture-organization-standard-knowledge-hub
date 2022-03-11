/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of knowledge-hub.
 *
 * knowledge-hub project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.knowledge.hub.commons.data.jpa.persistence;

import com.knowledge.hub.commons.data.persistence.IEntity;

import java.io.Serializable;

/**
 * Abstract implementation of an entity where the value of the primary key.
 *
 * @author Jagannath Mishra
 */
public abstract class AbstractPrimaryKeyEntity<ID extends Serializable> implements IEntity<ID> {

}
