/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of knowledge-hub.
 *
 * knowledge-hub project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.knowledge.hub.security.db.configuration;

import com.knowledge.hub.commons.web.security.AbstractWebSecurityConfigurerAdapter;
import com.knowledge.hub.security.properties.ApplicationSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.knowledge.hub.commons.web.filter.SecurityAuthenticationFilter;
import com.knowledge.hub.security.token.JwtTokenProvider;

/**
 * Configuration class responsible to configure the Db auth aspects for the microservice / application in
 * consideration.
 *
 * @author Jagannath Mishra
 */
public class AbstractDbAuthWebSecurityConfigurerAdapter  extends AbstractWebSecurityConfigurerAdapter {
    /** Password encoder of type {@link PasswordEncoder}. */
    protected PasswordEncoder passwordEncoder;

    /** Service implementation of type {@link UserDetailsService}. */
    protected UserDetailsService userDetailsService;

    /** Autowiring the password encoder.
     *
     * @param passwordEncoder
     *     the password encoder for the password.
     */
    @Autowired
    public final void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /** Autowiring the user details
     *
     * @param userDetailsService
     *     the user details service having the functionality to check the user with given username..
     */
    @Autowired
    public final void setUserDetailsService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Constructor.
     *
     * @param applicationSecurityProperties
     *      Configuration properties instance of type {@link ApplicationSecurityProperties}.
     * @param securityAuthenticationFilter
     *      Configuration properties instance of type {@link SecurityAuthenticationFilter}.
     * @param jwtTokenProvider
     *      Configuration properties instance of type {@link JwtTokenProvider}.
     */
    public AbstractDbAuthWebSecurityConfigurerAdapter(ApplicationSecurityProperties applicationSecurityProperties, SecurityAuthenticationFilter securityAuthenticationFilter, JwtTokenProvider jwtTokenProvider) {
        super(applicationSecurityProperties, securityAuthenticationFilter,jwtTokenProvider);
    }

    /*** This method configure the userDetails service with password encoder
     * that will be used for auth.
     *
     * @param auth
     *    The authentication manager builder having the auth details.
     * @throws Exception
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /** The below code gives the Authentication Manager that is registered
     * for the auth.
     *
     * @return AuthenticationManager
     *   the registered authentication manager.
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}