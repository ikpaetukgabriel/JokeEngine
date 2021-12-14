package com.example.jokeengine.controller;

import com.example.jokeengine.model.Jokes;
import com.example.jokeengine.service.JokeEngineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokeEngineController {

  private final JokeEngineServiceImpl jokeEngineServiceImpl;

  @Autowired
  public JokeEngineController(JokeEngineServiceImpl jokeEngineServiceImpl) {
    this.jokeEngineServiceImpl = jokeEngineServiceImpl;
  }

  @GetMapping(value = "/get-engine-jokes", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Jokes> getJokesEndpoint(@RequestParam String number) {
    Jokes jokes111 = jokeEngineServiceImpl.getRandomJokes(number);
    return new ResponseEntity<>(jokes111, HttpStatus.OK);
  }
}
