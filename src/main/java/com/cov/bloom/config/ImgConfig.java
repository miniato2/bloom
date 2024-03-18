package com.cov.bloom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImgConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        //이미지 불러올 수 있도록 세팅
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///c:/bloom/upload/");

//        registry.addResourceHandler("/thumbPath/**")
//                .addResourceLocations("file:///c:/bloom/img/upload/thumbnail/");
//        registry.addResourceHandler("/imagePath/**")
//                .addResourceLocations("file:///c:/bloom/img/upload/original/");

        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///Users/seung/practice/upload/");

        registry.addResourceHandler("/imagePath/**")
                .addResourceLocations("file:///Users/seung/practice/img/upload/original/");
    }

}
