/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of knowledge-hub.
 *
 * knowledge-hub project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.knowledge.hub.infra.service.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Main class for the configuration service and provides a centralized way of serving microservice-specific
 * configurations.
 *
 * @author Jagannath Mishra
 */
@EnableConfigServer
@SpringBootApplication
public class PlatformConfigurationService {
    public static void main(final String[] args) {
        SpringApplication.run(PlatformConfigurationService.class, args);
    }
}