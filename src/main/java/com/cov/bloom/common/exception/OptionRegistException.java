package com.cov.bloom.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class OptionRegistException extends Exception{

    public OptionRegistException(){}

    public OptionRegistException(String msg){
        super(msg);
    }
}
