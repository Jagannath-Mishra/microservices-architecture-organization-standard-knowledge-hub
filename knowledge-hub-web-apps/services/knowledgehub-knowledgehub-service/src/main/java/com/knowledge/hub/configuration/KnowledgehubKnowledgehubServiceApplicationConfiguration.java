/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of knowledgehub-knowledgehub-service.
 *
 * knowledgehub-knowledgehub-service project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.knowledge.hub.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.knowledge.hub.commons.web.configuration.WebConfiguration;
import com.knowledge.hub.commons.web.configuration.properties.ApiDocumentationSettings;
import com.knowledge.hub.security.properties.ApplicationSecurityProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class responsible to configure the beans, etc. for the microservice that contains this configuration.
 *
 * @author Jagannath Mishra
 */
@Import(value = {WebConfiguration.class})
@PropertySource("classpath:/l10n/ValidationMessages.properties")
@PropertySource("classpath:/l10n/error_messages.properties")
@EnableJpaRepositories(basePackages = {"com.knowledge.hub.features.platform.data.repository"})
@EntityScan(basePackages = {"com.knowledge.hub.features.platform.data.model.persistence"})
@Configuration
public class KnowledgehubKnowledgehubServiceApplicationConfiguration {
    /**
     * Bean that captures the API documentation settings.
     *
     * @return Bean instance of type {@link ApiDocumentationSettings}.
     */
    @ConfigurationProperties(prefix = "com.knowledge.hub.configuration")
    @Bean
    public ApiDocumentationSettings apiDocumentationSettings() {
        return new ApiDocumentationSettings();
    }

    /**
     * Bean that captures the application security settings.
     *
     * @return Bean instance of type {@link ApplicationSecurityProperties}.
     */
    @ConfigurationProperties(prefix = "com.knowledge.hub.configuration.security")
    @Bean
    public ApplicationSecurityProperties applicationSecurityProperties() {
        return new ApplicationSecurityProperties();
    }
}
