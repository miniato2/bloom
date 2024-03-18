package com.cov.bloom.common.exception;

import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class PortfolioRegistException extends  Exception{

    public PortfolioRegistException(){

    }

    public PortfolioRegistException(String msg){
        super(msg);
    }

    @ExceptionHandler(UnsupportedFormatException.class)
    public String handleUnsupportedFormatException(UnsupportedFormatException ex, RedirectAttributes rttr){
        rttr.addFlashAttribute("errorMessage", "잘못된 파일입니다.");
        return "redirect:/portfolio/regist";
    }
}
