/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of knowledgehub-knowledgehub-service.
 *
 * knowledgehub-knowledgehub-service project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.knowledge.hub.error;

import com.knowledge.hub.commons.error.ErrorMessages;
import com.knowledge.hub.commons.error.IError;
import com.knowledge.hub.commons.error.IErrorMessages;

/**
 * Enum constants that represent the API error codes and messages that can be used across the application.
 * <p>
 * For more details, see the documentation on {@link IError} contract.
 *
 * @author Jagannath Mishra
 */
public enum Errors implements IError {
    // NOTE:
    // Whenever a new constant is added here, ensure that the error message for the same constant is added in
    // src/main/resources/l10n/error_messages.properties

    RESOURCE_NOT_FOUND,
    RESOURCE_NOT_FOUND_WITH_ID;

    /** Reference to {@link IErrorMessages}, which holds the error messages. */
    private static final ErrorMessages ERROR_MESSAGES = ErrorMessages.instance("l10n/error_messages", Errors.class.getClassLoader());

    @Override
    public IErrorMessages getErrorMessages() {
        return Errors.ERROR_MESSAGES;
    }
}
