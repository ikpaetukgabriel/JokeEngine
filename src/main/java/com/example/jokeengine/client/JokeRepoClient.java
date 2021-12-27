package com.example.jokeengine.client;

import com.example.jokeengine.model.Jokes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JokeRepoClient {

  private final WebClient webClient;

  public JokeRepoClient(WebClient.Builder webClientBuilder, @Value("${jokeRepo.url}") String baseUrl) {
    this.webClient = webClientBuilder
        .baseUrl(baseUrl)
        .build();
  }

  public Jokes getJokes() {
    return webClient.get()
        .uri("/get-repo-jokes")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .retrieve()
        .bodyToMono(Jokes.class)
        .block();
  }
}
