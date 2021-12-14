package com.example.jokeengine.service;

import com.example.jokeengine.model.Jokes;

public interface JokeEngineService {
    Jokes getRandomJokes(String numberOfRandomJokes);
}
