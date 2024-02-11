package io.morin.codeai.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class SendMessageCommand {

  @NonNull
  String prompt;
}
