package com.example.bankservice.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "twelvedata.api")
public class TwelvedataApiProperties {
    private String baseUrl;
    private String key;
}
