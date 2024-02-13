package io.morin.codeai.adapter;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * A Language Chain 4J assistant.
 */
@SystemMessage(
  {
    "Your goal is to help developers to write better code based on the parse codebase.",
    "The parsed codebase is a collection of text files.",
  }
)
public interface Assistant {
  /**
   * Chat with the assistant.
   *
   * @param chat the chat message.
   * @return the response.
   */
  String chat(@UserMessage String chat);

  /**
   * Stream with the assistant.
   *
   * @param chat the chat message.
   * @return the response.
   */
  TokenStream stream(@UserMessage String chat);
}
