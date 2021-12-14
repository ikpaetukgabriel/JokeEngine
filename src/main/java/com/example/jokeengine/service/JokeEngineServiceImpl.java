package com.example.jokeengine.service;

import com.example.jokeengine.client.JokeRepoClient;
import com.example.jokeengine.model.Jokes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class JokeEngineServiceImpl implements JokeEngineService {

  private Jokes jokes;
  private final JokeRepoClient jokeRepoClient;

  @Autowired
  public JokeEngineServiceImpl(Jokes jokes, JokeRepoClient jokeRepoClient) {
    this.jokes = jokes;
    this.jokeRepoClient = jokeRepoClient;
  }

  @Override
  public Jokes getRandomJokes(String numberOfRandomJokes) {
    Jokes jokesFromRepo = jokeRepoClient.getJokes();
    jokes = selectRandomJokes(jokesFromRepo, numberOfRandomJokes);
    return jokes;
  }

  private Jokes selectRandomJokes(Jokes jokesFromRepo, String numberOfRandomJokes) {
    int numberOfRandomJokesAsInt = Integer.parseInt(numberOfRandomJokes);
    int numberOfJokesFromRepo = jokesFromRepo.getJokes().size();

    if (numberOfRandomJokesAsInt > numberOfJokesFromRepo) {
      numberOfRandomJokesAsInt = numberOfJokesFromRepo;
    }

    List<Integer> listOfNonRepeatingRandomNumbers = generateAListOfNonRepeatingRandomNumbers(
        numberOfJokesFromRepo, numberOfRandomJokesAsInt);

    Jokes randomJokes = new Jokes();

    listOfNonRepeatingRandomNumbers.forEach(index ->
        randomJokes.addJoke(jokesFromRepo
            .getJokes()
            .get(index))
    );

    return randomJokes;
  }

  private List<Integer> generateAListOfNonRepeatingRandomNumbers(int highestNumber, int sizeOfList) {
    return new Random()
        .ints(0, highestNumber)
        .distinct()
        .limit(sizeOfList)
        .boxed()
        .collect(Collectors.toList());
  }
}
