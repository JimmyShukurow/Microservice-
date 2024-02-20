package io.smartir;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class MailChimpConfig {
    public static final String API_KEY = "c5176c6cb7aaad9381868419bca55f44-us9";
    public static final String LIST_ID = "57bdb82db7";
    public static final String STATUS = "pending";

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
