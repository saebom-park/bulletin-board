package com.saebom.bulletinboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns(
                        "/articles/**",
                        "/comments/**",
                        "/members/me/**"
                )
                .excludePathPatterns(
                        "/",
                        "/login",
                        "/logout",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/error",
                        "/members/me/withdraw/success"
                );
    }

}