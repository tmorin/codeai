package io.morin.codeai.core;

import io.morin.codeai.api.CodeAi;
import io.morin.codeai.api.SendMessageCommand;
import io.morin.codeai.api.SendMessageResult;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class CodeAiCore implements CodeAi {

  @NonNull
  Chat chat;

  @Override
  public SendMessageResult execute(
    @NonNull SendMessageCommand sendMessageCommand
  ) {
    val answer = chat.ask(sendMessageCommand.getPrompt());
    return SendMessageResult.builder().answer(answer).build();
  }
}
