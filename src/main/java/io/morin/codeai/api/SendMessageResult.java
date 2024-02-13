package io.morin.codeai.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The SendMessageResult class is the result of a message being sent.
 */
@Value
@Builder(toBuilder = true)
public class SendMessageResult {

  /**
   * The prompt.
   */
  @NonNull
  String answer;
}
