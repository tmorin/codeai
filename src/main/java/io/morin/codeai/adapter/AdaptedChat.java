package io.morin.codeai.adapter;

import io.morin.codeai.core.Chat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class AdaptedChat implements Chat {

  @NonNull
  Assistant assistant;

  @Override
  public @NonNull String ask(@NonNull String message) {
    return assistant.chat(message);
  }
}
