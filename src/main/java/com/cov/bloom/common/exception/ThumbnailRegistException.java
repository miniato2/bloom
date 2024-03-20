package com.cov.bloom.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class ThumbnailRegistException extends Exception{

    public ThumbnailRegistException(){}

    public ThumbnailRegistException(String msg){
        super(msg);
    }
}
