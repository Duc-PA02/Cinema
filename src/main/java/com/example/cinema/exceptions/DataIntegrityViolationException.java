package com.example.cinema.exceptions;

import org.springframework.dao.NonTransientDataAccessException;

public class DataIntegrityViolationException extends NonTransientDataAccessException {
    public DataIntegrityViolationException(String msg) {
        super(msg);
    }

    public DataIntegrityViolationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
