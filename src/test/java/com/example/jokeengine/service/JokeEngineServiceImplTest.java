package com.example.jokeengine.service;

import com.example.jokeengine.client.JokeRepoClient;
import com.example.jokeengine.model.Joke;
import com.example.jokeengine.model.Jokes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
class JokeEngineServiceImplTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JokeEngineServiceImpl jokeEngineService;

    @MockBean
    private JokeRepoClient jokeRepoClient;

    @BeforeEach
    void setUp() {
        Jokes jokes = generateJokesForTesting();
        // Mocks the response from the external api call from jokeRepoClient.getJokes()
        when(jokeRepoClient.getJokes()).thenReturn(jokes);
    }

    @Test
    void getRandomJokes() {
        String numberOfRandomJokes = "10";
        Jokes jokes = jokeEngineService.getRandomJokes(numberOfRandomJokes);
        assertThat(jokes)
                .isNotNull()
                .hasNoNullFieldsOrProperties();

        assertThat(jokes.getJokes().size())
                .isEqualTo(Integer.valueOf(numberOfRandomJokes));
    }

    private Jokes generateJokesForTesting() {
        Jokes jokes = new Jokes();
        IntStream.range(0, 15)
                .forEach(x -> jokes.addJoke(
                        Joke.builder()
                                .id("JokeID-".concat(String.valueOf(x)))
                                .content("Joke ".concat(String.valueOf(x)))
                                .build()));
        return jokes;
    }
}