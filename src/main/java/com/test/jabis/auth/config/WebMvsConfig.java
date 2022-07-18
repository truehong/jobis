package com.test.jabis.auth.config;

import com.test.jabis.common.handler.TokenResolver;
import com.test.jabis.common.handler.UserInfoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvsConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserInfoHandler());
        resolvers.add(new TokenResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
