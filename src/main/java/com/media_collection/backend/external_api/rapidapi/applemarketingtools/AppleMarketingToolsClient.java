package com.media_collection.backend.external_api.rapidapi.applemarketingtools;

import com.media_collection.backend.config.ExternalApiConfig;
import com.media_collection.backend.domain.AppleSongResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AppleMarketingToolsClient {
    WebClient webClient = WebClient.create();

    private final ExternalApiConfig externalApiConfig;

    public AppleSongResponseDto getAppleSongFeedDto() {
//        String json = webClient.get()
//                .uri("https://apple-marketing-tools.p.rapidapi.com/pl/music/most-played/100/songs.json")
//                .headers(
//                        httpHeaders -> {
//                            httpHeaders.set("X-RapidAPI-Key", externalApiConfig.getApple_api_key());
//                            httpHeaders.set("X-RapidAPI-Host", "apple-marketing-tools.p.rapidapi.com");
//                        })
//                .exchange()
//                .block()
//                .bodyToMono(String.class)
//                .block();
//        System.out.println(json);
        return webClient.get()
                .uri("https://apple-marketing-tools.p.rapidapi.com/pl/music/most-played/100/songs.json")
                .headers(
                        httpHeaders -> {
                            httpHeaders.set("X-RapidAPI-Key", externalApiConfig.getApple_api_key());
                            httpHeaders.set("X-RapidAPI-Host", "apple-marketing-tools.p.rapidapi.com");
                        })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AppleSongResponseDto.class)
                .block();
    }
}
