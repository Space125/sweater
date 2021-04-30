package org.example.sweater.config;

import org.example.sweater.properties.SweaterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ivan Kurilov on 22.04.2021
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final String uploadDir;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public MvcConfig(SweaterProperties properties) {
        this.uploadDir = properties.getPath();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/" + uploadDir + "/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
