package com.example.jokeengine.client;

import com.example.jokeengine.model.Joke;
import com.example.jokeengine.model.Jokes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
class JokeRepoClientTest {

    private MockWebServer mockWebServer;

    private JokeRepoClient jokeRepoClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String url = mockWebServer.url("/").toString();
        this.jokeRepoClient = new JokeRepoClient(WebClient.builder(), url);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getJokes() throws JsonProcessingException, InterruptedException {

        Jokes jokes = new Jokes();

        IntStream.range(0, 3)
                .forEach(x -> jokes.addJoke(
                        Joke.builder()
                                .id("JokeID-".concat(String.valueOf(x)))
                                .content("Joke ".concat(String.valueOf(x)))
                                .build()));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(OK.value())
                .setBody(objectMapper.writeValueAsString(jokes))
                .addHeader(CONTENT_TYPE, APPLICATION_JSON));

        Jokes JokesFromClient = jokeRepoClient.getJokes();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals(GET.toString(), recordedRequest.getMethod());
        assertEquals("/get-repo-jokes", recordedRequest.getPath());
        assertThat(JokesFromClient)
                .isInstanceOf(Jokes.class)
                .hasFieldOrProperty("jokes")
                .hasNoNullFieldsOrProperties();
    }
}