package com.example.jokeengine.controller;

import com.example.jokeengine.client.JokeRepoClient;
import com.example.jokeengine.model.Joke;
import com.example.jokeengine.model.Jokes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JokeEngineControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private JokeRepoClient jokeRepoClient;

  @BeforeEach
  void init() {
    Jokes jokes  = generateJokesForTesting();
    // Mocks the response from the external api call from jokeRepoClient.getJokes()
    when(jokeRepoClient.getJokes()).thenReturn(jokes);
  }

  @Test
  public void test_getJokesEndpoint() throws Exception {

    String parameterValue = "1";
    MvcResult mvcResult = mockMvc.perform(get("/get-engine-jokes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("number", parameterValue))
            .andExpect(status().isOk())
            .andReturn();

    String jokesAsStringFromMvcResult = mvcResult.getResponse().getContentAsString();
    Jokes jokesFromMvcResult = objectMapper.readValue(jokesAsStringFromMvcResult, Jokes.class);

    assertThat(jokesFromMvcResult)
            .hasFieldOrProperty("jokes")
            .hasNoNullFieldsOrProperties();

    assertEquals(Integer.valueOf(parameterValue),
            jokesFromMvcResult.getJokes().size());
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