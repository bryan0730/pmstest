package com.forwiz.pms.domain.organization.exception;

public class DeleteListEmptyException extends RuntimeException{

    public DeleteListEmptyException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
