package com.chirag.book.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCode {

    NO_CODE(0,HttpStatus.NOT_IMPLEMENTED,"No code"),

    INCORRECT_CURRENT_PASSWORD(300,HttpStatus.BAD_REQUEST,"Current password is incorrect"),

    NEW_PASSWORD_DOES_NOT_MATCH(301,HttpStatus.BAD_REQUEST,"New password does not match"),
    ACCOUNT_DISABLE(303,HttpStatus.FORBIDDEN,"User account is disable"),
    ACCOUNT_LOCKED(302,HttpStatus.FORBIDDEN,"User account is locked"),

    BAD_CREDENTIALS(304,HttpStatus.FORBIDDEN,"Login and / or password is incorrect");

    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCode(int code,HttpStatus httpStatus,String description){
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
