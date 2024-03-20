package com.cov.bloom.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class FileDeleteException extends  Exception{
    public FileDeleteException(){}

    public FileDeleteException(String msg){
        super(msg);
    }

}
