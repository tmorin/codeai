package io.morin.codeai.adapter;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

@SystemMessage(
  {
    "Your goal is to help developers to write better code based on the parse codebase.",
    "The parsed codebase is a collection of text files.",
  }
)
public interface Assistant {
  String chat(@UserMessage String chat);
}
