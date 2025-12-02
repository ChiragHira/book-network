package com.chirag.book.exception;

public class OperationNotPermittedException extends RuntimeException {
    public OperationNotPermittedException(String s) {
        super(s);
    }
}
