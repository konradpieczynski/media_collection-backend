package com.media_collection.backend.external_api.rapidapi.moviesdatabase;

import com.media_collection.backend.config.ExternalApiConfig;
import com.media_collection.backend.domain.ExternalMovieDatabaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class MoviesDatabaseClient {
    WebClient webClient = WebClient.create();
    private final ExternalApiConfig externalApiConfig;

    public ExternalMovieDatabaseDto getExternalMovieDatabaseDto() {
//                String json = webClient.get()
//                .uri("https://moviesdatabase.p.rapidapi.com/titles/random?list=top_rated_250&info=base_info&limit=2&titleType=movie")
//                .headers(
//                        httpHeaders -> {
//                            httpHeaders.set("X-RapidAPI-Key", "externalApiConfig.getMovie_api_key()");
//                            httpHeaders.set("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com");
//                        })
//                .exchange()
//                .block()
//                .bodyToMono(String.class)
//                .block();
//        System.out.println(json);
        return webClient.get()
                .uri("https://moviesdatabase.p.rapidapi.com/titles/random?list=top_rated_250&info=base_info&limit=50&titleType=movie")
                .headers(
                        httpHeaders -> {
                            httpHeaders.set("X-RapidAPI-Key", externalApiConfig.getMovie_api_key());
                            httpHeaders.set("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com");
                        })
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ExternalMovieDatabaseDto.class)
                .block();
    }
}
