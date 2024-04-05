package com.example.MoviesAI.service.api;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Service
public class RapidAPIService {

    private static final String RAPIDAPI_HOST = "imdb188.p.rapidapi.com";
    private static final String RAPIDAPI_KEY = "9a461107e4mshedcfa85633d5584p14c69bjsn76d663eb8bfd";

    private final HttpClient httpClient;

    public RapidAPIService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public CompletableFuture<String> searchIMDB() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://imdb-top-100-movies.p.rapidapi.com/"))
                .header("X-RapidAPI-Key", "9a461107e4mshedcfa85633d5584p14c69bjsn76d663eb8bfd")
                .header("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
    }
}
