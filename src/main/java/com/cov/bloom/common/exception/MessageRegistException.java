package com.cov.bloom.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MessageRegistException extends Exception{
    public MessageRegistException(){

    }
    public MessageRegistException(String message){ super(message);}
}
