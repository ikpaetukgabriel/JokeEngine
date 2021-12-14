package com.example.jokeengine.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Component
public class Jokes {
  private List<Joke> jokes = new ArrayList<>();

  public void addJoke(Joke joke) {
    jokes.add(joke);
  }
}
