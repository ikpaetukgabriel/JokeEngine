package com.example.jokeengine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public final class Joke {
  // final instance fields
  private String id;
  private String content;
}
