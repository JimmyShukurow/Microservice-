package io.smartir.smartir.website.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;

@Configuration
public class BeanConfiguration {

    @Bean(BeanNames.ARTICLE_IMAGE_UPLOAD_PATH_BEAN_NAME)
    public Path imageUploadPath(@Value(PropertyNames.SMART_IMAGE_UPLOAD_PATH_PROP_NAME) String UploadPath) {
        var path = Path.of(UploadPath);
        if (!path.toFile().exists())
            throw new RuntimeException("Given directory does not exists : " + UploadPath);
        return path;
    }

    @Bean(BeanNames.ARTICLE_BANNER_IMAGE_UPLOAD_PATH_BEAN_NAME)
    public Path bannerImageUploadPath(@Value(PropertyNames.SMART_BANNER_IMAGE_UPLOAD_PATH_PROP_NAME) String UploadPath) {
        var path = Path.of(UploadPath);
        if (!path.toFile().exists())
            throw new RuntimeException("Given directory does not exists : " + UploadPath);
        return path;
    }


}
