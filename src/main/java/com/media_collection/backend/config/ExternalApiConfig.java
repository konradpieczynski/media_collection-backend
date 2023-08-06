package com.media_collection.backend.config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ExternalApiConfig {
    @Value("${externalapi.apple}")
    private String apple_api_key;

    @Value("${externalapi.movie}")
    private String movie_api_key;
}
