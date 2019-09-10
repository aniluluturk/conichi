package conichiapi.service;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractService {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
