/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of knowledge-hub.
 *
 * knowledge-hub project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.knowledge.hub.security.db;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.knowledge.hub.security.db.configuration.SecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * A meta-annotation that configures all the beans required for the Database Authentication functionality.
 * <p>
 * It is recommended to annotate the application's main class with this annotation.
 *
 * @author Jagannath Mishra
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(value = {SecurityConfiguration.class})
@Configuration
public @interface EnableDBAuthentication {
}
