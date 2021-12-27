package com.example.jokeengine.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor // Here for the builder
@NoArgsConstructor // Here for Jackson to avoid com.fasterxml.jackson.databind.exc.InvalidDefinitionException
public final class Joke {
  // final instance fields
  private String id;
  private String content;
}
