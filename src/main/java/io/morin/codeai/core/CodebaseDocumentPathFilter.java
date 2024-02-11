package io.morin.codeai.core;

import io.morin.codeai.api.CodeAi;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
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
class CodebaseDocumentPathFilter implements Predicate<Path> {

  @NonNull
  @Builder.Default
  Set<String> blockedList = CodeAi.Settings.DEFAULT_BLOCK_LIST;

  @Override
  public boolean test(@NonNull Path path) {
    return blockedList.stream().noneMatch(path.toString()::contains);
  }
}
