package io.smartir.smartir.website.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/Image/**")
                .addResourceLocations("file:Image/")
                .setCachePeriod(0);

        registry
                .addResourceHandler("/BannerImage/**")
                .addResourceLocations("file:BannerImage/")
                .setCachePeriod(0);

    }

}
