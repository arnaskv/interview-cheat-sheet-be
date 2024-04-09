package com.interview.manager.backend.exceptions;

import com.interview.manager.backend.types.DataValidation;

import lombok.Getter;

public class DataValidationException extends RuntimeException {

    @Getter
    private final DataValidation.Status status;

    public DataValidationException(DataValidation.Status status, String message) {
        super(message == null ? status.getDescription() : message);
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public DataValidationException(DataValidation.Status status) {
        this(status, status.getDescription());
    }

}
